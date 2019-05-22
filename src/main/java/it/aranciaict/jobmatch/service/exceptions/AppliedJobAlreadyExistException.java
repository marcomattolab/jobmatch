package it.aranciaict.jobmatch.service.exceptions;

@SuppressWarnings("serial")
public class AppliedJobAlreadyExistException extends AbstractJobMatchServiceException {

	private static final String ERROR_CODE = "appliedJob.alreadyExist";

	/**
	 * Instantiates a new svincolo non eseguibile exception.
	 *
	 * @param message the message
	 */
	public AppliedJobAlreadyExistException(String message) {
		super(ERROR_CODE, message);
	}

}
