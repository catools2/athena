package org.catools.athena.tms.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class StatusTransitionDto implements Serializable {

    private Long id;

    private String from;

    private String to;

    private LocalDateTime occurred;
}
