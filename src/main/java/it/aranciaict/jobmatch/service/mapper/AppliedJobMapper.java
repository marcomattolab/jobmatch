package it.aranciaict.jobmatch.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.aranciaict.jobmatch.domain.AppliedJob;
import it.aranciaict.jobmatch.service.dto.AppliedJobDTO;

/**
 * Mapper for the entity AppliedJob and its DTO AppliedJobDTO.
 */
@Mapper(componentModel = "spring", uses = {CandidateMapper.class, JobOfferMapper.class})
public interface AppliedJobMapper extends EntityMapper<AppliedJobDTO, AppliedJob> {

	@Override
    @Mapping(source = "candidate.id", target = "candidateId")
    @Mapping(source = "jobOffer.id", target = "jobOfferId")
    AppliedJobDTO toDto(AppliedJob appliedJob);

	@Override
    @Mapping(target = "appliedJobIterations", ignore = true)
    @Mapping(source = "candidateId", target = "candidate")
    @Mapping(source = "jobOfferId", target = "jobOffer")
    AppliedJob toEntity(AppliedJobDTO appliedJobDTO);

    /**
     * From id.
     *
     * @param id the id
     * @return the applied job
     */
    default AppliedJob fromId(Long id) {
        if (id == null) {
            return null;
        }
        AppliedJob appliedJob = new AppliedJob();
        appliedJob.setId(id);
        return appliedJob;
    }
}
