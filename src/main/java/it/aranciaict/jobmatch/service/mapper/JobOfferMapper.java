package it.aranciaict.jobmatch.service.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import it.aranciaict.jobmatch.domain.JobOffer;
import it.aranciaict.jobmatch.service.dto.JobOfferDTO;
import it.aranciaict.jobmatch.service.util.actions.JobOfferActionsUtils;

/**
 * Mapper for the entity JobOffer and its DTO JobOfferDTO.
 */
@Mapper(componentModel = "spring", uses = { CompanyMapper.class, CompanySectorMapper.class, ProjectMapper.class,
		JobOfferSkillMapper.class })
public interface JobOfferMapper extends EntityMapper<JobOfferDTO, JobOffer> {

	@Override
	@Mapping(source = "skills", target = "skills")
	@Mapping(source = "company.id", target = "companyId")
	@Mapping(source = "sector.id", target = "sectorId")
	@Mapping(source = "project.id", target = "projectId")
	@Mapping(source = "company.user.imageUrl", target = "imageUrl")
	@Mapping(source = "company.companyName", target = "companyName")
	@Mapping(source = "company.sponsoringInstitution.id", target = "sponsoringInstitutionId")
	JobOfferDTO toDto(JobOffer jobOffer);

	@Override
	@Mapping(target = "skills", ignore = true)
	@Mapping(source = "companyId", target = "company")
	@Mapping(source = "sectorId", target = "sector")
	@Mapping(source = "projectId", target = "project")
	JobOffer toEntity(JobOfferDTO jobOfferDTO);

	/**
	 * From id.
	 *
	 * @param id the id
	 * @return the job offer
	 */
	default JobOffer fromId(Long id) {
		if (id == null) {
			return null;
		}
		JobOffer jobOffer = new JobOffer();
		jobOffer.setId(id);
		return jobOffer;
	}

	/**
	 * Sets the permissions.
	 *
	 * @param entity the entity
	 * @param dto    the dto
	 */
	@AfterMapping
	default void setPermissions(JobOffer entity, @MappingTarget JobOfferDTO dto) {
		dto.setEditAvailable(JobOfferActionsUtils.isEditAvailable(entity));
		dto.setDeleteAvailable(JobOfferActionsUtils.isDeleteAvailable(entity));
		dto.setAppliedJobAvailable(JobOfferActionsUtils.isAppliedJobAvailable(entity));
	}
}
