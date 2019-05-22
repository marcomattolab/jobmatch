package it.aranciaict.jobmatch.service.impl;

import it.aranciaict.jobmatch.service.CompanyService;
import it.aranciaict.jobmatch.domain.Company;
import it.aranciaict.jobmatch.repository.CompanyRepository;
import it.aranciaict.jobmatch.service.dto.CompanyDTO;
import it.aranciaict.jobmatch.service.mapper.CompanyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Company.
 */
@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);

    /** The company repository. */
    private final CompanyRepository companyRepository;

    /** The company mapper. */
    private final CompanyMapper companyMapper;
    
    /**
     * Instantiates a new company service impl.
     *
     * @param companyRepository the company repository
     * @param companyMapper the company mapper
     */
    public CompanyServiceImpl(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    /**
     * Save a company.
     *
     * @param companyDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CompanyDTO save(CompanyDTO companyDTO) {
        log.debug("Request to save Company : {}", companyDTO);
        Company company = companyMapper.toEntity(companyDTO);
        company = companyRepository.save(company);
        return companyMapper.toDto(company);
    }

    /**
     * Get all the companies.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompanyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Companies");
        return companyRepository.findAll(pageable)
            .map(companyMapper::toDto);
    }

//    /**
//     * Get all the Company with eager load of many-to-many relationships.
//     *
//     * @param pageable the pageable
//     * @return the list of entities
//     */
//    public Page<CompanyDTO> findAllWithEagerRelationships(Pageable pageable) {
//        return companyRepository.findAllWithEagerRelationships(pageable).map(companyMapper::toDto);
//    }
    

    /**
     * Get one company by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CompanyDTO> findOne(Long id) {
        log.debug("Request to get Company : {}", id);
        return companyRepository.findById(id)
                .map(companyMapper::toDto);
//        return companyRepository.findOneWithEagerRelationships(id)
//            .map(companyMapper::toDto);
    }
    
	@Override
	@Transactional(readOnly = true)
	public Optional<CompanyDTO> findByCurrentUser() {
		log.debug("Request to get Company by current user: {}");
		return companyRepository.findByUserIsCurrentUser().map(companyMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Long> findCompanyIdByCurrentUser() {
		log.debug("Request to get Company by current user: {}");
		return companyRepository.findByUserIsCurrentUser().map(company -> company.getId());
	}

	
    /**
     * Delete the company by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Company : {}", id);
        companyRepository.deleteById(id);
    }


}
