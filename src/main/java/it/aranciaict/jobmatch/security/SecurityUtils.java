package it.aranciaict.jobmatch.security;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Utility class for Spring Security.
 */
public final class SecurityUtils {

    /**
     * Instantiates a new security utils.
     */
    private SecurityUtils() {
    }

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user
     */
    public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
            .map(authentication -> {
                if (authentication.getPrincipal() instanceof UserDetails) {
                    UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                    return springSecurityUser.getUsername();
                } else if (authentication.getPrincipal() instanceof String) {
                    return (String) authentication.getPrincipal();
                }
                return null;
            });
    }

    /**
     * Get the JWT of the current user.
     *
     * @return the JWT of the current user
     */
    public static Optional<String> getCurrentUserJWT() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
            .filter(authentication -> authentication.getCredentials() instanceof String)
            .map(authentication -> (String) authentication.getCredentials());
    }

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise
     */
    public static boolean isAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
            .map(authentication -> authentication.getAuthorities().stream()
                .noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(AuthoritiesConstants.ANONYMOUS)))
            .orElse(false);
    }

    /**
     * If the current user has a specific authority (security role).
     * <p>
     * The name of this method comes from the isUserInRole() method in the Servlet API
     *
     * @param authority the authority to check
     * @return true if the current user has the authority, false otherwise
     */
    public static boolean isCurrentUserInRole(String authority) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
            .map(authentication -> authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority)))
            .orElse(false);
    }
    
    /**
     * Checks if is current user admin.
     *
     * @return true, if is current user admin
     */
    public static boolean isCurrentUserAdmin() {
    	return isCurrentUserInRole(AuthoritiesConstants.ADMIN);
    }
    
    
    /**
     * Checks if is current user company.
     *
     * @return true, if is current user company
     */
    public static boolean isCurrentUserCompany() {
    	return isCurrentUserInRole(AuthoritiesConstants.COMPANY);
    }
    
    /**
     * Checks if is current user sponsoring institution.
     *
     * @return true, if is current user sponsoring institution
     */
    public static boolean isCurrentUserSponsoringInstitution() {
    	return isCurrentUserInRole(AuthoritiesConstants.SPONSORING_INSTITUTION);
    }
    
    /**
     * Checks if is current user company.
     *
     * @return true, if is current user company
     */
    public static boolean isCurrentUserCandidate() {
    	return isCurrentUserInRole(AuthoritiesConstants.CANDIDATE);
    }
    
}
