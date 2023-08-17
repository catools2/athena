package org.catools.git.cache;

import lombok.experimental.UtilityClass;
import org.catools.common.collections.CHashMap;
import org.catools.common.collections.interfaces.CMap;
import org.catools.git.dao.CGitBranchDao;
import org.catools.git.dao.CGitRepositoryDao;
import org.catools.git.dao.CGitTagDao;
import org.catools.git.dao.CGitUserDao;
import org.catools.git.model.CGitBranch;
import org.catools.git.model.CGitRepository;
import org.catools.git.model.CGitTag;
import org.catools.git.model.CGitUser;

import java.util.function.Supplier;

@UtilityClass
public class CGitCacheManager {
  private static final CMap<String, CGitUser> USERS = new CHashMap<>();
  private static final CMap<String, CGitRepository> REPOS = new CHashMap<>();
  private static final CMap<String, CGitTag> TAGS = new CHashMap<>();
  private static final CMap<String, CGitBranch> BRANCHES = new CHashMap<>();

  public static synchronized CGitUser getUser(String name) {
    return read(USERS, name, () -> {
      CGitUser result = CGitUserDao.getByName(name);
      if (result != null) {
        return result;
      }
      return CGitUserDao.merge(new CGitUser(name));
    });
  }

  public static synchronized CGitRepository getRepository(CGitRepository repository) {
    return read(REPOS, repository.getUrl(), () -> {
      CGitRepository result = CGitRepositoryDao.getByName(repository.getUrl());
      if (result != null) {
        return result;
      }
      return CGitRepositoryDao.merge(repository);
    });
  }

  public static CGitTag getTag(String hash, String name) {
    return read(TAGS, hash, () -> {
      CGitTag result = CGitTagDao.getByHash(hash);
      if (result != null) {
        return result;
      }
      return CGitTagDao.merge(new CGitTag(hash, name));
    });
  }

  public static CGitBranch getBranch(String hash, String name) {
    return read(BRANCHES, hash, () -> {
      CGitBranch result = CGitBranchDao.getByHash(hash);
      if (result != null) {
        return result;
      }
      return CGitBranchDao.merge(new CGitBranch(hash, name));
    });
  }

  private static synchronized  <T> T read(CMap<String, T> storage, String key, Supplier<T> getValue) {
    return storage.computeIfAbsent(key, k -> getValue.get());
  }
}
