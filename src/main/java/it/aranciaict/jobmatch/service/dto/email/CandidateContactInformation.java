package it.aranciaict.jobmatch.service.dto.email;

public class CandidateContactInformation {

	/** The Email */
	private String email;
	/** The language */
	private String langKey;

	/**
	 * Instantiates a new candidate contact information.
	 */
	public CandidateContactInformation() {
	}

	/**
	 * Instantiates a new candidate contact information.
	 *
	 * @param email   the email
	 * @param langKey the lang key
	 */
	public CandidateContactInformation(String email, String langKey) {
		this.email = email;
		this.langKey = langKey;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the langKey
	 */
	public String getLangKey() {
		return langKey;
	}

	/**
	 * @param langKey the langKey to set
	 */
	public void setLangKey(String langKey) {
		this.langKey = langKey;
	}

}