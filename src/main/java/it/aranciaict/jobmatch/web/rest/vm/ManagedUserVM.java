package it.aranciaict.jobmatch.web.rest.vm;

import it.aranciaict.jobmatch.service.dto.UserDTO;
import javax.validation.constraints.Size;

/**
 * View Model extending the UserDTO, which is meant to be used in the user management UI.
 */
public class ManagedUserVM extends UserDTO {

    /** The Constant PASSWORD_MIN_LENGTH. */
    public static final int PASSWORD_MIN_LENGTH = 4;

    /** The Constant PASSWORD_MAX_LENGTH. */
    public static final int PASSWORD_MAX_LENGTH = 100;

    /** The password. */
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;
    
    /** The role account. */
    private String roleAccount;
    

    /**
     * Instantiates a new managed user VM.
     */
    public ManagedUserVM() {
        // Empty constructor needed for Jackson.
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
     * Gets the role account.
     *
     * @return the role account
     */
    public String getRoleAccount() {
		return roleAccount;
	}

	/**
	 * Sets the role account.
	 *
	 * @param roleAccount the new role account
	 */
	public void setRoleAccount(String roleAccount) {
		this.roleAccount = roleAccount;
	}

	/* (non-Javadoc)
     * @see it.aranciaict.jobmatch.service.dto.UserDTO#toString()
     */
    @Override
    public String toString() {
        return "ManagedUserVM{" +
            "} " + super.toString();
    }

}
