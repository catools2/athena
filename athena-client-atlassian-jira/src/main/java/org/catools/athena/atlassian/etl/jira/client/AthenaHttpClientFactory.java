package org.catools.athena.atlassian.etl.jira.client;

import com.atlassian.event.api.EventPublisher;
import com.atlassian.httpclient.apache.httpcomponents.DefaultHttpClientFactory;
import com.atlassian.httpclient.api.HttpClient;
import com.atlassian.httpclient.api.factory.HttpClientOptions;
import com.atlassian.jira.rest.client.api.AuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousHttpClientFactory;
import com.atlassian.jira.rest.client.internal.async.AtlassianHttpClientDecorator;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import com.atlassian.sal.api.ApplicationProperties;
import com.atlassian.sal.api.UrlMode;
import com.atlassian.sal.api.executor.ThreadLocalContextManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Extend AsynchronousHttpClientFactory to address socket timeout issue as per
 * https://confluence.atlassian.com/jirakb/receiving-sockettimeoutexception-when-using-jira-rest-java-client-1072216885.html
 */
public class AthenaHttpClientFactory extends AsynchronousHttpClientFactory {
  public DisposableHttpClient createClient(int socketTimeout, TimeUnit timeUnit, URI serverUri, AuthenticationHandler authenticationHandler) {
    HttpClientOptions options = new HttpClientOptions();
    options.setSocketTimeout(socketTimeout, timeUnit);

    final DefaultHttpClientFactory<?> defaultHttpClientFactory = new DefaultHttpClientFactory<>(new NoOpEventPublisher(),
        new RestClientApplicationProperties(serverUri),
        new ThreadLocalContextManager<>() {
          public Object getThreadLocalContext() {
            return null;
          }

          public void setThreadLocalContext(Object context) {
          }

          public void clearThreadLocalContext() {
          }
        });
    final HttpClient httpClient = defaultHttpClientFactory.create(options);
    return new AtlassianHttpClientDecorator(httpClient, authenticationHandler) {
      public void destroy() throws Exception {
        defaultHttpClientFactory.dispose(httpClient);
      }
    };
  }

  public DisposableHttpClient createClient(HttpClient client) {
    return new AtlassianHttpClientDecorator(client, null) {
      public void destroy() {
      }
    };
  }

  private static final class MavenUtils {
    private static final Logger logger = LoggerFactory.getLogger(MavenUtils.class);

    private MavenUtils() {
    }

    static String getVersion(final String groupId, String artifactId) {
      Properties props = new Properties();
      InputStream resourceAsStream = null;

      String var5;
      try {
        resourceAsStream = MavenUtils.class.getResourceAsStream(String.format("/META-INF/maven/%s/%s/pom.properties", groupId, artifactId));
        props.load(resourceAsStream);
        String var4 = props.getProperty("version", "unknown");
        return var4;
      } catch (Exception var15) {
        logger.debug("Could not find version for maven artifact {}:{}", groupId, artifactId);
        logger.debug("Got the following exception", var15);
        var5 = "unknown";
      } finally {
        if (resourceAsStream != null) {
          try {
            resourceAsStream.close();
          } catch (IOException var14) {
          }
        }

      }

      return var5;
    }
  }

  private static class RestClientApplicationProperties implements ApplicationProperties {
    private final String baseUrl;

    private RestClientApplicationProperties(URI jiraURI) {
      this.baseUrl = jiraURI.getPath();
    }

    public String getBaseUrl() {
      return this.baseUrl;
    }

    @Nonnull
    public String getBaseUrl(UrlMode urlMode) {
      return this.baseUrl;
    }

    @Nonnull
    public String getDisplayName() {
      return "Atlassian JIRA Rest Java Client";
    }

    @Nonnull
    public String getPlatformId() {
      return "jira";
    }

    @Nonnull
    public String getVersion() {
      return MavenUtils.getVersion("com.atlassian.jira", "jira-rest-java-client-core");
    }

    @Nonnull
    public Date getBuildDate() {
      throw new UnsupportedOperationException();
    }

    @Nonnull
    public String getBuildNumber() {
      return String.valueOf(0);
    }

    public File getHomeDirectory() {
      return new File(".");
    }

    public String getPropertyValue(final String s) {
      throw new UnsupportedOperationException("Not implemented");
    }

    @Nonnull
    public String getApplicationFileEncoding() {
      return StandardCharsets.UTF_8.name();
    }

    @Nonnull
    public Optional<Path> getLocalHomeDirectory() {
      return Optional.of(this.getHomeDirectory().toPath());
    }

    @Nonnull
    public Optional<Path> getSharedHomeDirectory() {
      return this.getLocalHomeDirectory();
    }
  }

  private static class NoOpEventPublisher implements EventPublisher {
    private NoOpEventPublisher() {
    }

    public void publish(Object o) {
    }

    public void register(Object o) {
    }

    public void unregister(Object o) {
    }

    public void unregisterAll() {
    }
  }
}
