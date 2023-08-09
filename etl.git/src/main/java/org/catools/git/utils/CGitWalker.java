package org.catools.git.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.catools.common.concurrent.CParallelRunner;
import org.catools.common.date.CDate;
import org.catools.git.exception.CGitApiException;
import org.catools.git.model.*;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.*;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.CommitTimeRevFilter;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.util.io.NullOutputStream;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Slf4j
@UtilityClass
public class CGitWalker {
  public static CGitRepository readRepository(String name, String url, int totalParallelProcessors) {
    return readRepository(name, url, null, totalParallelProcessors);
  }

  public static CGitRepository readRepository(String name, String url, Date since, Date until, int totalParallelProcessors) {
    Git git = CGitCloneClinet.shallowClone(name, url, since);
    return readRepository(name, url, git, CommitTimeRevFilter.between(since, until), totalParallelProcessors);
  }

  public static CGitRepository readRepository(String name, String url, RevFilter filter, int totalParallelProcessors) {
    Git git = CGitCloneClinet.clone(name, url);
    return readRepository(name, url, git, filter, totalParallelProcessors);
  }

  public static CGitRepository readRepository(String name, String url, String username, String password, int totalParallelProcessors) {
    return readRepository(name, url, username, password, null, totalParallelProcessors);
  }

  public static CGitRepository readRepository(String name, String url, String username, String password, Date since, Date until, int totalParallelProcessors) {
    Git git = CGitCloneClinet.shallowClone(name, url, username, password, since);
    return readRepository(name, url, git, CommitTimeRevFilter.between(since, until), totalParallelProcessors);
  }

  public static CGitRepository readRepository(String name, String url, String username, String password, RevFilter filter, int totalParallelProcessors) {
    Git git = CGitCloneClinet.clone(name, url, username, password);
    return readRepository(name, url, git, filter, totalParallelProcessors);
  }

  public static CGitRepository readRepository(String name, String url, Git git, RevFilter filter, int totalParallelProcessors) {
    CGitRepository gitRepository = new CGitRepository();
    gitRepository.setUrl(url);
    gitRepository.setName(name);
    gitRepository.setLastUpdate(CDate.now());

    Repository repo = git.getRepository();

    try {
      readCommits(git, gitRepository, repo, filter, totalParallelProcessors);
    } catch (Exception e) {
      throw new CGitApiException("Failed to read commits from repository", e);
    }

    return gitRepository;
  }

  private static void readCommits(Git git, CGitRepository gitRepository, Repository repo, RevFilter filter, int totalParallelProcessors) throws IOException, GitAPIException {
    Iterator<RevCommit> commits = git.log().setRevFilter(filter).all().call().iterator();

    CParallelRunner runner = new CParallelRunner("Git Commit Reader",totalParallelProcessors, () -> {
      while(true) {
        RevCommit commit = null;
        synchronized (commits) {
          if (!commits.hasNext()) break;
          commit = commits.next();
        }
        if (commit != null) {
          addCommitToRepo(git, gitRepository, repo, commit);
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

  private static void addCommitToRepo(Git git, CGitRepository gitRepository, Repository repo, RevCommit commit) throws IOException, GitAPIException {
    CGitCommit gitCommit = new CGitCommit();
    gitCommit.setId(commit.getName());

    gitCommit.setAuthor(getUser(commit.getAuthorIdent()));
    gitCommit.setCommitTime(commit.getAuthorIdent().getWhen());
    gitCommit.setShortMessage(commit.getShortMessage());
    gitCommit.setFullMessage(commit.getFullMessage());
    gitCommit.setCommitter(getUser(commit.getCommitterIdent()));

    addFileDiff(gitCommit, commit, repo);
    addRelatedBranches(repo, gitCommit, commit);
    addRelatedTags(git, gitCommit, commit);

    gitRepository.addCommit(gitCommit);
    log.trace("Translated a new commit with {} diff, {} branches and {} tags",
        gitCommit.getFileChanges().size(),
        gitCommit.getBranches().size(),
        gitCommit.getTags().size());
    log.trace("total translated commits: {}", gitRepository.getCommits().size());
  }

  private static CGitUser getUser(PersonIdent personIdent) {
    return new CGitUser(personIdent.getName());
  }

  private static void addRelatedBranches(Repository repo, CGitCommit gitCommit, RevCommit commit) throws IOException {
    RevWalk walk = new RevWalk(repo);
    RevCommit targetCommit = walk.parseCommit(repo.resolve(commit.getName()));
    for (Ref ref : repo.getRefDatabase().getRefs()) {
      if (ref.getName().startsWith(Constants.R_HEADS)) {
        if (walk.isMergedInto(targetCommit, walk.parseCommit(ref.getObjectId()))) {
          gitCommit.addBranch(new CGitBranch().setId(ref.getObjectId().getName()).setName(ref.getName()));
        }
      }
    }
  }

  private static void addRelatedTags(Git git, CGitCommit gitCommit, RevCommit commit) throws IOException, GitAPIException {
    List<Ref> list = git.tagList().setContains(commit.getId()).call();
    for (Ref tag : list) {
      gitCommit.addTag(new CGitTag().setId(tag.getObjectId().getName()).setName(tag.getName()));
    }
  }

  private static void addFileDiff(CGitCommit gitCommit, RevCommit commit, Repository repo) throws IOException {
    if (commit.getParentCount() == 0) return;

    for (RevCommit parent : commit.getParents()) {
      CanonicalTreeParser parentTree = getParser(repo, parent);
      CanonicalTreeParser commitTree = getParser(repo, commit);

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
  }

  private static CanonicalTreeParser getParser(Repository repo, RevCommit commi) throws IOException {
    CanonicalTreeParser parentTree = new CanonicalTreeParser();
    parentTree.reset(repo.newObjectReader(), commi.getTree());
    return parentTree;
  }
}
