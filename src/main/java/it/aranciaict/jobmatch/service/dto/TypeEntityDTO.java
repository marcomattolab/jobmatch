package it.aranciaict.jobmatch.service.dto;

import java.io.Serializable;

/**
 * The Class TypeEntityDto.
 */
public interface TypeEntityDTO extends Serializable{
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId();

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id);

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode();
	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(String code);

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription();

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description);

}
