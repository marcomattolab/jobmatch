package it.aranciaict.jobmatch.service.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import it.aranciaict.jobmatch.domain.Company;
import it.aranciaict.jobmatch.service.dto.CompanyDTO;
import it.aranciaict.jobmatch.service.util.actions.CompanyActionsUtils;

/**
 * Mapper for the entity Company and its DTO CompanyDTO.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class, CompanySectorMapper.class })
public interface CompanyMapper extends EntityMapper<CompanyDTO, Company> {

	@Override
	@Mapping(source = "user.id", target = "userId")
	@Mapping(source = "user.imageUrl", target = "imageUrl")
	@Mapping(source = "sector.id", target = "sectorId")
	@Mapping(source = "sponsoringInstitution.id", target = "sponsoringInstitutionId")
	CompanyDTO toDto(Company company);

	@Override
	@Mapping(source = "userId", target = "user")
	@Mapping(target = "skills", ignore = true)
	@Mapping(source = "sectorId", target = "sector")
	Company toEntity(CompanyDTO companyDTO);

	/**
	 * From id.
	 *
	 * @param id the id
	 * @return the company
	 */
	default Company fromId(Long id) {
		if (id == null) {
			return null;
		}
		Company company = new Company();
		company.setId(id);
		return company;
	}

	/**
	 * Sets the permissions.
	 *
	 * @param entity the entity
	 * @param dto    the dto
	 */
	@AfterMapping
	default void setPermissions(Company entity, @MappingTarget CompanyDTO dto) {
		dto.setEditAvailable(CompanyActionsUtils.isEditAvailable(entity));
		dto.setDeleteAvailable(CompanyActionsUtils.isDeleteAvailable(entity));
		dto.setApplicationAvailable(CompanyActionsUtils.isApplicationAvailable(entity));
	}
}
