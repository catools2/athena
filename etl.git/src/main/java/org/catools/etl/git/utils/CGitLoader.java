package org.catools.etl.git.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.catools.common.concurrent.CParallelRunner;
import org.catools.common.date.CDate;
import org.catools.etl.git.cache.CGitCacheManager;
import org.catools.etl.git.dao.CGitCommitDao;
import org.catools.etl.git.dao.CGitRepositoryDao;
import org.catools.etl.git.exception.CGitApiException;
import org.catools.etl.git.model.CGitCommit;
import org.catools.etl.git.model.CGitFileChange;
import org.catools.etl.git.model.CGitRepository;
import org.catools.etl.git.model.CGitUser;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.*;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.CommitTimeRevFilter;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;
import org.eclipse.jgit.util.io.NullOutputStream;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Slf4j
@UtilityClass
public class CGitLoader {
  public static void loadRepository(String name, String url, int totalParallelProcessors) {
    loadRepository(name, url, null, totalParallelProcessors);
  }

  public static void loadRepository(String name, String url, Date since, Date until, int totalParallelProcessors) {
    Git git = CGitCloneClient.clone(name, url);
    loadRepository(name, url, git, CommitTimeRevFilter.between(since, until), totalParallelProcessors);
  }

  public static void loadRepository(String name, String url, RevFilter filter, int totalParallelProcessors) {
    Git git = CGitCloneClient.clone(name, url);
    loadRepository(name, url, git, filter, totalParallelProcessors);
  }

  public static void loadRepository(String name, String url, String username, String password, int totalParallelProcessors) {
    loadRepository(name, url, username, password, null, totalParallelProcessors);
  }

  public static void loadRepository(String name, String url, String username, String password, Date since, Date until, int totalParallelProcessors) {
    Git git = CGitCloneClient.clone(name, url, username, password);
    loadRepository(name, url, git, CommitTimeRevFilter.between(since, until), totalParallelProcessors);
  }

  public static void loadRepository(String name, String url, String username, String password, RevFilter filter, int totalParallelProcessors) {
    Git git = CGitCloneClient.clone(name, url, username, password);
    loadRepository(name, url, git, filter, totalParallelProcessors);
  }

  public static void loadRepository(String name, String url, Git git, RevFilter filter, int totalParallelProcessors) {
    CGitRepository gitRepository = new CGitRepository();
    gitRepository.setUrl(url);
    gitRepository.setName(name);
    gitRepository.setLastUpdate(CDate.now());

    Repository repo = git.getRepository();

    try {
      gitRepository = CGitCacheManager.getRepository(gitRepository);

      // Update last updated to the processing start point to avoid losing data
      CGitRepositoryDao.updateEndDate(gitRepository.getUrl(), CDate.now().getTimeStamp());

      readCommits(git, gitRepository, repo, filter, totalParallelProcessors);
    } catch (Exception e) {
      throw new CGitApiException("Failed to read commits from repository", e);
    }
  }

  private static void readCommits(Git git, CGitRepository gitRepository, Repository repo, RevFilter filter, int totalParallelProcessors) throws IOException, GitAPIException {
    Iterator<RevCommit> commits = git.log().setRevFilter(filter).all().call().iterator();

    CParallelRunner<Boolean> runner = new CParallelRunner<>("Git Commit Reader", totalParallelProcessors, () -> {
      while (true) {
        RevCommit commit = null;
        synchronized (commits) {
          if (!commits.hasNext()) break;
          commit = commits.next();
        }
        if (commit != null) {
          loadCommit(git, gitRepository, repo, commit);
        }
      }
      return true;
    });

    try {
      runner.invokeAll();
    } catch (Throwable e) {
      throw new CGitApiException("Failed to read commits", e);
    }
  }

