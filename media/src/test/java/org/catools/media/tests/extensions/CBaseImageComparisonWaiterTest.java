package org.catools.media.tests.extensions;

import boofcv.factory.template.TemplateScoreType;
import org.catools.common.collections.CList;
import org.catools.common.configs.CPathConfigs;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.io.CFile;
import org.catools.common.io.CResource;
import org.catools.media.configs.CMediaConfigs;
import org.catools.media.extensions.wait.interfaces.CImageComparisonWaiter;
import org.catools.media.utils.CImageUtil;
import org.testng.annotations.Test;

import java.awt.image.BufferedImage;
import java.io.File;

public abstract class CBaseImageComparisonWaiterTest<O> {
  protected static final CResource FROG_RESOURCE = new CResource("testData/frog.jpg");
  protected static final CResource FROG2_RESOURCE = new CResource("testData/frog2.jpg");
  protected static final CResource FROG_EYE_RESOURCE = new CResource("testData/frog_eye.jpg");
  protected static final BufferedImage FROG_BUFFERED_IMAGE = CImageUtil.readImageOrNull(FROG_RESOURCE);
  protected static final BufferedImage FROG2_BUFFERED_IMAGE = CImageUtil.readImageOrNull(FROG2_RESOURCE);
  protected static final BufferedImage FROG_EYE_BUFFERED_IMAGE = CImageUtil.readImageOrNull(FROG_EYE_RESOURCE);
  protected static final CFile FROG_FILE = FROG_RESOURCE.saveToFolder(CPathConfigs.getTempChildFolder("testData"));
  protected static final CFile FROG2_FILE = FROG2_RESOURCE.saveToFolder(CPathConfigs.getTempChildFolder("testData"));
  protected static final CFile FROG_EYE_FILE = FROG_EYE_RESOURCE.saveToFolder(CPathConfigs.getTempChildFolder("testData"));

  @Test
  public void waitEquals() {
    CVerify.Bool.isTrue(toWaiter().waitEquals(FROG_BUFFERED_IMAGE));
    CVerify.Bool.isTrue(toWaiter().waitEquals(FROG_FILE));
    CVerify.Bool.isTrue(toWaiter().waitEquals(FROG_RESOURCE));
    CVerify.Bool.isTrue(toWaiter().waitEquals(FROG_BUFFERED_IMAGE, 1, 100));
    CVerify.Bool.isTrue(toWaiter().waitEquals(FROG_FILE, 1, 100));
    CVerify.Bool.isTrue(toWaiter().waitEquals(FROG_RESOURCE, 1, 100));
  }

  @Test
  public void waitNotEquals() {
    CVerify.Bool.isTrue(toWaiter().waitNotEquals(FROG2_BUFFERED_IMAGE));
    CVerify.Bool.isTrue(toWaiter().waitNotEquals(FROG2_FILE));
    CVerify.Bool.isTrue(toWaiter().waitNotEquals(FROG2_RESOURCE));
    CVerify.Bool.isTrue(toWaiter().waitNotEquals(FROG2_BUFFERED_IMAGE, 1, 100));
    CVerify.Bool.isTrue(toWaiter().waitNotEquals(FROG2_FILE, 1, 100));
    CVerify.Bool.isTrue(toWaiter().waitNotEquals(FROG2_RESOURCE, 1, 100));
  }

  @Test
  public void waitEqualsAny() {
    CVerify.Bool.isTrue(toWaiter().waitEqualsAny(CList.of(FROG_EYE_BUFFERED_IMAGE, FROG_FILE, FROG2_FILE)));
    CVerify.Bool.isTrue(toWaiter().waitEqualsAny(CList.of(FROG_EYE_BUFFERED_IMAGE, FROG_FILE, FROG2_FILE), 1, 100));
  }

  @Test
  public void waitEqualsNone() {
    CVerify.Bool.isTrue(toWaiter().waitEqualsNone(CList.of(FROG_EYE_BUFFERED_IMAGE, FROG2_FILE)));
    CVerify.Bool.isTrue(toWaiter().waitEqualsNone(CList.of(FROG_EYE_BUFFERED_IMAGE, FROG2_FILE), 1, 100));
  }

  @Test
  public void waitContains() {
    CVerify.Bool.isTrue(toWaiter().waitContains(FROG_EYE_BUFFERED_IMAGE));
    CVerify.Bool.isTrue(toWaiter().waitContains(FROG_EYE_FILE));
    CVerify.Bool.isTrue(toWaiter().waitContains(FROG_EYE_RESOURCE));
    CVerify.Bool.isTrue(toWaiter().waitContains(FROG_EYE_BUFFERED_IMAGE, 1, 100));
    CVerify.Bool.isTrue(toWaiter().waitContains(FROG_EYE_FILE, 1, 100));
    CVerify.Bool.isTrue(toWaiter().waitContains(FROG_EYE_RESOURCE, 1, 100));
    CVerify.Bool.isTrue(toWaiter().waitContains(FROG_EYE_BUFFERED_IMAGE, TemplateScoreType.NCC, (BufferedImage) null, CMediaConfigs.getDefaultMatchPrecision()));
    CVerify.Bool.isTrue(toWaiter().waitContains(FROG_EYE_BUFFERED_IMAGE, TemplateScoreType.NCC, (File) null, CMediaConfigs.getDefaultMatchPrecision()));
    CVerify.Bool.isTrue(toWaiter().waitContains(FROG_EYE_BUFFERED_IMAGE, TemplateScoreType.NCC, (CResource) null, CMediaConfigs.getDefaultMatchPrecision()));
    CVerify.Bool.isTrue(toWaiter().waitContains(FROG_EYE_FILE, TemplateScoreType.NCC, (BufferedImage) null, CMediaConfigs.getDefaultMatchPrecision()));
    CVerify.Bool.isTrue(toWaiter().waitContains(FROG_EYE_FILE, TemplateScoreType.NCC, (File) null, CMediaConfigs.getDefaultMatchPrecision()));
    CVerify.Bool.isTrue(toWaiter().waitContains(FROG_EYE_FILE, TemplateScoreType.NCC, (CResource) null, CMediaConfigs.getDefaultMatchPrecision()));
    CVerify.Bool.isTrue(toWaiter().waitContains(FROG_EYE_RESOURCE, TemplateScoreType.NCC, (BufferedImage) null, CMediaConfigs.getDefaultMatchPrecision()));
    CVerify.Bool.isTrue(toWaiter().waitContains(FROG_EYE_RESOURCE, TemplateScoreType.NCC, (File) null, CMediaConfigs.getDefaultMatchPrecision()));
    CVerify.Bool.isTrue(toWaiter().waitContains(FROG_EYE_RESOURCE, TemplateScoreType.NCC, (CResource) null, CMediaConfigs.getDefaultMatchPrecision()));
  }

