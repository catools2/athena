package org.catools.k8s.utils;

import org.catools.common.collections.CList;
import org.catools.common.date.CDate;
import org.catools.common.extensions.verify.CVerify;
import org.catools.git.configs.CGitConfigRepo;
import org.catools.git.configs.CGitConfigs;
import org.catools.git.model.CGitCommit;
import org.catools.git.model.CGitRepository;
import org.catools.git.utils.CGitWalker;
import org.testng.annotations.Test;

import java.util.List;

@Test(singleThreaded = true)
public class CGitWalkerTest {

  @Test
  public void testReadRepo() {
    CGitConfigRepo repository = CGitConfigs.getRepositories().get(0);
    CGitRepository gitRepository = CGitWalker.readRepository("repo1", repository.getUrl(), 10);
    List<CGitCommit> commits = gitRepository.getCommits();
    CVerify.Int.greaterOrEqual(commits.size(), 70, "correct number of commits returns");
    CGitCommit commit = commits.get(commits.size() - 3);
    CVerify.String.equals(commit.getAuthor().getName(), "akeshmiri", "The author name is correct");
    CVerify.String.equals(commit.getCommitter().getName(), "akeshmiri", "The committer name is correct");
    CVerify.String.equals(commit.getShortMessage(), "Merge modules to one for easier development by my fellow friends", "The short message is correct");
    CVerify.String.equals(commit.getFullMessage(), "Merge modules to one for easier development by my fellow friends\n", "The full message is correct");
    CVerify.Int.equals(commit.getFileChanges().size(), 615, "The total file changes size is correct");
    CVerify.Int.equals(commit.getBranches().size(), 1, "The total branches size is correct");
    CVerify.Int.equals(commit.getTags().size(), 13, "The total tags size is correct");
  }

  @Test
  public void testReadRepoSince() {
    CDate from = CDate.valueOf("2021/9/5", "YYYY/dd/MM");
    CDate to = CDate.of(from).addDays(2);
    CGitConfigRepo repository = CGitConfigs.getRepositories().get(0);
    CGitRepository gitRepository = CGitWalker.readRepository("repo2", repository.getUrl(), from, to, 10);
    CList<CGitCommit> commits = CList.of(gitRepository.getCommits());
    CVerify.Int.greaterOrEqual(commits.size(), 2, "correct number of commits returns");
    CGitCommit commit = commits.getFirst(c -> !c.getFileChanges().isEmpty());
    CVerify.String.equals(commit.getAuthor().getName(), "AKeshmiri", "The author name is correct");
    CVerify.String.equals(commit.getCommitter().getName(), "AKeshmiri", "The committer name is correct");
    CVerify.String.equals(commit.getShortMessage(), "Remove TestNG and WS dependencies from atlassian modules", "The short message is correct");
    CVerify.String.equals(commit.getFullMessage(), "Remove TestNG and WS dependencies from atlassian modules\n", "The full message is correct");
    CVerify.Int.equals(commit.getFileChanges().size(), 7, "The total file changes size is correct");
    CVerify.Int.equals(commit.getBranches().size(), 1, "The total branches size is correct");
    CVerify.Int.equals(commit.getTags().size(), 5, "The total tags size is correct");
  }
}