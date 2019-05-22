package it.aranciaict.jobmatch.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";
    
    public static final String CANDIDATE = "ROLE_CANDIDATE";
    
    public static final String COMPANY = "ROLE_COMPANY";
    
    public static final String SPONSORING_INSTITUTION = "ROLE_PROMOTER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    /**
     * Instantiates a new authorities constants.
     */
    private AuthoritiesConstants() {
    }
}
