package org.catools.web.factory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface CFindBys {
  String xpath();

  String name() default "";

  int waitForFirstElementInSecond() default -1;

  int waitForOtherElementInSecond() default -1;
}
