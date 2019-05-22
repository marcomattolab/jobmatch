package it.aranciaict.jobmatch.service.util.actions;

import org.apache.commons.lang3.StringUtils;

import it.aranciaict.jobmatch.domain.Project;
import it.aranciaict.jobmatch.domain.User;
import it.aranciaict.jobmatch.security.SecurityUtils;

/**
 * The Class PojectActionsUtils.
 */
public class ProjectActionsUtils {

	/**
	 * Instantiates a new poject actions utils.
	 */
	private ProjectActionsUtils() {
		super();
	}

	/**
	 * Checks if is edits the available.
	 *
	 * @param project the project
	 * @return true, if is edits the available
	 */
	public static boolean isEditAvailable(Project project) {
		return isUserOwner(project);
	}

	/**
	 * Checks if is delete available.
	 *
	 * @param project the project
	 * @return true, if is delete available
	 */
	public static boolean isDeleteAvailable(Project project) {
		return isUserOwner(project);
	}

	/**
	 * Checks if is user owner.
	 *
	 * @param project the project
	 * @return true, if is user owner
	 */
	private static boolean isUserOwner(Project project) {
		boolean isUserOwner = false;
		if (SecurityUtils.isCurrentUserAdmin()) {
			isUserOwner = true;
		} else if (SecurityUtils.isCurrentUserCompany() || SecurityUtils.isCurrentUserSponsoringInstitution()) {
			String currentUser = SecurityUtils.getCurrentUserLogin().get();
			User user = project.getCompany().getUser();
			if (user != null) {
				isUserOwner = StringUtils.equalsIgnoreCase(currentUser, project.getCompany().getUser().getLogin());
			}
		}
		return isUserOwner;
	}

}
