package it.aranciaict.jobmatch.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.aranciaict.jobmatch.config.Constants;
import it.aranciaict.jobmatch.domain.constants.ValidationConstants;
import it.aranciaict.jobmatch.security.AuthoritiesConstants;

/**
 * A user.
 */
@Entity
@Table(name = "jhi_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User extends AbstractAuditingEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The login. */
    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = ValidationConstants.SIZE_1, max = ValidationConstants.SIZE_50)
    @Column(length = ValidationConstants.SIZE_50, unique = true, nullable = false)
    private String login;

    /** The password. */
    @JsonIgnore
    @NotNull
    @Size(min = ValidationConstants.SIZE_60, max = ValidationConstants.SIZE_60)
    @Column(name = "password_hash", length = ValidationConstants.SIZE_60, nullable = false)
    private String password;

    /** The first name. */
    @Size(max = ValidationConstants.SIZE_50)
    @Column(name = "first_name", length = ValidationConstants.SIZE_50)
    private String firstName;

    /** The last name. */
    @Size(max = ValidationConstants.SIZE_50)
    @Column(name = "last_name", length = ValidationConstants.SIZE_50)
    private String lastName;

    /** The email. */
    @Email
    @Size(min = ValidationConstants.SIZE_5, max = ValidationConstants.SIZE_254)
    @Column(length = ValidationConstants.SIZE_254, unique = true)
    private String email;

    /** The activated. */
    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    /** The lang key. */
    @Size(min = ValidationConstants.SIZE_2, max = ValidationConstants.SIZE_6)
    @Column(name = "lang_key", length = ValidationConstants.SIZE_6)
    private String langKey;

    /** The image url. */
    @Size(max = ValidationConstants.SIZE_256)
    @Column(name = "image_url", length = ValidationConstants.SIZE_256)
    private String imageUrl;

    /** The activation key. */
    @Size(max = ValidationConstants.SIZE_20)
    @Column(name = "activation_key", length = ValidationConstants.SIZE_20)
    @JsonIgnore
    private String activationKey;

    /** The reset key. */
    @Size(max = ValidationConstants.SIZE_20)
    @Column(name = "reset_key", length = ValidationConstants.SIZE_20)
    @JsonIgnore
    private String resetKey;

    /** The reset date. */
    @Column(name = "reset_date")
    private Instant resetDate = null;

    /** The authorities. */
    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "jhi_user_authority",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = ValidationConstants.SIZE_20)
    private Set<Authority> authorities = new HashSet<>();

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
    // Lowercase the login before saving it in database
    public void setLogin(String login) {
        this.login = StringUtils.lowerCase(login, Locale.ENGLISH);
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
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
     * Gets the activated.
     *
     * @return the activated
     */
    public boolean getActivated() {
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
     * Gets the activation key.
     *
     * @return the activation key
     */
    public String getActivationKey() {
        return activationKey;
    }

    /**
     * Sets the activation key.
     *
     * @param activationKey the new activation key
     */
    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    /**
     * Gets the reset key.
     *
     * @return the reset key
     */
    public String getResetKey() {
        return resetKey;
    }

    /**
     * Sets the reset key.
     *
     * @param resetKey the new reset key
     */
    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    /**
     * Gets the reset date.
     *
     * @return the reset date
     */
    public Instant getResetDate() {
        return resetDate;
    }

    /**
     * Sets the reset date.
     *
     * @param resetDate the new reset date
     */
    public void setResetDate(Instant resetDate) {
        this.resetDate = resetDate;
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
     * Gets the authorities.
     *
     * @return the authorities
     */
    public Set<Authority> getAuthorities() {
        return authorities;
    }

    /**
     * Sets the authorities.
     *
     * @param authorities the new authorities
     */
    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
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

        User user = (User) o;
        return !(user.getId() == null || getId() == null) && Objects.equals(getId(), user.getId());
    }

    /**
     * Checks if is candidate.
     *
     * @return true, if is candidate
     */
    public boolean isCandidate() {
    	return hasAuhtority(AuthoritiesConstants.CANDIDATE);
    }
    
    /**
     * Checks if is company.
     *
     * @return true, if is company
     */
    public boolean isCompany() {
    	return hasAuhtority(AuthoritiesConstants.COMPANY);
    }
    
    /**
     * Checks if is sponsoring institution.
     *
     * @return true, if is sponsoring institution
     */
    public boolean isSponsoringInstitution() {
    	return hasAuhtority(AuthoritiesConstants.SPONSORING_INSTITUTION);
    }
    
    /**
     * Checks if is admin.
     *
     * @return true, if is admin
     */
    public boolean isAdmin() {
    	return hasAuhtority(AuthoritiesConstants.ADMIN);
    }
    
    /**
     * Checks for auhtority.
     *
     * @param anthorityName the anthority name
     * @return true, if successful
     */
    private boolean hasAuhtority(String anthorityName) {
    	boolean hasAuhtority = false;
    	if(!CollectionUtils.isEmpty(authorities)) {
    		hasAuhtority = authorities.stream().filter(aut -> anthorityName.equals(aut.getName())).findAny().isPresent();
    	}
    	return hasAuhtority;
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
        return "User{" +
            "login='" + login + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", activated='" + activated + '\'' +
            ", langKey='" + langKey + '\'' +
            ", activationKey='" + activationKey + '\'' +
            "}";
    }
}
