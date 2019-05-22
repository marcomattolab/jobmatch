package it.aranciaict.jobmatch.service.mapper;

import org.mapstruct.Mapper;

import it.aranciaict.jobmatch.domain.item.CompanyInfo;
import it.aranciaict.jobmatch.service.dto.CompanyInfoDTO;

/**
 * Mapper for the entity Company and its DTO CompanyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CompanyInfoMapper extends ItemMapper<CompanyInfoDTO, CompanyInfo> {

	@Override
	CompanyInfoDTO toDto(CompanyInfo company);
}
