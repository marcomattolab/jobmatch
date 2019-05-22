package it.aranciaict.jobmatch.service.exceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractJobMatchServiceException.
 */
@SuppressWarnings("serial")
public abstract class AbstractJobMatchServiceException extends Exception {

	/** The error key. */
	private final String errorKey;
	
	/** The params. */
	private final String params;

	/**
	 * Instantiates a new depositeria con posti non disponibili exception.
	 *
	 * @param errorKey the error key
	 * @param message      the message
	 */
	public AbstractJobMatchServiceException(String errorKey, String message) {
		this(errorKey, message, null);
	}

	
	/**
	 * Instantiates a new depositeria con posti non disponibili exception.
	 *
	 * @param errorKey the error key
	 * @param message      the message
	 * @param params the params
	 */
	public AbstractJobMatchServiceException(String errorKey, String message, String params) {
		super(message);
		this.errorKey = errorKey;
		this.params = params;
	}
	/**
	 * Gets the codice errore.
	 *
	 * @return the codice errore
	 */
	public String getErrorKey() {
		return errorKey;
	}
	
	/**
	 * Gets the param.
	 *
	 * @return the param
	 */
	public String getParams() {
		return params;
	}
	
}
