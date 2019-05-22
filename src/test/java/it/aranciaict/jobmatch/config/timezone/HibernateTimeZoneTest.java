package it.aranciaict.jobmatch.config.timezone;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import it.aranciaict.jobmatch.JobmatchApp;
import it.aranciaict.jobmatch.repository.timezone.DateTimeWrapper;
import it.aranciaict.jobmatch.repository.timezone.DateTimeWrapperRepository;

/**
 * Unit tests for the UTC Hibernate configuration.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JobmatchApp.class)
public class HibernateTimeZoneTest {

    /** The date time wrapper repository. */
    @Autowired
    private DateTimeWrapperRepository dateTimeWrapperRepository;
    
    /** The jdbc template. */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /** The date time wrapper. */
    private DateTimeWrapper dateTimeWrapper;
    
    /** The date time formatter. */
    private DateTimeFormatter dateTimeFormatter;
    
    /** The time formatter. */
    private DateTimeFormatter timeFormatter;
    
    /** The date formatter. */
    private DateTimeFormatter dateFormatter;

    /**
     * Setup.
     */
    @Before
    public void setup() {
        dateTimeWrapper = new DateTimeWrapper();
        dateTimeWrapper.setInstant(Instant.parse("2014-11-12T05:50:00.0Z"));
        dateTimeWrapper.setLocalDateTime(LocalDateTime.parse("2014-11-12T07:50:00.0"));
        dateTimeWrapper.setOffsetDateTime(OffsetDateTime.parse("2011-12-14T08:30:00.0Z"));
        dateTimeWrapper.setZonedDateTime(ZonedDateTime.parse("2011-12-14T08:30:00.0Z"));
        dateTimeWrapper.setLocalTime(LocalTime.parse("14:30:00"));
        dateTimeWrapper.setOffsetTime(OffsetTime.parse("14:30:00+02:00"));
        dateTimeWrapper.setLocalDate(LocalDate.parse("2016-09-10"));

        dateTimeFormatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss.S")
            .withZone(ZoneId.of("UTC"));

        timeFormatter = DateTimeFormatter
            .ofPattern("HH:mm:ss")
            .withZone(ZoneId.of("UTC"));

        dateFormatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd");
    }

    /**
     * Store instant with utc config should be stored on GMT time zone.
     */
    @Test
    @Transactional
    public void storeInstantWithUtcConfigShouldBeStoredOnGMTTimeZone() {
        dateTimeWrapperRepository.saveAndFlush(dateTimeWrapper);

        String request = generateSqlRequest("instant", dateTimeWrapper.getId());
        SqlRowSet resultSet = jdbcTemplate.queryForRowSet(request);
        String expectedValue = dateTimeFormatter.format(dateTimeWrapper.getInstant());

        assertThatDateStoredValueIsEqualToInsertDateValueOnGMTTimeZone(resultSet, expectedValue);
    }

    /**
     * Store local date time with utc config should be stored on GMT time zone.
     */
    @Test
    @Transactional
    public void storeLocalDateTimeWithUtcConfigShouldBeStoredOnGMTTimeZone() {
        dateTimeWrapperRepository.saveAndFlush(dateTimeWrapper);

        String request = generateSqlRequest("local_date_time", dateTimeWrapper.getId());
        SqlRowSet resultSet = jdbcTemplate.queryForRowSet(request);
        String expectedValue = dateTimeWrapper
            .getLocalDateTime()
            .atZone(ZoneId.systemDefault())
            .format(dateTimeFormatter);

        assertThatDateStoredValueIsEqualToInsertDateValueOnGMTTimeZone(resultSet, expectedValue);
    }

    /**
     * Store offset date time with utc config should be stored on GMT time zone.
     */
    @Test
    @Transactional
    public void storeOffsetDateTimeWithUtcConfigShouldBeStoredOnGMTTimeZone() {
        dateTimeWrapperRepository.saveAndFlush(dateTimeWrapper);

        String request = generateSqlRequest("offset_date_time", dateTimeWrapper.getId());
        SqlRowSet resultSet = jdbcTemplate.queryForRowSet(request);
        String expectedValue = dateTimeWrapper
            .getOffsetDateTime()
            .format(dateTimeFormatter);

        assertThatDateStoredValueIsEqualToInsertDateValueOnGMTTimeZone(resultSet, expectedValue);
    }

    /**
     * Store zone date time with utc config should be stored on GMT time zone.
     */
    @Test
    @Transactional
    public void storeZoneDateTimeWithUtcConfigShouldBeStoredOnGMTTimeZone() {
        dateTimeWrapperRepository.saveAndFlush(dateTimeWrapper);

        String request = generateSqlRequest("zoned_date_time", dateTimeWrapper.getId());
        SqlRowSet resultSet = jdbcTemplate.queryForRowSet(request);
        String expectedValue = dateTimeWrapper
            .getZonedDateTime()
            .format(dateTimeFormatter);

        assertThatDateStoredValueIsEqualToInsertDateValueOnGMTTimeZone(resultSet, expectedValue);
    }

    /**
     * Store local time with utc config should be stored on GMT time zone according to his 1 st jan 1970 value.
     */
    @Test
    @Transactional
    @SuppressWarnings("checkstyle:magicNumber")
    public void storeLocalTimeWithUtcConfigShouldBeStoredOnGMTTimeZoneAccordingToHis1stJan1970Value() {
        dateTimeWrapperRepository.saveAndFlush(dateTimeWrapper);

        String request = generateSqlRequest("local_time", dateTimeWrapper.getId());
        SqlRowSet resultSet = jdbcTemplate.queryForRowSet(request);
        String expectedValue = dateTimeWrapper
            .getLocalTime()
            .atDate(LocalDate.of(1970, Month.JANUARY, 1))
            .atZone(ZoneId.systemDefault())
            .format(timeFormatter);

        assertThatDateStoredValueIsEqualToInsertDateValueOnGMTTimeZone(resultSet, expectedValue);
    }

    /**
     * Store offset time with utc config should be stored on GMT time zone according to his 1 st jan 1970 value.
     */
    @Test
    @Transactional
    @SuppressWarnings("checkstyle:magicNumber")
    public void storeOffsetTimeWithUtcConfigShouldBeStoredOnGMTTimeZoneAccordingToHis1stJan1970Value() {
        dateTimeWrapperRepository.saveAndFlush(dateTimeWrapper);

        String request = generateSqlRequest("offset_time", dateTimeWrapper.getId());
        SqlRowSet resultSet = jdbcTemplate.queryForRowSet(request);
        String expectedValue = dateTimeWrapper
            .getOffsetTime()
            .toLocalTime()
            .atDate(LocalDate.of(1970, Month.JANUARY, 1))
            .atZone(ZoneId.systemDefault())
            .format(timeFormatter);

        assertThatDateStoredValueIsEqualToInsertDateValueOnGMTTimeZone(resultSet, expectedValue);
    }

    /**
     * Store local date with utc config should be stored without transformation.
     */
    @Test
    @Transactional
    public void storeLocalDateWithUtcConfigShouldBeStoredWithoutTransformation() {
        dateTimeWrapperRepository.saveAndFlush(dateTimeWrapper);

        String request = generateSqlRequest("local_date", dateTimeWrapper.getId());
        SqlRowSet resultSet = jdbcTemplate.queryForRowSet(request);
        String expectedValue = dateTimeWrapper
            .getLocalDate()
            .format(dateFormatter);

        assertThatDateStoredValueIsEqualToInsertDateValueOnGMTTimeZone(resultSet, expectedValue);
    }

    /**
     * Generate sql request.
     *
     * @param fieldName the field name
     * @param id the id
     * @return the string
     */
    private String generateSqlRequest(String fieldName, long id) {
        return format("SELECT %s FROM jhi_date_time_wrapper where id=%d", fieldName, id);
    }

    /**
     * Assert that date stored value is equal to insert date value on GMT time zone.
     *
     * @param sqlRowSet the sql row set
     * @param expectedValue the expected value
     */
    private void assertThatDateStoredValueIsEqualToInsertDateValueOnGMTTimeZone(SqlRowSet sqlRowSet, String expectedValue) {
        while (sqlRowSet.next()) {
            String dbValue = sqlRowSet.getString(1);

            assertThat(dbValue).isNotNull();
            assertThat(dbValue).isEqualTo(expectedValue);
        }
    }
}
