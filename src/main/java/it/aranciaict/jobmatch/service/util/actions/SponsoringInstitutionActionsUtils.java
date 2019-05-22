package it.aranciaict.jobmatch.service.util.actions;

import org.apache.commons.lang3.StringUtils;

import it.aranciaict.jobmatch.domain.SponsoringInstitution;
import it.aranciaict.jobmatch.domain.User;
import it.aranciaict.jobmatch.security.SecurityUtils;

/**
 * The Class CompanyActionUtils.
 */
public class SponsoringInstitutionActionsUtils {

	/**
	 * Instantiates a new job offer action utils.
	 */
	private SponsoringInstitutionActionsUtils() {
		super();
	}

	/**
	 * Checks if is edits the available.
	 *
	 * @param sponsoringInstitution the sponsoring institution
	 * @return true, if is edits the available
	 */
	public static boolean isEditAvailable(SponsoringInstitution sponsoringInstitution) {
		return isUserOwner(sponsoringInstitution);
	}

	/**
	 * Checks if is delete available.
	 *
	 * @param sponsoringInstitution the sponsoring institution
	 * @return true, if is delete available
	 */
	public static boolean isDeleteAvailable(SponsoringInstitution sponsoringInstitution) {
		return isUserOwner(sponsoringInstitution);
	}
	

	/**
	 * Checks if is user owner.
	 *
	 * @param sponsoringInstitution the sponsoring institution
	 * @return true, if is user owner
	 */
	private static boolean isUserOwner(SponsoringInstitution sponsoringInstitution) {
		boolean isUserOwner = false;
		if (SecurityUtils.isCurrentUserAdmin()) {
			isUserOwner = true;
		} else if (SecurityUtils.isCurrentUserSponsoringInstitution()) {
			String currentUser = SecurityUtils.getCurrentUserLogin().get();
			User user = sponsoringInstitution.getUser();
			if (user != null) {
				isUserOwner = StringUtils.equalsIgnoreCase(currentUser, user.getLogin());
			}
		}
		return isUserOwner;
	}

}
