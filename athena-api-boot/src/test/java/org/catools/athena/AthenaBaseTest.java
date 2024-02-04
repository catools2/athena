package org.catools.athena;

import org.catools.athena.core.model.NameValuePair;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AthenaBaseTest.SpringTestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AthenaBaseTest {

  @Configuration
  @ComponentScan({"org.catools.athena"})
  @PropertySource("classpath:application.properties")
  public static class SpringTestConfig {
  }


  protected static <T1 extends NameValuePair, T2 extends NameValuePair> void verifyNameValuePairs(Collection<T1> actuals, Collection<T2> expected) {
    assertThat(expected, notNullValue());
    assertThat(expected.isEmpty(), equalTo(false));
    for (T2 t2 : expected) {
      verifyNameValuePair(actuals, t2);
    }
  }


  protected static <T1 extends NameValuePair, T2 extends NameValuePair> void verifyNameValuePair(Collection<T1> actuals, T2 expected) {
    T1 actual = actuals.stream().filter(m -> Objects.equals(m.getName(), expected.getName())).findFirst().orElse(null);
    assertThat(actual, notNullValue());

    assertThat(actual.getName(), equalTo(expected.getName()));
    assertThat(actual.getValue(), equalTo(expected.getValue()));
  }
}