package it.aranciaict.jobmatch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;

// TODO: Auto-generated Javadoc
/**
 * The Class JacksonConfiguration.
 */
@Configuration
public class JacksonConfiguration {

    /**
     * Support for Java date and time API.
     * @return the corresponding Jackson module.
     */
    @Bean
    public JavaTimeModule javaTimeModule() {
        return new JavaTimeModule();
    }

    /**
     * Jdk 8 time module.
     *
     * @return the jdk 8 module
     */
    @Bean
    public Jdk8Module jdk8TimeModule() {
        return new Jdk8Module();
    }


    /**
     * Support for Hibernate types in Jackson.
     *
     * @return the hibernate 5 module
     */
    @Bean
    public Hibernate5Module hibernate5Module() {
        return new Hibernate5Module();
    }

    /**
     * Jackson Afterburner module to speed up serialization/deserialization.
     * @return the afterburner module
     */
    @Bean
    public AfterburnerModule afterburnerModule() {
        return new AfterburnerModule();
    }

    /**
     * Module for serialization/deserialization of RFC7807 Problem.
     *
     * @return the problem module
     */
    @Bean
    ProblemModule problemModule() {
        return new ProblemModule();
    }

    /**
     * Module for serialization/deserialization of ConstraintViolationProblem.
     *
     * @return the constraint violation problem module
     */
    @Bean
    ConstraintViolationProblemModule constraintViolationProblemModule() {
        return new ConstraintViolationProblemModule();
    }

}
