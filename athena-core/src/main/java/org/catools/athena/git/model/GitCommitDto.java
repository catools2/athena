package org.catools.athena.git.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.athena.core.model.MetadataDto;
import org.catools.athena.core.model.UserDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@Accessors(chain = true)
public class GitCommitDto implements Serializable {

    private Long id;
    private String hash;
    private String shortMessage;
    private String fullMessage;
    private LocalDateTime commitTime;
    private UserDto author;
    private UserDto committer;
    private Set<GitFileChangeDto> fileChanges = new HashSet<>();
    private Set<GitBranchDto> branches = new HashSet<>();
    private GitRepositoryDto repository;
    private Set<MetadataDto> metadata = new HashSet<>();

}
