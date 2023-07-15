package org.catools.common.tests.concurrent;

import org.catools.common.concurrent.CStorage;
import org.catools.common.concurrent.exceptions.CThreadTimeoutException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class CStorageTest {

  @Test
  public void testInit() {
    CStorage<Integer> storage = new CStorage<>("Test1", 1, 5);
    storage.init(List.of(1, 2, 3, 4, 5, 6));
    Assert.assertEquals(storage.getAvailableSize(), 6, "storage returned correct available size");
    Assert.assertEquals(storage.getBorrowedSize(), 0, "storage returned correct borrowed size");
  }

  @Test
  public void testPerformAction() {
    CStorage<Integer> storage = new CStorage<>("Test1", 1, 5);
    storage.init(List.of(1));
    storage.performAction("Test2", idx -> idx + 1);
    storage.performAction("Test2", idx -> idx + 1);
    Assert.assertEquals(storage.getAvailableSize(), 1, "storage returned correct available size");
    Assert.assertEquals(storage.getBorrowedSize(), 0, "storage returned correct borrowed size");
  }

  @Test(expectedExceptions = CThreadTimeoutException.class)
  public void testTimeout() {
    CStorage<Integer> storage = new CStorage<>("Test1", 1, 2);
    storage.init(List.of(1));
    storage.borrow("Test2", idx -> idx > 1);
  }

  @Test
  public void testBorrowAndRelease() {
    CStorage<Integer> storage = new CStorage<>("Test1", 1, 5);
    storage.init(List.of(1, 2, 3, 4, 5, 6));
    Integer test1 = storage.borrow("Test1");
    Assert.assertEquals(storage.getAvailableSize(), 5, "storage returned correct available size");
    Assert.assertEquals(storage.getBorrowedSize(), 1, "storage returned correct borrowed size");
    storage.release(test1);
    Assert.assertEquals(storage.getAvailableSize(), 6, "storage returned correct available size");
    Assert.assertEquals(storage.getBorrowedSize(), 0, "storage returned correct borrowed size");
  }
}