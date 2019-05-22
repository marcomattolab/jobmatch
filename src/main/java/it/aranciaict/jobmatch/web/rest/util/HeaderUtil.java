package it.aranciaict.jobmatch.web.rest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

// TODO: Auto-generated Javadoc
/**
 * Utility class for HTTP headers creation.
 */
public final class HeaderUtil {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(HeaderUtil.class);

    /** The Constant APPLICATION_NAME. */
    private static final String APPLICATION_NAME = "jobmatchApp";

    /**
     * Instantiates a new header util.
     */
    private HeaderUtil() {
    }

    /**
     * Creates the alert.
     *
     * @param message the message
     * @param param the param
     * @return the http headers
     */
    public static HttpHeaders createAlert(String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-" + APPLICATION_NAME + "-alert", message);
        headers.add("X-" + APPLICATION_NAME + "-params", param);
        return headers;
    }

    /**
     * Creates the entity creation alert.
     *
     * @param entityName the entity name
     * @param param the param
     * @return the http headers
     */
    public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
        return createAlert(APPLICATION_NAME + "." + entityName + ".created", param);
    }

    /**
     * Creates the entity update alert.
     *
     * @param entityName the entity name
     * @param param the param
     * @return the http headers
     */
    public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
        return createAlert(APPLICATION_NAME + "." + entityName + ".updated", param);
    }

    /**
     * Creates the entity deletion alert.
     *
     * @param entityName the entity name
     * @param param the param
     * @return the http headers
     */
    public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
        return createAlert(APPLICATION_NAME + "." + entityName + ".deleted", param);
    }

    /**
     * Creates the failure alert.
     *
     * @param entityName the entity name
     * @param errorKey the error key
     * @param defaultMessage the default message
     * @return the http headers
     */
    public static HttpHeaders createFailureAlert(String entityName, String errorKey, String defaultMessage) {
        LOG.error("Entity processing failed, {}", defaultMessage);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-" + APPLICATION_NAME + "-error", "error." + errorKey);
        headers.add("X-" + APPLICATION_NAME + "-params", entityName);
        return headers;
    }
}
