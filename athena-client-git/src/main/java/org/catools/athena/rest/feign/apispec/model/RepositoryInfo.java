package org.catools.athena.rest.feign.apispec.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.model.core.MetadataDto;
import org.catools.athena.model.core.UserAliasDto;
import org.catools.athena.model.core.UserDto;
import org.catools.athena.model.git.CommitDto;
import org.catools.athena.model.git.DiffEntryDto;
import org.catools.athena.model.git.GitRepositoryDto;
import org.catools.athena.model.git.TagDto;
import org.catools.athena.rest.feign.apispec.configs.GitConfigs;
import org.catools.athena.rest.feign.apispec.entity.MetadataPatternInfo;
import org.catools.athena.rest.feign.apispec.entity.MetadataPatternSet;
import org.catools.athena.rest.feign.apispec.exception.GitClientException;
import org.catools.athena.rest.feign.apispec.helpers.AthenaGitApi;
import org.catools.athena.rest.feign.core.cache.CoreCache;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.filter.CommitTimeRevFilter;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;
import org.eclipse.jgit.util.io.NullOutputStream;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.catools.athena.rest.feign.common.utils.ThreadUtils.executeInParallel;
import static org.eclipse.jgit.diff.DiffEntry.DEV_NULL;

@Data
@RequiredArgsConstructor
@Accessors(chain = true)
@Slf4j
public class RepositoryInfo {

  @Getter(AccessLevel.NONE)
  private final Git git;
  private final String name;
  private final String url;
  private final MetadataPatternSet metadataPatternSet = GitConfigs.getMetadataPatternSet();

  private GitRepositoryDto repositoryDto = new GitRepositoryDto();

  @SuppressWarnings("unused")
  public void uploadRepository(int threadsCount, long timeoutInMinutes) {
    uploadRepository(threadsCount, timeoutInMinutes, null);
  }

  @SuppressWarnings("unused")
  public void uploadRepository(Date since, Date until, int threadsCount, long timeoutInMinutes) {
    uploadRepository(threadsCount, timeoutInMinutes, CommitTimeRevFilter.between(since, until));
  }

  public void uploadRepository(int threadsCount, long timeoutInMinutes, RevFilter filter) {
    readRepository();
    readCommits(filter, threadsCount, timeoutInMinutes);
    repositoryDto.setLastSync(Instant.now());
    onPersistRepository();
  }

  protected void readRepository() {
    repositoryDto = new GitRepositoryDto();
    repositoryDto.setUrl(url);
    repositoryDto.setName(name);
    onPersistRepository();
  }

  protected void readCommits(RevFilter filter, int threadsCount, long timeoutInMinutes) {
    Iterator<RevCommit> commits;
    try {
      commits = git.log().setRevFilter(filter).all().call().iterator();
    } catch (GitAPIException | IOException e) {
      throw new GitClientException("Failed to read commits from repository", e);
    }

    Repository repo = git.getRepository();
    executeInParallel(threadsCount, timeoutInMinutes, () -> {
      while (true) {
        RevCommit next;
        synchronized (commits) {
          if (!commits.hasNext()) {
            return true;
          }
          next = commits.next();
        }
        readCommit(repo, next);
      }
    });
  }

  protected void readCommit(Repository repo, RevCommit commit) {
    CommitDto gitCommit = readCommitInfo(repo, commit);

    log.info("{} persisting commit, diffs: {}, author: {}, committer: {}, tags: {}, metadata: {}.",
        gitCommit.getHash(),
        gitCommit.getDiffEntries().size(),
        gitCommit.getAuthor(),
        gitCommit.getCommitter(),
        gitCommit.getTags().size(),
        gitCommit.getMetadata().size());

    AthenaGitApi.persistCommit(gitCommit);

    log.debug("{} commit persisted, diffs: {}, author: {}, committer: {}, tags: {}, metadata: {}.",
        gitCommit.getHash(),
        gitCommit.getDiffEntries().size(),
        gitCommit.getAuthor(),
        gitCommit.getCommitter(),
        gitCommit.getTags().size(),
        gitCommit.getMetadata().size());
  }

  @NotNull
  protected CommitDto readCommitInfo(Repository repo, RevCommit commit) {
    CommitDto gitCommit = new CommitDto();
    gitCommit.setRepository(name);
    gitCommit.setHash(commit.getName());

    if (commit.getParentCount() > 0) {
      gitCommit.setParentHash(commit.getParent(0).getName());
    }

    gitCommit.setParentCount(commit.getParentCount());
    gitCommit.setCommitTime(commit.getAuthorIdent().getWhen().toInstant());
    if (StringUtils.isNotBlank(commit.getShortMessage())) {
      gitCommit.setShortMessage(commit.getShortMessage());
    } else if (StringUtils.isNotBlank(commit.getFullMessage())) {
      gitCommit.setShortMessage(commit.getFullMessage().split("[\\r\\n]")[0]);
    } else {
      gitCommit.setShortMessage("UNSET");
    }

    gitCommit.setAuthor(readPerson(commit.getAuthorIdent()));
    gitCommit.setCommitter(readPerson(commit.getCommitterIdent()));

    readDiffEntries(repo, commit, gitCommit);
    readRelatedTags(commit, gitCommit);
    readMetadata(commit, gitCommit);
    return gitCommit;
  }

