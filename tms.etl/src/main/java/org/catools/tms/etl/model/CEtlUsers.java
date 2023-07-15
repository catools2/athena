package org.catools.tms.etl.model;

import org.catools.common.collections.CSet;

import java.util.Objects;
import java.util.stream.Stream;

public class CEtlUsers extends CSet<CEtlUser> {
  public CEtlUsers() {
  }

  public CEtlUsers(CEtlUser... c) {
    super(c);
  }

  public CEtlUsers(Stream<CEtlUser> stream) {
    super(stream);
  }

  public CEtlUsers(Iterable<CEtlUser> iterable) {
    super(iterable);
  }

  public CEtlUser getByNameOrAddIfNotExists(CEtlUser newUser) {
    if (newUser == null) {
      return null;
    }
    CEtlUser user = getFirstOrNull(u -> Objects.equals(u.getName(), newUser.getName()));
    if (user != null) {
      return user;
    }
    add(newUser);
    return newUser;
  }
}
