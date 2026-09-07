package org.catools.athena.rest.feign.git.helpers;

import com.jcraft.jsch.Session;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.net.URL;
import java.util.Locale;
import java.util.function.Function;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.rest.feign.common.configs.ConfigUtils;
import org.catools.athena.rest.feign.git.exception.GitClientException;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.transport.TransportHttp;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.transport.http.HttpConnection;
import org.eclipse.jgit.transport.http.HttpConnectionFactory2;
import org.eclipse.jgit.transport.http.JDKHttpConnectionFactory;
import org.eclipse.jgit.transport.ssh.jsch.JschConfigSessionFactory;
import org.eclipse.jgit.transport.ssh.jsch.OpenSshConfig;

@Slf4j
@UtilityClass
public class GitCloneClient {

  /**
   * Clone repository from specified url to the directory in storage with repository name.
   *
   * @param name repository/directory name
   * @param url the source repository to clone
   * @return JGit client to work with
   */
  public static Git clone(final String localPath, String name, String url) {
    return clone(localPath, name, url, buildCloneCommand(url));
  }

  /**
   * Clone repository from specified url to the directory in storage with repository name.
   *
   * @param name repository/directory name
   * @param url the source repository to clone
   * @param username the git user
   * @param password the password for the git user
   * @return JGit client to work with
   */
  public static Git clone(
      final String localPath, String name, String url, String username, String password) {
    CloneCommand command =
        buildCloneCommand(url)
            .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password));
    return clone(localPath, name, url, command);
  }

  private static CloneCommand buildCloneCommand(String url) {
    return Git.cloneRepository()
        .setURI(url)
        .setTransportConfigCallback(new GitTransportConfigCallback(resolveProxySettings(url)));
  }

  private static synchronized Git clone(
      final String localPath, String name, String url, CloneCommand command) {
    File gitDir = new File(localPath);

    try {
      log.info("Start cloning {} repository from {}.", name, url);
      return command.setDirectory(gitDir).call();
    } catch (GitAPIException e) {
      throw new GitClientException("Failed to clone the git repository", e);
    } finally {
      log.info("Finish cloning {} repository from {}.", name, url);
    }
  }

  static ProxySettings resolveProxySettings(String url) {
    return resolveProxySettings(url, ConfigUtils::getProperty);
  }

  /**
   * Resolve the proxy an http(s) clone of {@code url} should go through, honouring the conventional
   * {@code HTTP_PROXY} / {@code HTTPS_PROXY} / {@code NO_PROXY} variables in either case. Returns
   * {@code null} when the clone must go direct - including for every non-http scheme, since ssh
   * cannot use an http proxy.
   */
  static ProxySettings resolveProxySettings(String url, Function<String, String> propertyLookup) {
    URI targetUri;

    try {
      targetUri = URI.create(url);
    } catch (IllegalArgumentException ex) {
      log.debug("Ignoring invalid git url for proxy resolution.", ex);
      return null;
    }

    String scheme = StringUtils.lowerCase(targetUri.getScheme(), Locale.ROOT);
    if (!"http".equals(scheme) && !"https".equals(scheme)) {
      return null;
    }

    String host = targetUri.getHost();
    if (StringUtils.isBlank(host) || isNoProxyHost(host, propertyLookup)) {
      return null;
    }

    String proxyUrl = getProxyUrl(scheme, propertyLookup);
    if (StringUtils.isBlank(proxyUrl)) {
      return null;
    }

    try {
      URI proxyUri = URI.create(proxyUrl.contains("://") ? proxyUrl : "http://" + proxyUrl);
      String proxyHost = proxyUri.getHost();
      int proxyPort = proxyUri.getPort();

      if (StringUtils.isBlank(proxyHost)) {
        return null;
      }

      if (proxyPort < 0) {
        proxyPort = "https".equalsIgnoreCase(proxyUri.getScheme()) ? 443 : 80;
      }

      return new ProxySettings(proxyHost, proxyPort);
    } catch (IllegalArgumentException ex) {
      log.warn("Ignoring invalid HTTP proxy configuration for git clone.", ex);
      return null;
    }
  }

  private static String getProxyUrl(String scheme, Function<String, String> propertyLookup) {
    if ("https".equals(scheme)) {
      return firstNonBlank(
          propertyLookup.apply("HTTPS_PROXY"),
          propertyLookup.apply("https_proxy"),
          propertyLookup.apply("HTTP_PROXY"),
          propertyLookup.apply("http_proxy"));
    }

    return firstNonBlank(
        propertyLookup.apply("HTTP_PROXY"),
        propertyLookup.apply("http_proxy"),
        propertyLookup.apply("HTTPS_PROXY"),
        propertyLookup.apply("https_proxy"));
  }

  private static boolean isNoProxyHost(String host, Function<String, String> propertyLookup) {
    String noProxy =
        firstNonBlank(propertyLookup.apply("NO_PROXY"), propertyLookup.apply("no_proxy"));
    if (StringUtils.isBlank(noProxy)) {
      return false;
    }

    String normalizedHost = StringUtils.lowerCase(host, Locale.ROOT);
    for (String rawEntry : noProxy.split(",")) {
      String entry = StringUtils.trimToEmpty(rawEntry);
      if (entry.isEmpty()) {
        continue;
      }

      if ("*".equals(entry)) {
        return true;
      }

      String normalizedEntry = StringUtils.lowerCase(stripPort(entry), Locale.ROOT);
      if (normalizedEntry.startsWith(".")) {
        String suffix = normalizedEntry.substring(1);
        if (normalizedHost.equals(suffix) || normalizedHost.endsWith('.' + suffix)) {
          return true;
        }
      } else if (normalizedHost.equals(normalizedEntry)) {
        return true;
      }
    }

    return false;
  }

  private static String stripPort(String host) {
    if (host.startsWith("[") && host.contains("]")) {
      return host.substring(1, host.indexOf(']'));
    }

    int colonIndex = host.lastIndexOf(':');
    if (colonIndex > -1 && host.indexOf(':') == colonIndex) {
      return host.substring(0, colonIndex);
    }

    return host;
  }

  private static String firstNonBlank(String... values) {
    for (String value : values) {
      if (StringUtils.isNotBlank(value)) {
        return value;
      }
    }
    return null;
  }

  private static class GitTransportConfigCallback implements TransportConfigCallback {

    private final SshSessionFactory sshSessionFactory =
        new JschConfigSessionFactory() {
          @Override
          protected void configure(OpenSshConfig.Host hc, Session session) {
            session.setConfig("StrictHostKeyChecking", "no");
          }
        };

    private final HttpConnectionFactory2 httpConnectionFactory;

    private GitTransportConfigCallback(ProxySettings proxySettings) {
      this.httpConnectionFactory =
          proxySettings == null ? null : new ProxyHttpConnectionFactory(proxySettings);
    }

    @Override
    public void configure(Transport transport) {
      // Typed checks, not a bare cast: this callback is now attached to every clone, and the
      // previous unconditional cast to SshTransport meant it could only ever be used for ssh://.
      if (transport instanceof SshTransport sshTransport) {
        sshTransport.setSshSessionFactory(sshSessionFactory);
      }

      if (transport instanceof TransportHttp transportHttp && httpConnectionFactory != null) {
        transportHttp.setHttpConnectionFactory(httpConnectionFactory);
      }
    }
  }

  private static class ProxyHttpConnectionFactory implements HttpConnectionFactory2 {

    private final HttpConnectionFactory2 delegate = new JDKHttpConnectionFactory();
    private final Proxy proxy;

    private ProxyHttpConnectionFactory(ProxySettings proxySettings) {
      this.proxy = proxySettings.toProxy();
    }

    @Override
    public HttpConnection create(URL url) throws IOException {
      return delegate.create(url, proxy);
    }

    @Override
    public HttpConnection create(URL url, Proxy ignoredProxy) throws IOException {
      return delegate.create(url, proxy);
    }

    @Override
    public GitSession newSession() {
      return delegate.newSession();
    }
  }

  record ProxySettings(String host, int port) {

    private Proxy toProxy() {
      return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
    }
  }
}
