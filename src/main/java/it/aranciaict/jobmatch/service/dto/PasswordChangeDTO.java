package it.aranciaict.jobmatch.service.dto;

// TODO: Auto-generated Javadoc
/**
 * A DTO representing a password change required data - current and new password.
 */
public class PasswordChangeDTO {
    
    /** The current password. */
    private String currentPassword;
    
    /** The new password. */
    private String newPassword;

    /**
     * Instantiates a new password change DTO.
     */
    public PasswordChangeDTO() {
        // Empty constructor needed for Jackson.
    }

    /**
     * Instantiates a new password change DTO.
     *
     * @param currentPassword the current password
     * @param newPassword the new password
     */
    public PasswordChangeDTO(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    /**
     * Gets the current password.
     *
     * @return the current password
     */
    public String getCurrentPassword() {

        return currentPassword;
    }

    /**
     * Sets the current password.
     *
     * @param currentPassword the new current password
     */
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    /**
     * Gets the new password.
     *
     * @return the new password
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * Sets the new password.
     *
     * @param newPassword the new new password
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
