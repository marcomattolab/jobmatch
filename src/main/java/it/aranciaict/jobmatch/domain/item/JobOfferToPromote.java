package it.aranciaict.jobmatch.domain.item;

import java.io.Serializable;

/**
 * The Company entity Info.
 */
public interface JobOfferToPromote extends Serializable {

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId();

	/**
	 * 
	 * /** Gets the company name.
	 *
	 * @return the company name
	 */
	public String getCompanyName();
	
	/**
	 * 
	 * /** Gets the company name.
	 *
	 * @return the company name
	 */
    public String getCompanyId();

	/**
	 * 
	 * /** getJobTitle.
	 *
	 * @return getJobTitle
	 */
    public String getJobTitle();

	/**
	 * 
	 * /** getJobShortDescription
	 *
	 * @return getJobShortDescription
	 */
	public String getJobShortDescription();
    
}