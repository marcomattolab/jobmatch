package it.aranciaict.jobmatch.web.rest.errors;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

// TODO: Auto-generated Javadoc
/**
 * The Class ExceptionTranslatorTestController.
 */
@RestController
public class ExceptionTranslatorTestController {

    /**
     * Concurrency failure.
     */
    @GetMapping("/test/concurrency-failure")
    public void concurrencyFailure() {
        throw new ConcurrencyFailureException("test concurrency failure");
    }

    /**
     * Method argument.
     *
     * @param testDTO the test DTO
     */
    @PostMapping("/test/method-argument")
    public void methodArgument(@Valid @RequestBody TestDTO testDTO) {
    }

    /**
     * Parameterized error.
     */
    @GetMapping("/test/parameterized-error")
    public void parameterizedError() {
        throw new CustomParameterizedException("test parameterized error", "param0_value", "param1_value");
    }

    /**
     * Parameterized error 2.
     */
    @GetMapping("/test/parameterized-error2")
    public void parameterizedError2() {
        Map<String, Object> params = new HashMap<>();
        params.put("foo", "foo_value");
        params.put("bar", "bar_value");
        throw new CustomParameterizedException("test parameterized error", params);
    }

    /**
     * Missing servlet request part exception.
     *
     * @param part the part
     */
    @GetMapping("/test/missing-servlet-request-part")
    public void missingServletRequestPartException(@RequestPart String part) {
    }

    /**
     * Missing servlet request parameter exception.
     *
     * @param param the param
     */
    @GetMapping("/test/missing-servlet-request-parameter")
    public void missingServletRequestParameterException(@RequestParam String param) {
    }

    /**
     * Accessdenied.
     */
    @GetMapping("/test/access-denied")
    public void accessdenied() {
        throw new AccessDeniedException("test access denied!");
    }

    /**
     * Unauthorized.
     */
    @GetMapping("/test/unauthorized")
    public void unauthorized() {
        throw new BadCredentialsException("test authentication failed!");
    }

    /**
     * Exception with reponse status.
     */
    @GetMapping("/test/response-status")
    public void exceptionWithReponseStatus() {
        throw new TestResponseStatusException();
    }

    /**
     * Internal server error.
     */
    @GetMapping("/test/internal-server-error")
    public void internalServerError() {
        throw new RuntimeException();
    }

    /**
     * The Class TestDTO.
     */
    public static class TestDTO {

        /** The test. */
        @NotNull
        private String test;

        /**
         * Gets the test.
         *
         * @return the test
         */
        public String getTest() {
            return test;
        }

        /**
         * Sets the test.
         *
         * @param test the new test
         */
        public void setTest(String test) {
            this.test = test;
        }
    }

    /**
     * The Class TestResponseStatusException.
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "test response status")
    @SuppressWarnings("serial")
    public static class TestResponseStatusException extends RuntimeException {
    }

}
