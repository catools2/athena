package org.catools.athena;

import org.catools.athena.core.model.NameValuePair;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringTestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public interface AthenaBaseTest {

  default <T1 extends NameValuePair, T2 extends NameValuePair> void verifyNameValuePairs(Collection<T1> actual, Collection<T2> expected) {
    assertThat(expected, notNullValue());
    assertThat(expected.size(), equalTo(actual.size()));
    for (T2 t2 : expected) {
      verifyNameValuePair(actual, t2);
    }
  }

  default <T1 extends NameValuePair, T2 extends NameValuePair> void verifyNameValuePair(Collection<T1> actuals, T2 expected) {
    T1 actual = actuals.stream().filter(m -> Objects.equals(m.getName(), expected.getName())).findFirst().orElse(null);
    assertThat(actual, notNullValue());

    assertThat(actual.getName(), equalTo(expected.getName()));
    assertThat(actual.getValue(), equalTo(expected.getValue()));
  }

}