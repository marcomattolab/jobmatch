package it.aranciaict.jobmatch.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.aranciaict.jobmatch.domain.AppliedJob;
import it.aranciaict.jobmatch.service.dto.email.AppliedJobEmailDTO;

/**
 * Mapper for the entity AppliedJob and its DTO AppliedJobDTO.
 */
@Mapper(componentModel = "spring", uses = {CandidateMapper.class, JobOfferMapper.class, UserMapper.class, CompanyMapper.class})
public interface AppliedJobEmailMapper extends EntityMapper<AppliedJobEmailDTO, AppliedJob> {

	@Override
    @Mapping(source = "candidate.id", target = "candidateId")
    @Mapping(source = "candidate.lastName", target = "lastName")
    @Mapping(source = "candidate.firstName", target = "firstName")
    @Mapping(source = "jobOffer.id", target = "jobOfferId")
    @Mapping(source = "jobOffer.jobTitle", target = "jobOfferTitle")
    AppliedJobEmailDTO toDto(AppliedJob appliedJob);
}
