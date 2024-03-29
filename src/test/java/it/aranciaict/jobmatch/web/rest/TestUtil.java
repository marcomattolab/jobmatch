package it.aranciaict.jobmatch.web.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Utility class for testing REST controllers.
 */
public final class TestUtil {

    /** The Constant mapper. */
    private static final ObjectMapper MAPPER = createObjectMapper();

    /**  MediaType for JSON UTF8. */
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);


    /**
     * Creates the object mapper.
     *
     * @return the object mapper
     */
    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    /**
     * Convert an object to JSON byte array.
     *
     * @param object            the object to convert
     * @return the JSON byte array
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static byte[] convertObjectToJsonBytes(Object object)
            throws IOException {
        return MAPPER.writeValueAsBytes(object);
    }

    /**
     * Create a byte array with a specific size filled with specified data.
     *
     * @param size the size of the byte array
     * @param data the data to put in the byte array
     * @return the JSON byte array
     */
    @SuppressWarnings("checkstyle:magicNumber")
    public static byte[] createByteArray(int size, String data) {
        byte[] byteArray = new byte[size];
        for (int i = 0; i < size; i++) {
            byteArray[i] = Byte.parseByte(data, 2);
        }
        return byteArray;
    }

    /**
     * A matcher that tests that the examined string represents the same instant as the reference datetime.
     */
    public static class ZonedDateTimeMatcher extends TypeSafeDiagnosingMatcher<String> {

        /** The date. */
        private final ZonedDateTime date;

        /**
         * Instantiates a new zoned date time matcher.
         *
         * @param date the date
         */
        public ZonedDateTimeMatcher(ZonedDateTime date) {
            this.date = date;
        }

        /* (non-Javadoc)
         * @see org.hamcrest.TypeSafeDiagnosingMatcher#matchesSafely(java.lang.Object, org.hamcrest.Description)
         */
        @Override
        protected boolean matchesSafely(String item, Description mismatchDescription) {
            try {
                if (!date.isEqual(ZonedDateTime.parse(item))) {
                    mismatchDescription.appendText("was ").appendValue(item);
                    return false;
                }
                return true;
            } catch (DateTimeParseException e) {
                mismatchDescription.appendText("was ").appendValue(item)
                    .appendText(", which could not be parsed as a ZonedDateTime");
                return false;
            }

        }

        /* (non-Javadoc)
         * @see org.hamcrest.SelfDescribing#describeTo(org.hamcrest.Description)
         */
        @Override
        public void describeTo(Description description) {
            description.appendText("a String representing the same Instant as ").appendValue(date);
        }
    }

    /**
     * Creates a matcher that matches when the examined string reprensents the same instant as the reference datetime.
     *
     * @param date the reference datetime against which the examined string is checked
     * @return the zoned date time matcher
     */
    public static ZonedDateTimeMatcher sameInstant(ZonedDateTime date) {
        return new ZonedDateTimeMatcher(date);
    }

    /**
     * Verifies the equals/hashcode contract on the domain object.
     *
     * @param <T> the generic type
     * @param clazz the clazz
     * @throws Exception the exception
     */
    public static <T> void equalsVerifier(Class<T> clazz) throws Exception {
        T domainObject1 = clazz.getConstructor().newInstance();
        assertThat(domainObject1.toString()).isNotNull();
        assertThat(domainObject1).isEqualTo(domainObject1);
        assertThat(domainObject1.hashCode()).isEqualTo(domainObject1.hashCode());
        // Test with an instance of another class
        Object testOtherObject = new Object();
        assertThat(domainObject1).isNotEqualTo(testOtherObject);
        assertThat(domainObject1).isNotEqualTo(null);
        // Test with an instance of the same class
        T domainObject2 = clazz.getConstructor().newInstance();
        assertThat(domainObject1).isNotEqualTo(domainObject2);
        // HashCodes are equals because the objects are not persisted yet
        assertThat(domainObject1.hashCode()).isEqualTo(domainObject2.hashCode());
    }

    /**
     * Create a FormattingConversionService which use ISO date format, instead of the localized one.
     * @return the FormattingConversionService
     */
    public static FormattingConversionService createFormattingConversionService() {
        DefaultFormattingConversionService dfcs = new DefaultFormattingConversionService ();
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setUseIsoFormat(true);
        registrar.registerFormatters(dfcs);
        return dfcs;
    }

    /**
     * Instantiates a new test util.
     */
    private TestUtil() {}
}
