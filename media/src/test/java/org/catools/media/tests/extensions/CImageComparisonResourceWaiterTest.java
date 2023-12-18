package org.catools.media.tests.extensions;

import org.catools.common.io.CResource;
import org.catools.media.extensions.wait.interfaces.CImageComparisonWaiter;

public class CImageComparisonResourceWaiterTest extends CBaseImageComparisonWaiterTest<CResource> {

  @Override
  protected CImageComparisonWaiter<CResource> toWaiter() {
    return () -> FROG_RESOURCE;
  }
}