package org.catools.media.tests.extensions;

import org.catools.common.io.CFile;
import org.catools.media.extensions.wait.interfaces.CImageComparisonWaiter;

public class CImageComparisonFileWaiterTest extends CBaseImageComparisonWaiterTest<CFile> {

  @Override
  protected CImageComparisonWaiter<CFile> toWaiter() {
    return () -> FROG_FILE;
  }
}