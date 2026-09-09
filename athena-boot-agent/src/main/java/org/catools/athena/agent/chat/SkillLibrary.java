package org.catools.athena.agent.chat;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Markdown prompts that give the model a worked method for a recurring question.
 *
 * <p>They are files rather than code so someone who knows the domain can change how a question is
 * answered without touching Java.
 */
@Slf4j
@Component
public class SkillLibrary {

  private final Map<String, Skill> skills = new LinkedHashMap<>();

  public record Skill(String name, String description, String content) {}

  @PostConstruct
  void load() throws IOException {
    Resource[] files = new PathMatchingResourcePatternResolver()
        .getResources("classpath:skills/*.md");
    for (Resource file : files) {
      String name = file.getFilename() == null ? "unknown"
          : file.getFilename().replaceFirst("\\.md$", "");
      String content;
      try (InputStream in = file.getInputStream()) {
        content = new String(in.readAllBytes(), StandardCharsets.UTF_8);
      }
      skills.put(name, new Skill(name, firstLine(content), content));
    }
    log.info("Loaded {} agent skills: {}", skills.size(), skills.keySet());
  }

  private static String firstLine(String content) {
    return content.lines()
        .filter(l -> !l.isBlank() && !l.startsWith("#"))
        .findFirst()
        .orElse("");
  }

  public List<Skill> all() {
    return List.copyOf(skills.values());
  }

  public Optional<Skill> find(String name) {
    return Optional.ofNullable(skills.get(name));
  }
}
