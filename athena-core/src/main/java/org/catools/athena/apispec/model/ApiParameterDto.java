package org.catools.athena.apispec.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ApiParameterDto implements NameTypePair, Serializable {

    private Long id;
    private String name;
    private String type;

}
