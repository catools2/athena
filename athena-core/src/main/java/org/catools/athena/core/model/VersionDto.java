package org.catools.athena.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@NoArgsConstructor
@Accessors(chain = true)
public class VersionDto implements Serializable {

    private Long id;

    private String code;

    private String name;

    private String project;
}