  private static void loadCommit(Git git, CGitRepository gitRepository, Repository repo, RevCommit commit) throws IOException, GitAPIException {
    CGitCommit gitCommit = new CGitCommit();
    gitCommit.setHash(commit.getName());
    gitCommit.setRepository(gitRepository);

    gitCommit.setAuthor(getUser(commit.getAuthorIdent()));
    gitCommit.setCommitTime(commit.getAuthorIdent().getWhen());
    gitCommit.setShortMessage(commit.getShortMessage());
    gitCommit.setFullMessage(commit.getFullMessage());
    gitCommit.setCommitter(getUser(commit.getCommitterIdent()));

    addFileDiff(gitCommit, commit, repo);
    addRelatedBranches(repo, gitCommit, commit);
    addRelatedTags(git, gitCommit, commit);

    CGitCommitDao.merge(gitCommit);
  }

  private static void addFileDiff(CGitCommit gitCommit, RevCommit commit, Repository repo) throws IOException {
    if (commit.getParentCount() == 0) return;

    if (commit.getParentCount() == 0) {
      readDiffsWithParent(repo, gitCommit, commit, null);
    } else {
      for (RevCommit parent : commit.getParents()) {
        readDiffsWithParent(repo, gitCommit, commit, parent);
      }
    }
  }

  private static void readDiffsWithParent(Repository repo, CGitCommit gitCommit, RevCommit commit, RevCommit parent) throws IOException {
    AbstractTreeIterator parentTree = getParser(repo, parent);
    AbstractTreeIterator commitTree = getParser(repo, commit);

    DiffFormatter diffFormatter = new DiffFormatter(NullOutputStream.INSTANCE);
    diffFormatter.setDiffComparator(RawTextComparator.WS_IGNORE_ALL);
    diffFormatter.setRepository(repo);
    diffFormatter.setDetectRenames(true);

    List<DiffEntry> entries = diffFormatter.scan(parentTree, commitTree);

    for (DiffEntry entry : entries) {
      CGitFileChange gitFileChange = new CGitFileChange();
      gitFileChange.setPath(entry.getOldPath());
      gitFileChange.setNewPath(entry.getNewPath());
      gitFileChange.setCommit(gitCommit);

      EditList edits = diffFormatter.toFileHeader(entry).toEditList();

      for (Edit edit : edits) {
        gitFileChange.setDeleted(gitFileChange.getDeleted() + edit.getLengthA());
        gitFileChange.setInserted(gitFileChange.getInserted() + edit.getLengthB());
      }
      gitCommit.addFileChange(gitFileChange);
    }
  }

  private static AbstractTreeIterator getParser(Repository repo, RevCommit commi) throws IOException {
    if (commi == null)
      return new EmptyTreeIterator();

    CanonicalTreeParser parentTree = new CanonicalTreeParser();
    parentTree.reset(repo.newObjectReader(), commi.getTree());
    return parentTree;
  }

  private static void addRelatedTags(Git git, CGitCommit gitCommit, RevCommit commit) throws IOException, GitAPIException {
    List<Ref> list = git.tagList().setContains(commit.getId()).call();
    for (Ref tag : list) {
      ObjectId objectId = Objects.requireNonNull(tag.getObjectId());
      gitCommit.addTag(CGitCacheManager.getTag(objectId.getName(), tag.getName()));
    }
  }

  private static void addRelatedBranches(Repository repo, CGitCommit gitCommit, RevCommit commit) throws IOException {
    RevWalk walk = new RevWalk(repo);
    RevCommit targetCommit = walk.parseCommit(repo.resolve(commit.getName()));
    for (Ref ref : repo.getRefDatabase().getRefs()) {
      if (ref.getName().startsWith(Constants.R_HEADS)) {
        ObjectId objectId = Objects.requireNonNull(ref.getObjectId());
        if (walk.isMergedInto(targetCommit, walk.parseCommit(objectId))) {
          gitCommit.addBranch(CGitCacheManager.getBranch(objectId.getName(), ref.getName()));
        }
      }
    }
  }

  private static CGitUser getUser(PersonIdent personIdent) {
    return CGitCacheManager.getUser(personIdent.getName());
  }
}
