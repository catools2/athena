package org.catools.common.tests.types;

import lombok.extern.slf4j.Slf4j;
import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;
import org.apache.commons.lang3.RandomStringUtils;
import org.catools.common.configs.CPathConfigs;
import org.catools.common.exception.CFileOperationException;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.io.CFile;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.catools.common.utils.CFileUtil;
import org.catools.common.utils.CInputStreamUtil;
import org.catools.common.utils.CSleeper;
import org.catools.common.utils.CStringUtil;
import org.testng.annotations.Test;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Test(singleThreaded = true)
public class CFileTest extends CBaseUnitTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testAppendBytes() {
    CFile file1 = getValidFile().append("A\nB\n".getBytes()).append("C\nD\n".getBytes());
    CVerify.String.equals(file1.readLines().join("\n"), "A\nB\nC\nD", "CFile -> append Works fine");
  }

  @Test(
      retryAnalyzer = CTestRetryAnalyzer.class,
      expectedExceptions = CFileOperationException.class)
  public void testAppendBytes_N() {
    getInvalidFile().append("A\nB\nC\nD\n".getBytes());
  }

  @Test(
      retryAnalyzer = CTestRetryAnalyzer.class,
      expectedExceptions = CFileOperationException.class)
  public void testAppend_N() {
    getInvalidFile().append("A\nB\nC\nD\n", StandardCharsets.US_ASCII);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testAppend_ReadLines() {
    CFile file1 = getValidFile().append("A\nB\n").append("C\nD\n");
    CVerify.String.equals(
        file1.readLines().join("\n"), "A\nB\nC\nD", "CFile -> read lines Works fine");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testAppend_ReadLines1() {
    CFile file1 =
        getValidFile()
            .append("A\nB\n", StandardCharsets.US_ASCII)
            .append("C\nD\n", StandardCharsets.US_ASCII);
    CVerify.String.equals(
        file1.readLines(Charset.forName("ASCII")).join("\n"),
        "A\nB\nC\nD",
        "CFile -> read lines Works fine");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCopyFile() {
    CFile file1 = getValidFile().write("ABCD");
    CFile file2 = getValidFile();
    file1.copyFile(file2);
    CVerify.Bool.isTrue(
        file1.exists(), "CFile -> copyFile does not delete source file when do copy");
    CVerify.String.equals(file2.readString(), "ABCD", "CFile -> copyFile method worked fine");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCopy_Directory() {
    String randomFileName = getValidFileName();
    CFile directory1 = CFile.fromTmp("./testCopy_Directory/");
    directory1.mkdirs();
    CFile file = directory1.getChildFile(randomFileName);
    file.createNewFile();
    CFile directory2 = directory1.copy(CFile.fromTmp("./testCopy_Directory2/"));
    CVerify.File.equalsStringContent(
        directory2.getChildFile(randomFileName),
        file,
        "CFile -> copy method copied directory with file");
  }

  @Test(
      retryAnalyzer = CTestRetryAnalyzer.class,
      expectedExceptions = CFileOperationException.class)
  public void testCopy_Directory_N() {
    String randomFileName = getValidFileName();
    CFile directory1 = CFile.fromTmp("./testCopy_Directory/");
    directory1.mkdirs();
    CFile file = directory1.getChildFile(randomFileName);
    file.createNewFile();
    directory1.copy(getInvalidFile());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCopy_File() {
    CFile file1 = getValidFile();
    CFile file2 = getValidFile();
    file1.write("ABC");
    file1.copy(file2);
    CVerify.File.equalsStringContent(file1, file2, "CFile -> copy method copied file");
  }

  @Test(
      retryAnalyzer = CTestRetryAnalyzer.class,
      expectedExceptions = CFileOperationException.class)
  public void testCopy_N() {
    CFile file1 = getValidFile();
    CFile file2 = getInvalidFile();
    file1.write("ABC");
    file1.copy(file2);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCreateNewFile() {
    CFile cFile = getValidFile();
    CVerify.Bool.isTrue(
        cFile.createNewFile(),
        "CFile -> createNewFile returns true if file is new and it is created");

    cFile.write("ABC");

    CVerify.Bool.isFalse(
        cFile.createNewFile(), "CFile -> createNewFile returns true if already exists");

    CVerify.String.equals(
        cFile.readString(), "ABC", "CFile -> createNewFile Does not overwrite exists file");
  }

  @Test(
      retryAnalyzer = CTestRetryAnalyzer.class,
      expectedExceptions = CFileOperationException.class)
  public void testCreateNewFile_N() {
    getInvalidFile().createNewFile();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testDeleteDirectory() {
    CFile directory = CFile.fromTmp("./testDeleteDirectory/");
    directory.mkdirs();
    CFile.fromTmp("./testDeleteDirectory/" + getValidFileName()).createNewFile();
    CFile.fromTmp("./testDeleteDirectory/" + getValidFileName()).createNewFile();
    CVerify.Bool.isTrue(
        directory.delete(), "CFile -> delete returns true if file is successfully deleted folder");
    CVerify.Bool.isFalse(directory.exists(), "CFile -> deleted folder does not exist");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testDeleteDirectory_N() {
    CFile directory = CFile.fromTmp("./testDeleteDirectory_N/");
    directory.mkdirs();
    CVerify.Bool.isFalse(
        CFile.fromTmp("./testDeleteDirectory_N/" + getInvalidFile()).delete(),
        "CFile -> delete cannot delete if directory name is not valid");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testDeleteFile() {
    CFile cFile = getValidFile();
    CVerify.Bool.isTrue(
        cFile.createNewFile(),
        "CFile -> createNewFile returns true if file is new and it is created");
    CVerify.Bool.isTrue(
        cFile.delete(), "CFile -> delete returns true if file is successfully deleted");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testDelete_InvalidDirectory() {
    AtomicBoolean firstCallSignal = new AtomicBoolean();
    new MockUp<CFile>() {
      @Mock
      public boolean isDirectory(Invocation inv) {
        if (!firstCallSignal.get()) {
          firstCallSignal.set(true);
          return true;
        }
        return inv.proceed();
      }
    };
    CVerify.Bool.isFalse(
        getInvalidFile().delete(), "CFile -> delete cannot delete if file name is not valid");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testDelete_InvalidFile() {
    CVerify.Bool.isFalse(
        getInvalidFile().delete(), "CFile -> delete cannot delete if file name is not valid");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = RuntimeException.class)
  public void testForceDelete_N() {
    getInvalidFile().forceDelete();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testForceDelete_directory() {
    CFile file1 = CFile.fromTmp("./testForceDelete_directory/A/B/C/D");
    file1.mkdirs();
    file1.getChildFile(getValidFileName()).write("ABCD");
    file1 = CFile.fromTmp("./testForceDelete_directory/");
    file1.forceDelete();
    CVerify.Bool.isFalse(file1.exists(), "CFile -> forceDelete delete source directory");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testForceDelete_file() {
    CFile file1 = getValidFile().write("ABCD");
    file1.forceDelete();
    CVerify.Bool.isFalse(file1.exists(), "CFile -> forceDelete delete source file");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testForceMkdirs() {
    CFile directory1 = CFile.fromTmp("./a/b/c/d/e/f/g/h");
    CVerify.Bool.isTrue(
        directory1.forceMkdirs(), "CFile -> mkdirs return true if it creates directories");
    CVerify.Bool.isTrue(directory1.exists(), "CFile -> mkdirs creates directories");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testFromOutput() {
    CVerify.String.startsWith(
        CFile.fromOutput(getValidFileName()).getCanonicalPath(),
        CPathConfigs.getOutputPath(),
        "CFile -> fromRoot points to file in output folder");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testFromCurrent() {
    CVerify.String.startsWith(
        CFile.fromCurrent(getValidFileName()).getCanonicalPath(),
        new CFile(".").getCanonicalPath(),
        "CFile -> fromRoot points to file in current folder");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testFromStorage() {
    CVerify.String.startsWith(
        CFile.fromStorage(getValidFileName()).getCanonicalPath(),
        CFileUtil.getCanonicalPath(CPathConfigs.getStorageFolder()),
        "CFile -> fromRoot points to file in storage folder");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testFromTmp() {
    CVerify.String.startsWith(
        getValidFile().getCanonicalPath(),
        CFileUtil.getCanonicalPath(CPathConfigs.getTempFolder()),
        "CFile -> fromTemp points to file in temp folder");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetCanonicalFile() {
    CFile randomTmpFile = getValidFile();
    CVerify.String.endsWith(
        randomTmpFile.getCanonicalFile().getCanonicalPath(),
        randomTmpFile.getCanonicalPath(),
        "CFile -> getCanonicalFile points to getCanonicalPath of the file");
  }

  @Test(
      retryAnalyzer = CTestRetryAnalyzer.class,
      expectedExceptions = CFileOperationException.class)
  public void testGetCanonicalFile_N() {
    getInvalidFile().getCanonicalFile();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetCanonicalPath() {
    CVerify.String.endsWith(
        getValidFile().getCanonicalPath(),
        CStringUtil.EMPTY,
        "CFile -> getCanonicalPath returns the right value");
  }

  @Test(
      retryAnalyzer = CTestRetryAnalyzer.class,
      expectedExceptions = CFileOperationException.class)
  public void testGetCanonicalPath_N() {
    getInvalidFile().getCanonicalPath();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetChildFile() {
    CFile directory = CFile.fromTmp("./testGetChildFile/");
    directory.mkdirs();
    CFile file = directory.getChildFile(getValidFileName());
    file.createNewFile();
    CVerify.String.equals(
        file.getParentFile().getCanonicalPath(),
        directory.getCanonicalPath(),
        "CFile -> getChildFile point to the file in parent folder");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetParentFile() {
    CFile directory = CFile.fromTmp("./testGetParentFile/");
    directory.mkdirs();
    CFile file = CFile.fromTmp("./testGetParentFile/" + getValidFileName());
    file.createNewFile();
    CVerify.String.equals(
        file.getParentFile().getCanonicalPath(),
        directory.getCanonicalPath(),
        "CFile -> getParentFile returns correct value");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testMoveTo() {
    CFile file1 = getValidFile().write("ABCD");
    CFile file2 = getValidFile().write("ABC");
    file1.moveTo(file2);
    CVerify.Bool.isFalse(file1.exists(), "CFile -> moveTo deleted source file when do move");
    CVerify.String.equals(file2.readString(), "ABCD", "CFile -> moveTo method worked fine");
  }

  @Test(
      retryAnalyzer = CTestRetryAnalyzer.class,
      expectedExceptions = CFileOperationException.class)
  public void testReadLines_N() {
    getInvalidFile().readLines();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReadString() {
    CFile file1 = getValidFile().write("A\nB\nC\nD\n");
    CVerify.String.equals(file1.readString(), "A\nB\nC\nD\n", "CFile -> read String Works fine");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReadString1() {
    CFile file1 = getValidFile().write("A\nB\nC\nD\n", Charset.forName("ASCII"));
    CVerify.String.equals(
        file1.readString(Charset.forName("ASCII")),
        "A\nB\nC\nD\n",
        "CFile -> read String Works fine");
  }

  @Test(
      retryAnalyzer = CTestRetryAnalyzer.class,
      expectedExceptions = CFileOperationException.class)
  public void testReadString_N() {
    getInvalidFile().readString();
  }

  @Test(
      retryAnalyzer = CTestRetryAnalyzer.class,
      expectedExceptions = CFileOperationException.class)
  public void testSafeRename_CannotDeleteDestFile() {
    CFile file1 = getValidFile().write("ABCD");
    CFile file2 = getValidFile().write("ABC");

    new MockUp<File>() {
      @Mock
      public boolean delete() {
        return false;
      }
    };

    file1.safeRename(file2, true);
  }

  @Test(
      retryAnalyzer = CTestRetryAnalyzer.class,
      expectedExceptions = CFileOperationException.class)
  public void testSafeRename_CannotDeleteSrcFile() {
    CFile file1 = getValidFile().write("ABCD");
    CFile file2 = getValidFile().write("ABC");

    AtomicBoolean deleteFlag = new AtomicBoolean();
    new MockUp<CFile>() {
      @Mock
      public boolean delete(Invocation inv) {
        if (!deleteFlag.get()) {
          deleteFlag.set(true);
          return false;
        }
        return Boolean.TRUE.equals(inv.proceed());
      }
    };

    file1.safeRename(file2, true);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSafeRename_FileNotExists() {
    CFile file1 = getValidFile();
    CFile file2 = getValidFile();
    file1.write("ABC");
    file1.safeRename(file2, false);
    CVerify.Bool.isFalse(file1.exists(), "CFile -> safeRenamed deleted source file");
    CVerify.String.equals(file2.readString(), "ABC", "CFile -> safeRenamed method renamed file");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSafeRename_NoOverwrite_FileExists() {
    CFile file1 = getValidFile().write("ABC");
    CFile file2 = getValidFile().write("ABCD");
    file1.safeRename(file2, false);
    CVerify.File.notEqualsStringContent(
        file1, file2, "CFile -> safeRenamed method did not override file");
    CVerify.Bool.isTrue(
        file1.exists(),
        "CFile -> safeRenamed does not deleted source file when do not override file");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSafeRename_Overwrite_FileExists() {
    CFile file1 = getValidFile().write("ABCD");
    CFile file2 = getValidFile().write("ABC");
    file1.safeRename(file2, true);
    CVerify.Bool.isFalse(
        file1.exists(), "CFile -> safeRenamed deleted source file when override file");
    CVerify.String.equals(file2.readString(), "ABCD", "CFile -> safeRenamed method renamed file");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testToInputStream() {
    CVerify.String.equals(
        CInputStreamUtil.read(getValidFile().write("ABCD").getInputStream()),
        "ABCD",
        "CFile -> testToInputStream delete source file");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = RuntimeException.class)
  public void testToInputStream_N() {
    getInvalidFile().getInputStream();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testWaitForWritable1() {
    CFile file1 = getValidFile();
    new Thread(
        () -> {
          CSleeper.sleepTightInSeconds(1);
          file1.createNewFile();
        })
        .run();
    CVerify.Bool.isTrue(file1.waitForWritable(2), "CFile -> waitForWritable works fine");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testWaitForWritable3() {
    CFile file1 = getValidFile();
    new Thread(
        () -> {
          CSleeper.sleepTightInSeconds(1);
          file1.createNewFile();
        })
        .run();
    CVerify.Bool.isTrue(file1.waitForWritable(2, 100), "CFile -> waitForWritable works fine");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testWaitToExists() {
    CFile file1 = getValidFile();
    new Thread(
        () -> {
          CSleeper.sleepTightInSeconds(1);
          file1.write("ABCD");
        })
        .run();
    CVerify.Bool.isTrue(file1.waitToExists(2), "CFile -> waitToExists works fine");
    CVerify.String.equals(file1.readString(), "ABCD", "CFile -> waitToExists works fine");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testWaitToExists1() {
    CFile file1 = getValidFile();
    new Thread(
        () -> {
          CSleeper.sleepTightInSeconds(1);
          file1.write("ABCD");
        })
        .run();
    CVerify.Bool.isTrue(file1.waitToExists(2, 100), "CFile -> waitToExists works fine");
    CVerify.String.equals(file1.readString(), "ABCD", "CFile -> waitToExists works fine");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testWriteBytes() {
    CFile file1 = getValidFile().write("A\nB\nC\nD\n".getBytes());
    CVerify.String.equals(
        file1.readLines().join("\n"), "A\nB\nC\nD", "CFile -> read lines Works fine");
  }

  @Test(
      retryAnalyzer = CTestRetryAnalyzer.class,
      expectedExceptions = CFileOperationException.class)
  public void testWriteBytes_N() {
    getInvalidFile().write("A\nB\nC\nD\n".getBytes());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testWrite_DoesNotAppend() {
    CFile file1 = getValidFile().write("A\nB\nC\nD\n").write("A\nB\nC\nD\n");
    CVerify.String.equals(
        file1.readLines().join("\n"), "A\nB\nC\nD", "CFile -> read lines Works fine");
  }

  @Test(
      retryAnalyzer = CTestRetryAnalyzer.class,
      expectedExceptions = CFileOperationException.class)
  public void testWrite_N() {
    getInvalidFile().write("A\nB\nC\nD\n", Charset.forName("ASCII"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testWrite_ReadLines() {
    CFile file1 = getValidFile().write("A\nB\nC\nD\n");
    CVerify.String.equals(
        file1.readLines().join("\n"), "A\nB\nC\nD", "CFile -> read lines Works fine");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testWrite_ReadLines1() {
    CFile file1 = getValidFile().write("A\nB\nC\nD\n", Charset.forName("ASCII"));
    CVerify.String.equals(
        file1.readLines(Charset.forName("ASCII")).join("\n"),
        "A\nB\nC\nD",
        "CFile -> read lines Works fine");
  }

  private CFile getInvalidFile() {
    return new CFile(CFile.fromTmp(getInvalidFileName()));
  }

  private String getInvalidFileName() {
    return RandomStringUtils.randomAlphabetic(10) + '\u0000' + ".file";
  }

  private String getValidFileName() {
    return RandomStringUtils.randomAlphabetic(10) + ".file";
  }

  private CFile getValidFile() {
    return CFile.fromTmp(getValidFileName());
  }
}