  protected void readRelatedTags(RevCommit commit, CommitDto gitCommit) {
    try {
      List<Ref> list = git.tagList().setContains(commit.getId()).call();
      gitCommit.getTags().clear();
      for (Ref rTag : list) {
        TagDto tag = new TagDto().setName(rTag.getName());

        ObjectId tagObjectId = rTag.getObjectId();
        if (tagObjectId != null) {
          tag.setHash(tagObjectId.getName());
        }

        gitCommit.getTags().add(tag);
      }
    } catch (GitAPIException | IOException e) {
      throw new GitClientException("Failed to read tags for commit", e);
    }
  }

  protected String readPerson(PersonIdent person) {
    UserDto user = new UserDto();

    if (!StringUtils.isBlank(person.getName())) {
      user.setUsername(person.getName().toLowerCase());

      if (!StringUtils.isBlank(person.getEmailAddress())) {
        user.getAliases().add(new UserAliasDto().setAlias(person.getEmailAddress().toLowerCase()));
      }

    } else if (!StringUtils.isBlank(person.getEmailAddress())) {
      user.setUsername(person.getEmailAddress().toLowerCase());
    }

    return CoreCache.readUser(user).getUsername();
  }

  protected void readDiffEntries(Repository repo, RevCommit commit, CommitDto gitCommit) {
    gitCommit.getDiffEntries().clear();

    if (commit.getParentCount() == 0) {
      readCommitDiff(repo, commit, null, gitCommit.getDiffEntries());
    } else {
      readCommitDiff(repo, commit, commit.getParent(0), gitCommit.getDiffEntries());
    }
  }

  protected void readCommitDiff(Repository repo, RevCommit commit, RevCommit parent, Set<DiffEntryDto> diffEntries) {
    AbstractTreeIterator parentTree = getParser(repo, parent);
    AbstractTreeIterator commitTree = getParser(repo, commit);

    DiffFormatter diffFormatter = new DiffFormatter(NullOutputStream.INSTANCE);
    diffFormatter.setDiffComparator(RawTextComparator.WS_IGNORE_ALL);
    diffFormatter.setRepository(repo);
    diffFormatter.setDetectRenames(true);

    try {
      List<DiffEntry> entries = diffFormatter.scan(parentTree, commitTree);

      for (DiffEntry entry : entries) {
        readDiffEntry(entry, diffFormatter, diffEntries);
      }
    } catch (IOException e) {
      throw new GitClientException("Failed to read diff entries.", e);
    }
  }

  protected void readDiffEntry(DiffEntry entry, DiffFormatter diffFormatter, Set<DiffEntryDto> diffEntries) {
    DiffEntryDto gitFileChange = new DiffEntryDto();
    gitFileChange.setOldPath(DEV_NULL.equals(entry.getOldPath()) ? "" : entry.getOldPath());
    gitFileChange.setNewPath(DEV_NULL.equals(entry.getNewPath()) ? "" : entry.getNewPath());
    gitFileChange.setChangeType(entry.getChangeType().name());
    gitFileChange.setInserted(0);
    gitFileChange.setDeleted(0);

    try {
      EditList edits = diffFormatter.toFileHeader(entry).toEditList();
      for (Edit edit : edits) {
        gitFileChange.setDeleted(gitFileChange.getDeleted() + edit.getLengthA());
        gitFileChange.setInserted(gitFileChange.getInserted() + edit.getLengthB());
      }
    } catch (IOException e) {
      throw new GitClientException("Failed to get edit list from commit.", e);
    }

    diffEntries.add(gitFileChange);
  }

  protected void readMetadata(RevCommit commit, CommitDto gitCommit) {
    if (metadataPatternSet != null) {
      for (MetadataPatternInfo metadataPatternInfo : metadataPatternSet) {
        Matcher matcher = Pattern.compile(metadataPatternInfo.getPattern(), Pattern.CASE_INSENSITIVE)
            .matcher(commit.getFullMessage());
        while (matcher.find()) {
          gitCommit.getMetadata().add(new MetadataDto(metadataPatternInfo.getName(), matcher.group(1)));
        }
      }
    }
  }

  protected static String getContentDiff(Repository repository, DiffEntry diff) {
    try (ByteArrayOutputStream out = new ByteArrayOutputStream(); DiffFormatter formatter = new DiffFormatter(out)) {

      formatter.setRepository(repository);
      formatter.format(diff);

      return out.toString();
    } catch (IOException e) {
      throw new GitClientException("Failed to read content diff.", e);
    }
  }

  private static AbstractTreeIterator getParser(Repository repo, RevCommit commit) {
    if (commit == null) {
      return new EmptyTreeIterator();
    }

    try {
      CanonicalTreeParser parentTree = new CanonicalTreeParser();
      parentTree.reset(repo.newObjectReader(), commit.getTree());
      return parentTree;
    } catch (IOException e) {
      throw new GitClientException("Failed to parse commit.", e);
    }
  }

  private void onPersistRepository() {
    AthenaGitApi.persistRepository(repositoryDto);
  }
}
