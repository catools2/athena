package org.catools.common.extensions.verify;

import lombok.extern.slf4j.Slf4j;
import org.catools.common.extensions.verify.hard.*;

@Slf4j
public class CVerify {

  public static final CObjectVerification Object = new CObjectVerification();
  public static final CCollectionVerification Collection = new CCollectionVerification();
  public static final CMapVerification Map = new CMapVerification();
  public static final CBooleanVerification Bool = new CBooleanVerification();
  public static final CDateVerification Date = new CDateVerification();
  public static final CStringVerification String = new CStringVerification();
  public static final CFileVerification File = new CFileVerification();
  public static final CNumberVerification<Long> Long = new CNumberVerification<>();
  public static final CNumberVerification<java.math.BigDecimal> BigDecimal = new CNumberVerification<>();
  public static final CNumberVerification<Double> Double = new CNumberVerification<>();
  public static final CNumberVerification<Float> Float = new CNumberVerification<>();
  public static final CNumberVerification<Integer> Int = new CNumberVerification<>();
}
