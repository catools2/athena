package org.catools.etl.git.utils;

import org.catools.common.date.CDate;
import org.catools.common.extensions.verify.CVerify;
import org.catools.etl.git.configs.CGitConfigRepo;
import org.catools.etl.git.configs.CGitConfigs;
import org.catools.etl.git.dao.CGitCommitDao;
import org.catools.etl.git.model.CGitCommit;
import org.testng.annotations.Test;

@Test(singleThreaded = true)
public class CGitLoaderTest {
  @Test
  public void testLoadRepo() {
    CGitConfigRepo repository = CGitConfigs.getRepositories().get(0);
    CGitLoader.loadRepository("repo1", repository.getUrl(), 10);
    verifyCommit();
  }

  @Test
  public void testReadRepoSince() {
    deleteCommit("86cc2645a58c6d33056bd97d280d5c0d838b8195");
    CDate from = CDate.valueOf("2020/6/9", "YYYY/dd/MM");
    CDate to = CDate.of(from).addDays(2);
    CGitConfigRepo repository = CGitConfigs.getRepositories().get(0);
    CGitLoader.loadRepository("repo2", repository.getUrl(), from, to, 10);
    verifyCommit();
  }

  private void deleteCommit(String hash) {
    CGitCommit commit = CGitCommitDao.getByHash(hash);
    CGitCommitDao.doTransaction(entityManager -> {
      entityManager.remove(entityManager.find(CGitCommit.class, commit.getId()));
      return true;
    });
  }

  private static void verifyCommit() {
    CGitCommit commit = CGitCommitDao.getByHash("86cc2645a58c6d33056bd97d280d5c0d838b8195");
    CVerify.String.equals(commit.getAuthor().getName(), "akeshmiri", "The author name is correct");
    CVerify.String.equals(commit.getCommitter().getName(), "akeshmiri", "The committer name is correct");
    CVerify.String.equals(commit.getShortMessage(), "Merge modules to one for easier development by my fellow friends", "The short message is correct");
    CVerify.String.equals(commit.getFullMessage(), "Merge modules to one for easier development by my fellow friends\n", "The full message is correct");
    CVerify.Int.equals(commit.getBranches().size(), 1, "The total branches size is correct");
    CVerify.Int.equals(commit.getTags().size(), 13, "The total tags size is correct");
    CVerify.Int.equals(commit.getFileChanges().size(), 615, "The total file changes size is correct");
  }
}