package org.catools.athena;

import org.catools.athena.feign.TestFeignBuilder;
import org.catools.athena.model.core.NameValuePair;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AthenaTestConfig.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ComponentScan(basePackages = {"org.catools.athena"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("testContainers")
public class AthenaSpringBootIT {

  @LocalServerPort
  protected int port;

  @Autowired
  protected TestFeignBuilder testFeignBuilder;

  protected <T1 extends NameValuePair, T2 extends NameValuePair> void verifyNameValuePairs(Collection<T1> actual, Collection<T2> expected) {
    assertThat(expected, notNullValue());
    assertThat(expected.size(), equalTo(actual.size()));
    for (T2 t2 : expected) {
      verifyNameValuePair(actual, t2);
    }
  }

  protected <T1 extends NameValuePair, T2 extends NameValuePair> void verifyNameValuePair(Collection<T1> actuals, T2 expected) {
    T1 actual = actuals.stream().filter(m -> Objects.equals(m.getName(), expected.getName())).findFirst().orElse(null);
    assertThat(actual, notNullValue());

    assertThat(actual.getName(), equalTo(expected.getName()));
    assertThat(actual.getValue(), equalTo(expected.getValue()));
  }
}