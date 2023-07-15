package org.catools.web.factory;

import org.catools.common.collections.CList;
import org.catools.common.utils.CStringUtil;
import org.catools.web.collections.CWebElements;
import org.catools.web.controls.CWebElement;
import org.catools.web.drivers.CDriverWaiter;
import org.catools.web.pages.CWebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Objects;

public class CWebElementFactory {
  /**
   * Similar to the other "initElements" methods, but takes an {@link FieldDecorator} which is used
   * for decorating each of the fields.
   *
   * @param component The CWebComponent child to decorate the fields of
   */
  public static void initElements(CWebComponent<?> component) {
    Class<?> proxyIn = component.getClass();
    while (proxyIn != Object.class) {
      decorateFields(component, proxyIn);
      proxyIn = proxyIn.getSuperclass();
    }
  }

  private static void decorateFields(CWebComponent<?> component, Class<?> proxyIn) {
    Field[] fields = proxyIn.getDeclaredFields();
    for (Field field : fields) {
      Object value = decorateField(component, field);
      if (value != null) {
        try {
          field.setAccessible(true);
          field.set(component, value);
        } catch (IllegalAccessException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }


  private static Object decorateField(CWebComponent<?> component, Field field) {
    if (!isDecoratable(field)) {
      return null;
    }

    assertValidAnnotations(field);

    if (CWebElement.class.isAssignableFrom(field.getType())) {
      return buildWebElement(component, field);
    } else if (CWebElements.class.isAssignableFrom(field.getType())) {
      return buildWebElements(component, field);
    } else {
      return null;
    }
  }

  private static boolean isDecoratable(Field field) {
    return field.getType().equals(CWebElement.class) || field.getType().equals(CWebElements.class);
  }

  private static CWebElement<?> buildWebElement(CWebComponent<?> component, Field field) {
    CFindBy cFindBYInfo = field.getAnnotation(CFindBy.class);
    FindBy findBYInfo = field.getAnnotation(FindBy.class);

    String name = field.getName();
    int timeout = CDriverWaiter.DEFAULT_TIMEOUT;

    By by;
    if (cFindBYInfo != null) {
      if (cFindBYInfo.findBy() == null) {
        by = new ByIdOrName(field.getName());
      } else {
        by = new FindBy.FindByBuilder().buildIt(cFindBYInfo.findBy(), field);
      }

      if (CStringUtil.isNotBlank(cFindBYInfo.name())) {
        name = cFindBYInfo.name();
      }

      if (cFindBYInfo.waitInSeconds() > 0)
        timeout = cFindBYInfo.waitInSeconds();
    } else if (findBYInfo != null) {
      by = new FindBy.FindByBuilder().buildIt(findBYInfo, field);
    } else {
      by = new ByIdOrName(field.getName());
    }

    return new CWebElement<>(name, component.getDriver(), by, timeout);
  }

  private static CWebElements<?> buildWebElements(CWebComponent<?> component, Field field) {
    CFindBys elementInfo = field.getAnnotation(CFindBys.class);
    if (CStringUtil.isBlank(elementInfo.xpath()))
      throw new IllegalArgumentException("CWebElements requires xpath (String) to find element.");

    String name = CStringUtil.defaultString(elementInfo.name(), field.getName());

    if (elementInfo.waitForFirstElementInSecond() < 0)
      return new CWebElements<>(name, component.getDriver(), elementInfo.xpath());

    if (elementInfo.waitForOtherElementInSecond() < 0)
      return new CWebElements<>(name, component.getDriver(), elementInfo.xpath(), elementInfo.waitForFirstElementInSecond());

    return new CWebElements<>(name, component.getDriver(), elementInfo.xpath(), elementInfo.waitForFirstElementInSecond(), elementInfo.waitForOtherElementInSecond());
  }

  private static void assertValidAnnotations(Field field) {
    CList<Annotation> seleniumAnnotatoins = CList.of(
        field.getAnnotation(FindBys.class),
        field.getAnnotation(FindAll.class));

    if (seleniumAnnotatoins.getAll(Objects::nonNull).isNotEmpty()) {
      throw new IllegalArgumentException("You should only use CFindBys or CFindBy or FindBy annotation! (Attention to first Letter 'C')");
    }

    CFindBys cFindBys = field.getAnnotation(CFindBys.class);
    CList<Annotation> catoolsAnnotations = CList.of(cFindBys, field.getAnnotation(CFindBy.class));

    if (catoolsAnnotations.getAll(Objects::nonNull).size() > 1) {
      throw new IllegalArgumentException("You should use only one of CFindBys or CFindBy or FindBy annotation!");
    }

    if (CWebElements.class.isAssignableFrom(field.getType()) && cFindBys == null) {
      throw new IllegalArgumentException("You should use CFindBys for CWebElements elements!");
    }

    if (CWebElement.class.isAssignableFrom(field.getType()) && cFindBys != null) {
      throw new IllegalArgumentException("You should use CFindBy or FindBy for CWebElement elements!");
    }

    // Note that CWebElement default behaviour is to use by id or name, and it technically can exist without CFindBy
  }
}
