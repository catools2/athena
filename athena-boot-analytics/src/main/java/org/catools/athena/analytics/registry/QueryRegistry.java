package org.catools.athena.analytics.registry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Loads the generated query registry from the classpath and validates it at startup.
 *
 * <p>Validation is deliberately a hard failure. Everything checked here is a property of the
 * packaged artifact, not of the environment - a query whose declared parameters do not match the
 * binds in its SQL is a broken build, and it is far better to refuse to start than to serve it
 * until someone happens to open the panel that uses it.
 */
@Slf4j
@Component
public class QueryRegistry {

  /**
   * Two sources, deliberately separate. {@code queries.json} is generated from the Grafana
   * dashboards and is rewritten wholesale by the extractor; {@code curated.json} is
   * hand-written for the workspace pages and the agent, and nothing regenerates it. Keeping
   * them apart is what stops a regeneration from silently deleting hand-authored SQL.
   */
  private static final List<Source> SOURCES = List.of(
      new Source("classpath:analytics/queries.json", "classpath:analytics/queries/", "grafana"),
      new Source("classpath:analytics/curated.json", "classpath:analytics/curated/", "curated"));

  private record Source(String manifest, String sqlDir, String origin) {}

  /** Named binds, e.g. {@code :timeFrom}. Excludes {@code ::cast} so type casts are not matched. */
  private static final Pattern BIND = Pattern.compile("(?<!:):(\\w+)");

  /**
   * String literals and comments, stripped before scanning for binds.
   *
   * <p>Without this, a {@code to_char(t, 'DD-Mon-YYYY HH24:MI:SS')} format string reads as binds
   * named MI and SS. Spring's own parser is quote-aware and ignores them at runtime, so a validator
   * that is not would reject queries that work perfectly well.
   */
  private static final Pattern LITERAL_OR_COMMENT =
      Pattern.compile("'(?:[^']|'')*'|--[^\\n]*|/\\*.*?\\*/", Pattern.DOTALL);

  /** Operator slots, e.g. <code>{{cycle_type}}</code>. */
  private static final Pattern SLOT = Pattern.compile("\\{\\{(\\w+)}}");

  private final ObjectMapper mapper = new ObjectMapper();
  private final Map<String, RegisteredQuery> queries = new LinkedHashMap<>();

  @PostConstruct
  void load() throws IOException {
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    List<String> problems = new ArrayList<>();

    for (Source source : SOURCES) {
      Resource manifest = resolver.getResource(source.manifest());
      if (!manifest.exists()) {
        throw new IllegalStateException("Analytics query manifest not found at " + source.manifest());
      }
      loadFrom(resolver, source, manifest, problems);
    }

    if (!problems.isEmpty()) {
      throw new IllegalStateException(
          "Analytics query registry is invalid:\n  " + String.join("\n  ", problems));
    }
    log.info("Loaded {} analytics queries ({} curated) over {} views.",
        queries.size(),
        queries.values().stream().filter(q -> "curated".equals(q.origin())).count(),
        queries.values().stream().flatMap(q -> q.views().stream()).distinct().count());
  }

  private void loadFrom(PathMatchingResourcePatternResolver resolver, Source source,
                        Resource manifest, List<String> problems) throws IOException {
    try (InputStream in = manifest.getInputStream()) {
      for (JsonNode node : mapper.readTree(in)) {
        String id = node.get("id").asText();
        if (queries.containsKey(id)) {
          problems.add(id + ": declared in more than one manifest");
          continue;
        }
        Resource sqlResource = resolver.getResource(source.sqlDir() + id + ".sql");
        if (!sqlResource.exists()) {
          problems.add(id + ": manifest entry has no matching " + id + ".sql");
          continue;
        }

        String sql;
        try (InputStream sqlIn = sqlResource.getInputStream()) {
          sql = new String(sqlIn.readAllBytes(), StandardCharsets.UTF_8);
        }

        List<QueryParam> params = new ArrayList<>();
        for (JsonNode p : node.withArray("params")) {
          ParamKind kind = ParamKind.valueOf(p.get("kind").asText().toUpperCase());
          List<String> allowed = new ArrayList<>();
          p.withArray("allowed").forEach(a -> allowed.add(a.asText()));
          if (kind == ParamKind.OPERATOR && allowed.isEmpty()) {
            problems.add(id + ": operator parameter '" + p.get("name").asText()
                + "' declares no allowed values, so nothing could ever be substituted safely");
          }
          params.add(new QueryParam(p.get("name").asText(), kind, allowed));
        }

        RegisteredQuery query = new RegisteredQuery(
            id,
            node.path("title").asText(id),
            readStrings(node, "views"),
            readStrings(node, "tables"),
            params,
            sql,
            readStrings(node, "dashboards"),
            source.origin());

        problems.addAll(validate(query));
        queries.put(id, query);
      }
    }
  }

  /** Declared parameters and the SQL must agree in both directions. */
  private List<String> validate(RegisteredQuery query) {
    List<String> problems = new ArrayList<>();
    List<String> declared = query.params().stream().map(QueryParam::name).toList();

    List<String> binds = matches(BIND, query.sql());
    List<String> slots = matches(SLOT, query.sql());

    for (String bind : binds) {
      Optional<QueryParam> param = query.param(bind);
      if (param.isEmpty()) {
        problems.add(query.id() + ": SQL binds :" + bind + " but the manifest does not declare it");
      } else if (param.get().isOperator()) {
        problems.add(query.id() + ": '" + bind + "' is an operator and cannot be bound as a value");
      }
    }
    for (String slot : slots) {
      Optional<QueryParam> param = query.param(slot);
      if (param.isEmpty()) {
        problems.add(query.id() + ": SQL has slot {{" + slot + "}} that the manifest does not declare");
      } else if (!param.get().isOperator()) {
        problems.add(query.id() + ": '" + slot + "' is substituted as text but is declared "
            + param.get().kind() + "; only operators may be substituted");
      }
    }
    for (String name : declared) {
      if (!binds.contains(name) && !slots.contains(name)) {
        problems.add(query.id() + ": declares parameter '" + name + "' that the SQL never uses");
      }
    }
    return problems;
  }

  private static List<String> matches(Pattern pattern, String sql) {
    List<String> found = new ArrayList<>();
    Matcher m = pattern.matcher(stripLiterals(sql));
    while (m.find()) {
      if (!found.contains(m.group(1))) {
        found.add(m.group(1));
      }
    }
    return found;
  }

  /** Blank out literals and comments, preserving length so nothing else shifts. */
  private static String stripLiterals(String sql) {
    Matcher m = LITERAL_OR_COMMENT.matcher(sql);
    StringBuilder out = new StringBuilder(sql);
    while (m.find()) {
      for (int i = m.start(); i < m.end(); i++) {
        out.setCharAt(i, ' ');
      }
    }
    return out.toString();
  }

  private static List<String> readStrings(JsonNode node, String field) {
    List<String> values = new ArrayList<>();
    node.withArray(field).forEach(v -> values.add(v.asText()));
    return values;
  }

  public Collection<RegisteredQuery> all() {
    return queries.values();
  }

  public Optional<RegisteredQuery> find(String id) {
    return Optional.ofNullable(queries.get(id));
  }

  public int size() {
    return queries.size();
  }
}
