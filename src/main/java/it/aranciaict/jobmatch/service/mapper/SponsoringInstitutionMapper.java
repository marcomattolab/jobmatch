package it.aranciaict.jobmatch.service.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import it.aranciaict.jobmatch.domain.SponsoringInstitution;
import it.aranciaict.jobmatch.service.dto.SponsoringInstitutionDTO;
import it.aranciaict.jobmatch.service.util.actions.SponsoringInstitutionActionsUtils;

/**
 * Mapper for the entity SponsoringInstitution and its DTO SponsoringInstitutionDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface SponsoringInstitutionMapper extends EntityMapper<SponsoringInstitutionDTO, SponsoringInstitution> {

	@Override
    @Mapping(source = "user.id", target = "userId")
	@Mapping(source = "user.imageUrl", target = "imageUrl")
    SponsoringInstitutionDTO toDto(SponsoringInstitution sponsoringInstitution);

	@Override
    @Mapping(source = "userId", target = "user")
    SponsoringInstitution toEntity(SponsoringInstitutionDTO sponsoringInstitutionDTO);

    /**
     * From id.
     *
     * @param id the id
     * @return the sponsoring institution
     */
    default SponsoringInstitution fromId(Long id) {
        if (id == null) {
            return null;
        }
        SponsoringInstitution sponsoringInstitution = new SponsoringInstitution();
        sponsoringInstitution.setId(id);
        return sponsoringInstitution;
    }
    
    /**
	 * Sets the permissions.
	 *
	 * @param entity the entity
	 * @param dto    the dto
	 */
	@AfterMapping
	default void setPermissions(SponsoringInstitution entity, @MappingTarget SponsoringInstitutionDTO dto) {
		dto.setEditAvailable(SponsoringInstitutionActionsUtils.isEditAvailable(entity));
		dto.setDeleteAvailable(SponsoringInstitutionActionsUtils.isDeleteAvailable(entity));
	}
}
