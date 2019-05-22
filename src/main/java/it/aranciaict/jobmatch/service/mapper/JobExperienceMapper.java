package it.aranciaict.jobmatch.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.aranciaict.jobmatch.domain.JobExperience;
import it.aranciaict.jobmatch.service.dto.JobExperienceDTO;

/**
 * Mapper for the entity JobExperience and its DTO JobExperienceDTO.
 */
@Mapper(componentModel = "spring", uses = {CandidateMapper.class})
public interface JobExperienceMapper extends EntityMapper<JobExperienceDTO, JobExperience> {

	@Override
    @Mapping(source = "candidate.id", target = "candidateId")
    JobExperienceDTO toDto(JobExperience jobExperience);

    @Override
    @Mapping(source = "candidateId", target = "candidate")
    JobExperience toEntity(JobExperienceDTO jobExperienceDTO);

    /**
     * From id.
     *
     * @param id the id
     * @return the job experience
     */
    default JobExperience fromId(Long id) {
        if (id == null) {
            return null;
        }
        JobExperience jobExperience = new JobExperience();
        jobExperience.setId(id);
        return jobExperience;
    }
}
