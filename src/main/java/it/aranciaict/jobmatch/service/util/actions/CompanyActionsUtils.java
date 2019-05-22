package it.aranciaict.jobmatch.service.util.actions;

import org.apache.commons.lang3.StringUtils;

import it.aranciaict.jobmatch.domain.Company;
import it.aranciaict.jobmatch.domain.User;
import it.aranciaict.jobmatch.security.SecurityUtils;

/**
 * The Class CompanyActionUtils.
 */
public class CompanyActionsUtils {

	/**
	 * Instantiates a new job offer action utils.
	 */
	private CompanyActionsUtils() {
		super();
	}

	/**
	 * Checks if is edits the available.
	 *
	 * @param company the company
	 * @return true, if is edits the available
	 */
	public static boolean isEditAvailable(Company company) {
		return isUserOwner(company);
	}

	/**
	 * Checks if is delete available.
	 *
	 * @param company the company
	 * @return true, if is delete available
	 */
	public static boolean isDeleteAvailable(Company company) {
		return isUserOwner(company);
	}
	
	/**
	 * Checks if is application available.
	 *
	 * @param company the company
	 * @return true, if is application available
	 */
	public static boolean isApplicationAvailable(Company company) {
		return SecurityUtils.isCurrentUserCandidate() && !company.isSponsoringInstitution();
	}

	/**
	 * Checks if is user owner.
	 *
	 * @param company the company
	 * @return true, if is user owner
	 */
	private static boolean isUserOwner(Company company) {
		boolean isUserOwner = false;
		if (SecurityUtils.isCurrentUserAdmin()) {
			isUserOwner = true;
		} else if (SecurityUtils.isCurrentUserCompany()) {
			String currentUser = SecurityUtils.getCurrentUserLogin().get();
			User user = company.getUser();
			if (user != null) {
				isUserOwner = StringUtils.equalsIgnoreCase(currentUser, user.getLogin());
			}
		}
		return isUserOwner;
	}

}
