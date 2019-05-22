package it.aranciaict.jobmatch.repository.timezone;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// TODO: Auto-generated Javadoc
/**
 * The Class DateTimeWrapper.
 */
@Entity
@Table(name = "jhi_date_time_wrapper")
public class DateTimeWrapper implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The instant. */
    @Column(name = "instant")
    private Instant instant;

    /** The local date time. */
    @Column(name = "local_date_time")
    private LocalDateTime localDateTime;

    /** The offset date time. */
    @Column(name = "offset_date_time")
    private OffsetDateTime offsetDateTime;

    /** The zoned date time. */
    @Column(name = "zoned_date_time")
    private ZonedDateTime zonedDateTime;

    /** The local time. */
    @Column(name = "local_time")
    private LocalTime localTime;

    /** The offset time. */
    @Column(name = "offset_time")
    private OffsetTime offsetTime;

    /** The local date. */
    @Column(name = "local_date")
    private LocalDate localDate;

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the instant.
     *
     * @return the instant
     */
    public Instant getInstant() {
        return instant;
    }

    /**
     * Sets the instant.
     *
     * @param instant the new instant
     */
    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    /**
     * Gets the local date time.
     *
     * @return the local date time
     */
    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    /**
     * Sets the local date time.
     *
     * @param localDateTime the new local date time
     */
    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    /**
     * Gets the offset date time.
     *
     * @return the offset date time
     */
    public OffsetDateTime getOffsetDateTime() {
        return offsetDateTime;
    }

    /**
     * Sets the offset date time.
     *
     * @param offsetDateTime the new offset date time
     */
    public void setOffsetDateTime(OffsetDateTime offsetDateTime) {
        this.offsetDateTime = offsetDateTime;
    }

    /**
     * Gets the zoned date time.
     *
     * @return the zoned date time
     */
    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    /**
     * Sets the zoned date time.
     *
     * @param zonedDateTime the new zoned date time
     */
    public void setZonedDateTime(ZonedDateTime zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
    }

    /**
     * Gets the local time.
     *
     * @return the local time
     */
    public LocalTime getLocalTime() {
        return localTime;
    }

    /**
     * Sets the local time.
     *
     * @param localTime the new local time
     */
    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    /**
     * Gets the offset time.
     *
     * @return the offset time
     */
    public OffsetTime getOffsetTime() {
        return offsetTime;
    }

    /**
     * Sets the offset time.
     *
     * @param offsetTime the new offset time
     */
    public void setOffsetTime(OffsetTime offsetTime) {
        this.offsetTime = offsetTime;
    }

    /**
     * Gets the local date.
     *
     * @return the local date
     */
    public LocalDate getLocalDate() {
        return localDate;
    }

    /**
     * Sets the local date.
     *
     * @param localDate the new local date
     */
    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DateTimeWrapper dateTimeWrapper = (DateTimeWrapper) o;
        return !(dateTimeWrapper.getId() == null || getId() == null) && Objects.equals(getId(), dateTimeWrapper.getId());
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "TimeZoneTest{" +
            "id=" + id +
            ", instant=" + instant +
            ", localDateTime=" + localDateTime +
            ", offsetDateTime=" + offsetDateTime +
            ", zonedDateTime=" + zonedDateTime +
            '}';
    }
}
