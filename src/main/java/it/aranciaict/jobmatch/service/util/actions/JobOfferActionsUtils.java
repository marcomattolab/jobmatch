package it.aranciaict.jobmatch.service.util.actions;

import org.apache.commons.lang3.StringUtils;

import it.aranciaict.jobmatch.domain.JobOffer;
import it.aranciaict.jobmatch.domain.User;
import it.aranciaict.jobmatch.security.SecurityUtils;

/**
 * The Class JobOfferActionUtils.
 */
public class JobOfferActionsUtils {

	/**
	 * Instantiates a new job offer action utils.
	 */
	private JobOfferActionsUtils() {
		super();
	}

	/**
	 * Checks if is edits the available.
	 *
	 * @param jobOffer the job offer
	 * @return true, if is edits the available
	 */
	public static boolean isEditAvailable(JobOffer jobOffer) {
		return isUserOwner(jobOffer);
	}

	/**
	 * Checks if is delete available.
	 *
	 * @param jobOffer the job offer
	 * @return true, if is delete available
	 */
	public static boolean isDeleteAvailable(JobOffer jobOffer) {
		return isUserOwner(jobOffer);
	}

	/**
	 * Checks if is applied job available.
	 *
	 * @param jobOffer the job offer
	 * @return true, if is applied job available
	 */
	public static boolean isAppliedJobAvailable(JobOffer jobOffer) {
		return SecurityUtils.isCurrentUserCandidate();
	}

	/**
	 * Checks if is user owner.
	 *
	 * @param jobOffer the job offer
	 * @return true, if is user owner
	 */
	private static boolean isUserOwner(JobOffer jobOffer) {
		boolean isUserOwner = false;
		if (SecurityUtils.isCurrentUserAdmin()) {
			isUserOwner = true;
		} else if (SecurityUtils.isCurrentUserCompany() || SecurityUtils.isCurrentUserSponsoringInstitution()) {
			String currentUser = SecurityUtils.getCurrentUserLogin().get();
			User user = jobOffer.getCompany().getUser();
			if (user != null) {
				isUserOwner = StringUtils.equalsIgnoreCase(currentUser, user.getLogin());
			}
		}
		return isUserOwner;
	}

}
