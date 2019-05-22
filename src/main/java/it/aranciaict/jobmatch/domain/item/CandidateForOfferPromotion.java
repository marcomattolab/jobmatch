package it.aranciaict.jobmatch.domain.item;

import java.io.Serializable;

/**
 * CandidateForOfferPromotion.
 */
public interface CandidateForOfferPromotion extends Serializable {

    /**
	 * Gets the id.
	 *
	 * @return the id
	 */
    public Long getId();

    /**
	 * getLangKey.
	 *
	 * @return getLangKey
	 */
    public String getLangKey();

    /**
	 * getEmail.
	 *
	 * @return getEmail
	 */
    public String getEmail();
    
}