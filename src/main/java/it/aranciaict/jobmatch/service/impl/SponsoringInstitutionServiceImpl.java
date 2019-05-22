package it.aranciaict.jobmatch.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.aranciaict.jobmatch.domain.Company;
import it.aranciaict.jobmatch.domain.SponsoringInstitution;
import it.aranciaict.jobmatch.repository.CompanyRepository;
import it.aranciaict.jobmatch.repository.SponsoringInstitutionRepository;
import it.aranciaict.jobmatch.service.SponsoringInstitutionService;
import it.aranciaict.jobmatch.service.dto.SponsoringInstitutionDTO;
import it.aranciaict.jobmatch.service.mapper.SponsoringInstitutionMapper;

/**
 * Service Implementation for managing SponsoringInstitution.
 */
@Service
@Transactional
public class SponsoringInstitutionServiceImpl implements SponsoringInstitutionService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(SponsoringInstitutionServiceImpl.class);

	/** The sponsoring institution repository. */
	private final SponsoringInstitutionRepository sponsoringInstitutionRepository;

	/** The company repository. */
	private final CompanyRepository companyRepository;

	/** The sponsoring institution mapper. */
	private final SponsoringInstitutionMapper sponsoringInstitutionMapper;

	/**
	 * Instantiates a new sponsoring institution service impl.
	 *
	 * @param sponsoringInstitutionRepository the sponsoring institution repository
	 * @param companyRepository the company repository
	 * @param sponsoringInstitutionMapper     the sponsoring institution mapper
	 */
	public SponsoringInstitutionServiceImpl(SponsoringInstitutionRepository sponsoringInstitutionRepository,
			CompanyRepository companyRepository, SponsoringInstitutionMapper sponsoringInstitutionMapper) {
		this.sponsoringInstitutionRepository = sponsoringInstitutionRepository;
		this.companyRepository = companyRepository;
		this.sponsoringInstitutionMapper = sponsoringInstitutionMapper;
	}

	/**
	 * Save a sponsoringInstitution.
	 *
	 * @param sponsoringInstitutionDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public SponsoringInstitutionDTO save(SponsoringInstitutionDTO sponsoringInstitutionDTO) {
		log.debug("Request to save SponsoringInstitution : {}", sponsoringInstitutionDTO);
		SponsoringInstitution sponsoringInstitution = sponsoringInstitutionMapper.toEntity(sponsoringInstitutionDTO);
		sponsoringInstitution = sponsoringInstitutionRepository.save(sponsoringInstitution);
		saveCompanyInfo(sponsoringInstitution);
		return sponsoringInstitutionMapper.toDto(sponsoringInstitution);
	}
	
	/**
	 * Save comany info.
	 *
	 * @param sponsoringInstitution the sponsoring institution
	 */
	private void saveCompanyInfo(SponsoringInstitution sponsoringInstitution) {
		Company company = companyRepository.findByUserIsCurrentUser().orElse(null);
		if(company != null) {
			company.setCompanyName(sponsoringInstitution.getIstituitionName());
			company.setCompanyDescription(sponsoringInstitution.getIstituitionDescription());
			company.setVatNumber(sponsoringInstitution.getVatNumber());
			company.setPhone(sponsoringInstitution.getPhone());
			company.setMobilePhone(sponsoringInstitution.getMobilePhone());
			company.setFoundationDate(sponsoringInstitution.getFoundationDate());
			company.setCountry(sponsoringInstitution.getCountry());
			company.setCity(sponsoringInstitution.getCity());
			company.setCap(sponsoringInstitution.getCap());
			company.setProvince(sponsoringInstitution.getProvince());
			company.setRegion(sponsoringInstitution.getRegion());
			company.urlSite(sponsoringInstitution.getUrlSite());
			companyRepository.save(company);
		}
	}
	
	
	

	/**
	 * Get all the sponsoringInstitutions.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<SponsoringInstitutionDTO> findAll(Pageable pageable) {
		log.debug("Request to get all SponsoringInstitutions");
		return sponsoringInstitutionRepository.findAll(pageable).map(sponsoringInstitutionMapper::toDto);
	}

	/**
	 * Get one sponsoringInstitution by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<SponsoringInstitutionDTO> findOne(Long id) {
		log.debug("Request to get SponsoringInstitution : {}", id);
		return sponsoringInstitutionRepository.findById(id).map(sponsoringInstitutionMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<SponsoringInstitutionDTO> findByCurrentUser() {
		log.debug("Request to get SponsoringInstitution by current user: {}");
		return sponsoringInstitutionRepository.findByUserIsCurrentUser().map(sponsoringInstitutionMapper::toDto);
	}

	/**
	 * Delete the sponsoringInstitution by id.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete SponsoringInstitution : {}", id);
		sponsoringInstitutionRepository.deleteById(id);
	}
}
