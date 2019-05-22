package it.aranciaict.jobmatch.service.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import it.aranciaict.jobmatch.domain.Project;
import it.aranciaict.jobmatch.service.dto.ProjectDTO;
import it.aranciaict.jobmatch.service.util.actions.ProjectActionsUtils;

/**
 * Mapper for the entity Project and its DTO ProjectDTO.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class, CompanySectorMapper.class})
public interface ProjectMapper extends EntityMapper<ProjectDTO, Project> {

	@Override
    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "sector.id", target = "sectorId")
    ProjectDTO toDto(Project project);

	@Override
    @Mapping(target = "jobOffers", ignore = true)
    @Mapping(source = "companyId", target = "company")
    @Mapping(source = "sectorId", target = "sector")
    Project toEntity(ProjectDTO projectDTO);

    /**
     * From id.
     *
     * @param id the id
     * @return the project
     */
    default Project fromId(Long id) {
        if (id == null) {
            return null;
        }
        Project project = new Project();
        project.setId(id);
        return project;
    }
    
    /**
     * Sets the permissions.
     *
     * @param project the project
     * @param dto the dto
     */
    @AfterMapping
    default void setPermissions(Project project, @MappingTarget ProjectDTO dto) {
    	dto.setEditAvailable(ProjectActionsUtils.isEditAvailable(project));
    	dto.setDeleteAvailable(ProjectActionsUtils.isDeleteAvailable(project));
    }
    
}
