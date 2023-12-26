package org.catools.athena.rest;

import org.catools.athena.core.model.MetadataDto;
import org.catools.athena.pipeline.model.PipelineDto;
import org.catools.athena.pipeline.model.PipelineExecutionDto;
import org.catools.athena.pipeline.model.PipelineScenarioExecutionDto;
import org.catools.athena.rest.pipeline.entity.*;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AthenaBaseTest.SpringTestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AthenaBaseTest {

    @Configuration
    @ComponentScan({"org.catools.athena.rest"})
    @PropertySource("classpath:application.properties")
    public static class SpringTestConfig {
    }

    protected static void verifyMetadata(PipelineScenarioExecution execution, MetadataDto expected) {
        PipelineExecutionMetadata actual = getMetadataDto(execution.getMetadata(), m -> Objects.equals(m.getName(), expected.getName()));
        assertThat(actual.getName(), equalTo(expected.getName()));
        assertThat(actual.getValue(), equalTo(expected.getValue()));
    }

    protected static void verifyMetadata(PipelineScenarioExecutionDto execution, PipelineExecutionMetadata expected) {
        MetadataDto actual = getMetadataDto(execution.getMetadata(), m -> Objects.equals(m.getName(), expected.getName()));
        assertThat(actual.getName(), equalTo(expected.getName()));
        assertThat(actual.getValue(), equalTo(expected.getValue()));
    }

    protected static void verifyMetadata(PipelineExecutionDto execution, PipelineExecutionMetadata expected) {
        MetadataDto actual = getMetadataDto(execution.getMetadata(), m -> Objects.equals(m.getName(), expected.getName()));
        assertThat(actual.getName(), equalTo(expected.getName()));
        assertThat(actual.getValue(), equalTo(expected.getValue()));
    }

    protected static void verifyMetadata(PipelineExecution execution, MetadataDto expected) {
        PipelineExecutionMetadata actual = getMetadataDto(execution.getMetadata(), m -> Objects.equals(m.getName(), expected.getName()));
        assertThat(actual.getName(), equalTo(expected.getName()));
        assertThat(actual.getValue(), equalTo(expected.getValue()));
    }

    protected static void verifyMetadata(Pipeline pipeline, MetadataDto expected) {
        PipelineMetadata actual = getMetadataDto(pipeline.getMetadata(), m -> Objects.equals(m.getName(), expected.getName()));
        assertThat(actual.getName(), equalTo(expected.getName()));
        assertThat(actual.getValue(), equalTo(expected.getValue()));
    }

    protected static void verifyMetadata(PipelineDto pipeline, PipelineMetadata expected) {
        MetadataDto actual = getMetadataDto(pipeline.getMetadata(), m -> Objects.equals(m.getName(), expected.getName()));
        assertThat(actual.getName(), equalTo(expected.getName()));
        assertThat(actual.getValue(), equalTo(expected.getValue()));
    }

    protected static void verifyMetadata(PipelineDto pipeline, MetadataDto expected) {
        MetadataDto actual = getMetadataDto(pipeline.getMetadata(), m -> Objects.equals(m.getName(), expected.getName()));
        assertThat(actual.getName(), equalTo(expected.getName()));
        assertThat(actual.getValue(), equalTo(expected.getValue()));
    }

    @NotNull
    private static <T> T getMetadataDto(Collection<T> actuals, Predicate<? super T> predicate) {
        T actual = actuals.stream().filter(predicate).findFirst().orElse(null);
        assertThat(actual, notNullValue());
        return actual;
    }
}