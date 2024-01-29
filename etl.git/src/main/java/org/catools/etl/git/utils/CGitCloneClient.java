package org.catools.etl.git.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.catools.common.configs.CPathConfigs;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.io.CFile;
import org.catools.etl.git.exception.CGitClientException;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.util.Date;

@Slf4j
@UtilityClass
public class CGitCloneClient {

  /**
   * Clone repository from specified url to the directory in storage with repository name.
   *
   * @param name repository/directory name
   * @param url  the source repository to clone
   * @return JGit client to work with
   */
  public static Git clone(String name, String url) {
    return clone(name, url, Git.cloneRepository().setURI(url));
  }

  /**
   * Clone repository from specified url to the directory in storage with repository name.
   *
   * @param name     repository/directory name
   * @param url      the source repository to clone
   * @param username the git user
   * @param password the password for the git user
   * @return JGit client to work with
   */
  public static Git clone(String name, String url, String username, String password) {
    CloneCommand command = Git.cloneRepository()
        .setURI(url)
        .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password));
    return clone(name, url, command);
  }

  /**
   * Shallow clone repository after specified date,  from specified url to the directory in storage with repository name.
   *
   * @param name  repository/directory name
   * @param url   the source repository to clone
   * @param since the date to start shallow clone from
   * @return JGit client to work with
   */
  public static Git shallowClone(String name, String url, Date since) {
    CloneCommand command = Git.cloneRepository()
        .setShallowSince(since.toInstant())
        .setURI(url);
    return clone(name, url, command);
  }

  /**
   * Shallow clone repository after specified date,  from specified url to the directory in storage with repository name.
   *
   * @param name     repository/directory name
   * @param url      the source repository to clone
   * @param username the git user
   * @param password the password for the git user
   * @param since    the date to start shallow clone from
   * @return JGit client to work with
   */
  public static Git shallowClone(String name, String url, String username, String password, Date since) {
    CloneCommand command = Git.cloneRepository()
        .setURI(url)
        .setShallowSince(since.toInstant())
        .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password));
    return clone(name, url, command);
  }

  private static synchronized Git clone(String name, String url, CloneCommand command) {
    CFile gitDir = cleanupLocalFolder(name);

    try {
      log.info("Start cloning {} repository from {}.", name, url);
      return command.setDirectory(gitDir).call();
    } catch (GitAPIException e) {
      throw new CGitClientException("Failed to clone the git repository", e);
    } finally {
      log.info("Finish cloning {} repository from {}.", name, url);
    }
  }

  private static CFile cleanupLocalFolder(String name) {
    CFile gitDir = CFile.of(CPathConfigs.fromStorage(name));

    if (gitDir.exists()) {
      CVerify.Bool.isTrue(gitDir.forceDelete(), "Local repository removed successfully. location: {}", gitDir.getCanonicalPath());
    }
    return gitDir;
  }

}
