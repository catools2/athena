package org.catools.athena.rest.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({
    "org.catools.rest.common",
    "org.catools.athena"
})
public class AthenaContextConfiguration {

}