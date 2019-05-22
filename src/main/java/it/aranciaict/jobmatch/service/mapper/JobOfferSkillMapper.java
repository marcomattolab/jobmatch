package it.aranciaict.jobmatch.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.aranciaict.jobmatch.domain.JobOfferSkill;
import it.aranciaict.jobmatch.service.dto.JobOfferSkillDTO;

/**
 * Mapper for the entity JobOfferSkill and its DTO JobOfferSkillDTO.
 */
@Mapper(componentModel = "spring", uses = {SkillTagMapper.class, JobOfferMapper.class})
public interface JobOfferSkillMapper extends EntityMapper<JobOfferSkillDTO, JobOfferSkill> {

	@Override
    @Mapping(source = "skillTag.id", target = "skillTagId")
    @Mapping(source = "skillTag.name", target = "skillTagName")
    @Mapping(source = "jobOffer.id", target = "jobOfferId")
    JobOfferSkillDTO toDto(JobOfferSkill jobOfferSkill);

	@Override
    @Mapping(source = "skillTagId", target = "skillTag")
    @Mapping(source = "jobOfferId", target = "jobOffer")
    JobOfferSkill toEntity(JobOfferSkillDTO jobOfferSkillDTO);

    /**
     * From id.
     *
     * @param id the id
     * @return the job offer skill
     */
    default JobOfferSkill fromId(Long id) {
        if (id == null) {
            return null;
        }
        JobOfferSkill jobOfferSkill = new JobOfferSkill();
        jobOfferSkill.setId(id);
        return jobOfferSkill;
    }
}
