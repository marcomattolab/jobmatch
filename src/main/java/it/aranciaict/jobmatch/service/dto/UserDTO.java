package it.aranciaict.jobmatch.service.dto;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import it.aranciaict.jobmatch.config.Constants;
import it.aranciaict.jobmatch.domain.Authority;
import it.aranciaict.jobmatch.domain.User;
import it.aranciaict.jobmatch.domain.constants.ValidationConstants;

// TODO: Auto-generated Javadoc
/**
 * A DTO representing a user, with his authorities.
 */
public class UserDTO {

    /** The id. */
    private Long id;

    /** The login. */
    @NotBlank
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = ValidationConstants.SIZE_1, max = ValidationConstants.SIZE_50)
    private String login;

    /** The first name. */
    @Size(max = ValidationConstants.SIZE_50)
    private String firstName;

    /** The last name. */
    @Size(max = ValidationConstants.SIZE_50)
    private String lastName;

    /** The email. */
    @Email
    @Size(min = ValidationConstants.SIZE_5, max = ValidationConstants.SIZE_254)
    private String email;

    /** The image url. */
    @Size(max = ValidationConstants.SIZE_256)
    private String imageUrl;

    /** The activated. */
    private boolean activated = false;

    /** The lang key. */
    @Size(min = ValidationConstants.SIZE_2, max = ValidationConstants.SIZE_6)
    private String langKey;

    /** The created by. */
    private String createdBy;

    /** The created date. */
    private Instant createdDate;

    /** The last modified by. */
    private String lastModifiedBy;

    /** The last modified date. */
    private Instant lastModifiedDate;

    /** The authorities. */
    private Set<String> authorities;

    /** The Current Role Id */
    private Long currentRoleId;
    
    /**
     * Instantiates a new user DTO.
     */
    public UserDTO() {
        // Empty constructor needed for Jackson.
    }

    /**
     * Instantiates a new user DTO.
     *
     * @param user the user
     */
    public UserDTO(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.activated = user.getActivated();
        this.imageUrl = user.getImageUrl();
        this.langKey = user.getLangKey();
        this.createdBy = user.getCreatedBy();
        this.createdDate = user.getCreatedDate();
        this.lastModifiedBy = user.getLastModifiedBy();
        this.lastModifiedDate = user.getLastModifiedDate();
        this.authorities = user.getAuthorities().stream()
            .map(Authority::getName)
            .collect(Collectors.toSet());
    }

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
     * Gets the login.
     *
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets the login.
     *
     * @param login the new login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName the new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     *
     * @param lastName the new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the image url.
     *
     * @return the image url
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Sets the image url.
     *
     * @param imageUrl the new image url
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Checks if is activated.
     *
     * @return true, if is activated
     */
    public boolean isActivated() {
        return activated;
    }

    /**
     * Sets the activated.
     *
     * @param activated the new activated
     */
    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    /**
     * Gets the lang key.
     *
     * @return the lang key
     */
    public String getLangKey() {
        return langKey;
    }

    /**
     * Sets the lang key.
     *
     * @param langKey the new lang key
     */
    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    /**
     * Gets the created by.
     *
     * @return the created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the created by.
     *
     * @param createdBy the new created by
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the created date.
     *
     * @return the created date
     */
    public Instant getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the created date.
     *
     * @param createdDate the new created date
     */
    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Gets the last modified by.
     *
     * @return the last modified by
     */
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    /**
     * Sets the last modified by.
     *
     * @param lastModifiedBy the new last modified by
     */
    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    /**
     * Gets the last modified date.
     *
     * @return the last modified date
     */
    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * Sets the last modified date.
     *
     * @param lastModifiedDate the new last modified date
     */
    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    /**
     * Gets the authorities.
     *
     * @return the authorities
     */
    public Set<String> getAuthorities() {
        return authorities;
    }

    /**
     * Sets the authorities.
     *
     * @param authorities the new authorities
     */
    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "UserDTO{" +
            "login='" + login + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", activated=" + activated +
            ", langKey='" + langKey + '\'' +
            ", createdBy=" + createdBy +
            ", createdDate=" + createdDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            ", authorities=" + authorities +
            "}";
    }

    /**
     * @return the currentRoleId
     */
    public Long getCurrentRoleId() {
        return currentRoleId;
    }

    /**
     * @param currentRoleId the currentRoleId to set
     */
    public void setCurrentRoleId(Long currentRoleId) {
        this.currentRoleId = currentRoleId;
    }

}
