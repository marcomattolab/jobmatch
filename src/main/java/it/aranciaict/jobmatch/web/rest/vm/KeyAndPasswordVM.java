package it.aranciaict.jobmatch.web.rest.vm;

// TODO: Auto-generated Javadoc
/**
 * View Model object for storing the user's key and password.
 */
public class KeyAndPasswordVM {

    /** The key. */
    private String key;

    /** The new password. */
    private String newPassword;

    /**
     * Gets the key.
     *
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key.
     *
     * @param key the new key
     */
    public void setKey(String key) {
        this.key = key;
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
