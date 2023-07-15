package org.catools.common.extensions.states.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.catools.common.extensions.verify.interfaces.CBaseVerify;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * CBaseExtension is an interface to hold shared method between all extensions.
 */
public interface CBaseState<O> extends Serializable {
  long serialVersionUID = 6067874018185613757L;
  Logger logger = LoggerFactory.getLogger(CBaseVerify.class);

  /**
   * For internal use only
   */
  @JsonIgnore
  O _get();
}
