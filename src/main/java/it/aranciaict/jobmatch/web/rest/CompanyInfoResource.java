package it.aranciaict.jobmatch.web.rest;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.jhipster.web.util.ResponseUtil;
import it.aranciaict.jobmatch.security.AuthoritiesConstants;
import it.aranciaict.jobmatch.service.CompanyInfoService;
import it.aranciaict.jobmatch.service.dto.CompanyInfoDTO;

/**
 * REST controller for managing Company Info.
 */
@RestController
@RequestMapping("/api/companies-info")
public class CompanyInfoResource {

	private static final Logger LOG = LoggerFactory.getLogger(CompanyInfoResource.class);

	private final CompanyInfoService companyInfoService;


	/**
	 * Instantiates a new company resource.
	 *
	 * @param companyInfoService the company info service
	 */
	public CompanyInfoResource(CompanyInfoService companyInfoService) {
		this.companyInfoService = companyInfoService;
	}
	

	/**
	 * GET /companies-info/:id : get the "id" company.
	 *
	 * @param id the id of the companyDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the companyDTO,
	 *         or with status 404 (Not Found)
	 */
	@GetMapping("/{id}")
	public ResponseEntity<CompanyInfoDTO> getCompany(@PathVariable Long id) {
		LOG.debug("REST request to get Company Info: {}", id);
		Optional<CompanyInfoDTO> companyInfoDTO = companyInfoService.findOne(id);
		return ResponseUtil.wrapOrNotFound(companyInfoDTO);
	}
	
	/**
	 * Gets the company by sponsoring institution.
	 *
	 * @param sponsoringInstitutionId the sponsoring institution id
	 * @return the company by sponsoring institution
	 */
	@GetMapping("/sponsoringInstitution/{sponsoringInstitutionId}")
	public ResponseEntity<CompanyInfoDTO> getCompanyBySponsoringInstitution(@PathVariable Long sponsoringInstitutionId) {
		LOG.debug("REST request to get Company Info: {}", sponsoringInstitutionId);
		Optional<CompanyInfoDTO> companyInfoDTO = companyInfoService.findBySponsoringInstitutionId(sponsoringInstitutionId);
		return ResponseUtil.wrapOrNotFound(companyInfoDTO);
	}
	
	/**
	 * GET /companies-info/current.
	 *
	 * @return the ResponseEntity with status 200 (OK) and with body the companyDTO,
	 *         or with status 404 (Not Found)
	 */
	@GetMapping("/current")
	@PreAuthorize("hasRole('" + AuthoritiesConstants.COMPANY + "') or hasRole('" + AuthoritiesConstants.SPONSORING_INSTITUTION + "')")
	public ResponseEntity<CompanyInfoDTO> getCompany() {
		LOG.debug("REST request to get current Company Info");
		Optional<CompanyInfoDTO> companyInfoDTO = companyInfoService.findByUserIsCurrentUser();
		return ResponseUtil.wrapOrNotFound(companyInfoDTO);
	}

	
}
