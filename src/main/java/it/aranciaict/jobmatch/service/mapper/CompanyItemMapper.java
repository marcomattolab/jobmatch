package it.aranciaict.jobmatch.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.aranciaict.jobmatch.domain.Company;
import it.aranciaict.jobmatch.service.dto.CompanyItemDTO;

/**
 * Mapper for the entity Candidate and its DTO CandidateDTO.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface CompanyItemMapper extends ItemMapper<CompanyItemDTO, Company> {

	@Override
	@Mapping(source = "user.id", target = "userId")
	@Mapping(source = "user.login", target = "userName")
	@Mapping(source = "user.imageUrl", target = "imageUrl")
	@Mapping(source = "sector.id", target = "sectorId")
	@Mapping(source = "sponsoringInstitution.id", target = "sponsoringInstitutionId")
	CompanyItemDTO toDto(Company company);


	
}
