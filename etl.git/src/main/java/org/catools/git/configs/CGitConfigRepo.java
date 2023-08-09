package org.catools.git.configs;

import lombok.Data;

@Data
public class CGitConfigRepo {
  private String name;
  private String url;
  private String username;
  private String password;
}
