package it.aranciaict.jobmatch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The Class FileStorageProperties.
 */
@ConfigurationProperties(prefix = "file", ignoreUnknownFields = false)
public class FileStorageProperties {
    
    /** The upload dir. */
    private String uploadDir;

    /**
     * Gets the upload dir.
     *
     * @return the upload dir
     */
    public String getUploadDir() {
        return uploadDir;
    }

    /**
     * Sets the upload dir.
     *
     * @param uploadDir the new upload dir
     */
    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
