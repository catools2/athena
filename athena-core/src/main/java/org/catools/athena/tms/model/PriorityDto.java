package org.catools.athena.tms.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PriorityDto implements Serializable {

    private Long id;
    private String code;
    private String name;

}
