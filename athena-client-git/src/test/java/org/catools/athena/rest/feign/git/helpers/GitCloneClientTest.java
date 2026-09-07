package org.catools.athena.rest.feign.git.helpers;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GitCloneClientTest {

  @Test
  void resolveProxySettings_withHttpsTargetShouldUseHttpsProxy() {
    GitCloneClient.ProxySettings proxySettings =
        GitCloneClient.resolveProxySettings(
            "https://github.com/group/project.git",
            Map.of("HTTPS_PROXY", "http://proxy.example.com:3128")::get);

    assertThat(proxySettings).isEqualTo(new GitCloneClient.ProxySettings("proxy.example.com", 3128));
  }

  @Test
  void resolveProxySettings_withMissingHttpsProxyShouldFallBackToHttpProxy() {
    GitCloneClient.ProxySettings proxySettings =
        GitCloneClient.resolveProxySettings(
            "https://github.com/group/project.git",
            Map.of("HTTP_PROXY", "proxy.example.com:8080")::get);

    assertThat(proxySettings).isEqualTo(new GitCloneClient.ProxySettings("proxy.example.com", 8080));
  }

  @Test
  void resolveProxySettings_withoutAnExplicitPortShouldDefaultBySchemeS() {
    GitCloneClient.ProxySettings proxySettings =
        GitCloneClient.resolveProxySettings(
            "https://github.com/group/project.git",
            Map.of("HTTPS_PROXY", "https://proxy.example.com")::get);

    assertThat(proxySettings).isEqualTo(new GitCloneClient.ProxySettings("proxy.example.com", 443));
  }

  @Test
  void resolveProxySettings_withNoProxyHostShouldBypassProxy() {
    GitCloneClient.ProxySettings proxySettings =
        GitCloneClient.resolveProxySettings(
            "https://github.com/group/project.git",
            Map.of(
                    "HTTPS_PROXY", "http://proxy.example.com:3128",
                    "NO_PROXY", ".github.com,localhost")
                ::get);

    assertThat(proxySettings).isNull();
  }

  @Test
  void resolveProxySettings_withNoProxyWildcardShouldBypassProxy() {
    GitCloneClient.ProxySettings proxySettings =
        GitCloneClient.resolveProxySettings(
            "https://github.com/group/project.git",
            Map.of(
                    "HTTPS_PROXY", "http://proxy.example.com:3128",
                    "NO_PROXY", "*")
                ::get);

    assertThat(proxySettings).isNull();
  }

  @Test
  void resolveProxySettings_withSshUrlShouldNotUseHttpProxy() {
    GitCloneClient.ProxySettings proxySettings =
        GitCloneClient.resolveProxySettings(
            "ssh://git@github.com/group/project.git",
            Map.of("HTTPS_PROXY", "http://proxy.example.com:3128")::get);

    assertThat(proxySettings).isNull();
  }

  @Test
  void resolveProxySettings_withNoProxyConfiguredShouldGoDirect() {
    GitCloneClient.ProxySettings proxySettings =
        GitCloneClient.resolveProxySettings(
            "https://github.com/group/project.git", Map.<String, String>of()::get);

    assertThat(proxySettings).isNull();
  }
}