  @Test
  public void waitNotContains() {
    CVerify.Bool.isTrue(toWaiter().waitNotContains(FROG2_BUFFERED_IMAGE));
    CVerify.Bool.isTrue(toWaiter().waitNotContains(FROG2_FILE));
    CVerify.Bool.isTrue(toWaiter().waitNotContains(FROG2_RESOURCE));
    CVerify.Bool.isTrue(toWaiter().waitNotContains(FROG2_BUFFERED_IMAGE, 1, 100));
    CVerify.Bool.isTrue(toWaiter().waitNotContains(FROG2_FILE, 1, 100));
    CVerify.Bool.isTrue(toWaiter().waitNotContains(FROG2_RESOURCE, 1, 100));
    CVerify.Bool.isTrue(toWaiter().waitNotContains(FROG2_BUFFERED_IMAGE, TemplateScoreType.NCC, (BufferedImage) null, CMediaConfigs.getDefaultMatchPrecision()));
    CVerify.Bool.isTrue(toWaiter().waitNotContains(FROG2_BUFFERED_IMAGE, TemplateScoreType.NCC, (File) null, CMediaConfigs.getDefaultMatchPrecision()));
    CVerify.Bool.isTrue(toWaiter().waitNotContains(FROG2_BUFFERED_IMAGE, TemplateScoreType.NCC, (CResource) null, CMediaConfigs.getDefaultMatchPrecision()));
    CVerify.Bool.isTrue(toWaiter().waitNotContains(FROG2_FILE, TemplateScoreType.NCC, (BufferedImage) null, CMediaConfigs.getDefaultMatchPrecision()));
    CVerify.Bool.isTrue(toWaiter().waitNotContains(FROG2_FILE, TemplateScoreType.NCC, (File) null, CMediaConfigs.getDefaultMatchPrecision()));
    CVerify.Bool.isTrue(toWaiter().waitNotContains(FROG2_FILE, TemplateScoreType.NCC, (CResource) null, CMediaConfigs.getDefaultMatchPrecision()));
    CVerify.Bool.isTrue(toWaiter().waitNotContains(FROG2_RESOURCE, TemplateScoreType.NCC, (BufferedImage) null, CMediaConfigs.getDefaultMatchPrecision()));
    CVerify.Bool.isTrue(toWaiter().waitNotContains(FROG2_RESOURCE, TemplateScoreType.NCC, (File) null, CMediaConfigs.getDefaultMatchPrecision()));
    CVerify.Bool.isTrue(toWaiter().waitNotContains(FROG2_RESOURCE, TemplateScoreType.NCC, (CResource) null, CMediaConfigs.getDefaultMatchPrecision()));
  }

  @Test
  public void waitContainsAny() {
    CVerify.Bool.isTrue(toWaiter().waitContainsAny(CList.of(FROG_EYE_BUFFERED_IMAGE, FROG2_FILE)));
    CVerify.Bool.isTrue(toWaiter().waitContainsAny(CList.of(FROG_EYE_BUFFERED_IMAGE, FROG2_FILE), 1, 100));
    CVerify.Bool.isTrue(toWaiter().waitContainsAny(CList.of(FROG_EYE_BUFFERED_IMAGE, FROG2_FILE), TemplateScoreType.NCC, (BufferedImage) null, CMediaConfigs.getDefaultMatchPrecision()));
    CVerify.Bool.isTrue(toWaiter().waitContainsAny(CList.of(FROG_EYE_BUFFERED_IMAGE, FROG2_FILE), TemplateScoreType.NCC, (File) null, CMediaConfigs.getDefaultMatchPrecision()));
    CVerify.Bool.isTrue(toWaiter().waitContainsAny(CList.of(FROG_EYE_BUFFERED_IMAGE, FROG2_FILE), TemplateScoreType.NCC, (CResource) null, CMediaConfigs.getDefaultMatchPrecision()));
  }

  @Test
  public void waitContainsNone() {
    CVerify.Bool.isTrue(toWaiter().waitContainsNone(CList.of(FROG2_BUFFERED_IMAGE, FROG2_FILE)));
    CVerify.Bool.isTrue(toWaiter().waitContainsNone(CList.of(FROG2_BUFFERED_IMAGE, FROG2_FILE), 1, 100));
  }

  protected abstract CImageComparisonWaiter<O> toWaiter();

}