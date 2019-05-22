package it.aranciaict.jobmatch.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.aranciaict.jobmatch.domain.SponsoringInstitution;
import it.aranciaict.jobmatch.service.dto.SponsoringInstitutionItemDTO;


/**
 * The Interface sponsoringInstitutionItemMapper.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface SponsoringInstitutionItemMapper extends ItemMapper<SponsoringInstitutionItemDTO, SponsoringInstitution> {

	@Override
	@Mapping(source = "user.id", target = "userId")
	@Mapping(source = "user.login", target = "userName")
	SponsoringInstitutionItemDTO toDto(SponsoringInstitution sponsoringInstitution);


	
}
