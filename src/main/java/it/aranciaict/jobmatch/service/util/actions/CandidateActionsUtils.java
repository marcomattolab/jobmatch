package it.aranciaict.jobmatch.service.util.actions;

import org.apache.commons.lang3.StringUtils;
import it.aranciaict.jobmatch.domain.Candidate;
import it.aranciaict.jobmatch.domain.User;
import it.aranciaict.jobmatch.security.SecurityUtils;

/**
 * The Class CandidateActionUtils.
 */
public class CandidateActionsUtils {

	/**
	 * Instantiates a new job offer action utils.
	 */
	private CandidateActionsUtils() {
		super();
	}

	/**
	 * Checks if is edits the available.
	 *
	 * @param candidate the candidate
	 * @return true, if is edits the available
	 */
	public static boolean isEditAvailable(Candidate candidate) {
		return isUserOwner(candidate);
	}

	/**
	 * Checks if is delete available.
	 *
	 * @param candidate the candidate
	 * @return true, if is delete available
	 */
	public static boolean isDeleteAvailable(Candidate candidate) {
		return isUserOwner(candidate);
	}

	/**
	 * Checks if is user owner.
	 *
	 * @param candidate the candidate
	 * @return true, if is user owner
	 */
	private static boolean isUserOwner(Candidate candidate) {
		boolean isUserOwner = false;
		if (SecurityUtils.isCurrentUserAdmin()) {
			isUserOwner = true;
		} else if (SecurityUtils.isCurrentUserCandidate()) {
			String currentUser = SecurityUtils.getCurrentUserLogin().get();
			User user = candidate.getUser();
			if (user != null) {
				isUserOwner = StringUtils.equalsIgnoreCase(currentUser, user.getLogin());
			}
		}
		return isUserOwner;
	}

}
