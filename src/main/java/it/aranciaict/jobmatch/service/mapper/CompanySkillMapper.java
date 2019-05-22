package it.aranciaict.jobmatch.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.aranciaict.jobmatch.domain.CompanySkill;
import it.aranciaict.jobmatch.service.dto.CompanySkillDTO; 

/**
 * Mapper for the entity CompanySkill and its DTO CompanySkillDTO.
 */
@Mapper(componentModel = "spring", uses = { SkillTagMapper.class, CompanyMapper.class })
public interface CompanySkillMapper extends EntityMapper<CompanySkillDTO, CompanySkill> {

	@Override
	@Mapping(source = "skillTag.id", target = "skillTagId")
	@Mapping(source = "skillTag.name", target = "skillTagName")
    @Mapping(source = "skillTag.type", target = "skillTagType") 
	@Mapping(source = "company.id", target = "companyId")
	CompanySkillDTO toDto(CompanySkill companySkill);

	@Override
	@Mapping(source = "skillTagId", target = "skillTag")
	@Mapping(source = "companyId", target = "company")
	CompanySkill toEntity(CompanySkillDTO companySkillDTO);

	/**
	 * From id.
	 *
	 * @param id the id
	 * @return the company skill
	 */
	default CompanySkill fromId(Long id) {
		if (id == null) {
			return null;
		}
		CompanySkill companySkill = new CompanySkill();
		companySkill.setId(id);
		return companySkill;
	}
}
