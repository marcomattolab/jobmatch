package it.aranciaict.jobmatch.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.aranciaict.jobmatch.repository.CompanyRepository;
import it.aranciaict.jobmatch.service.CompanyInfoService;
import it.aranciaict.jobmatch.service.dto.CompanyInfoDTO;
import it.aranciaict.jobmatch.service.mapper.CompanyInfoMapper;

/**
 * Service Implementation for managing Company.
 */
@Service
@Transactional(readOnly = true)
public class CompanyInfoServiceImpl implements CompanyInfoService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(CompanyInfoServiceImpl.class);

	/** The company repository. */
	private final CompanyRepository companyRepository;

	/** The company mapper. */
	private final CompanyInfoMapper companyInfoMapper;

	/**
	 * Instantiates a new company service impl.
	 *
	 * @param companyRepository the company repository
	 * @param companyInfoMapper the company info mapper
	 */
	public CompanyInfoServiceImpl(CompanyRepository companyRepository, CompanyInfoMapper companyInfoMapper) {
		this.companyRepository = companyRepository;
		this.companyInfoMapper = companyInfoMapper;
	}

	/**
	 * Get one company by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Override
	public Optional<CompanyInfoDTO> findOne(Long id) {
		log.debug("Request to get Company info: {}", id);
		return companyRepository.findInfoById(id).map(companyInfoMapper::toDto);
	}

	@Override
	public Optional<CompanyInfoDTO> findByUserIsCurrentUser() {
		log.debug("Request to get Company info by Current User");
		return companyRepository.findInfoByUserIsCurrentUser().map(companyInfoMapper::toDto);
	}

	@Override
	public Optional<CompanyInfoDTO> findBySponsoringInstitutionId(Long sponsoringInstitutionId) {
		log.debug("Request to get Company info by SponsoringInstitutionId {}", sponsoringInstitutionId);
		return companyRepository.findInfoBySponsoringInstitutionId(sponsoringInstitutionId).map(companyInfoMapper::toDto);
	}

}
