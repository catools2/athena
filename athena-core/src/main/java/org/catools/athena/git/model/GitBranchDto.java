package org.catools.athena.git.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class GitBranchDto implements Serializable {

    private Long id;
    private String hash;
    private String name;

}
