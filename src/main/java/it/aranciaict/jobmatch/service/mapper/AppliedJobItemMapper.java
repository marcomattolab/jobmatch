package it.aranciaict.jobmatch.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.aranciaict.jobmatch.domain.AppliedJob;
import it.aranciaict.jobmatch.service.dto.AppliedJobItemDTO;

/**
 * Mapper for the entity AppliedJob and its DTO AppliedJobDTO.
 */
@Mapper(componentModel = "spring", uses = {CandidateMapper.class, JobOfferMapper.class})
public interface AppliedJobItemMapper extends ItemMapper<AppliedJobItemDTO, AppliedJob> {

	@Override
    @Mapping(source = "candidate.id", target = "candidateId")
    @Mapping(source = "candidate.lastName", target = "lastName")
    @Mapping(source = "candidate.firstName", target = "firstName")
	@Mapping(source = "jobOffer.id", target = "jobOfferId")
	@Mapping(source = "jobOffer.jobTitle", target = "jobOfferTitle")
	@Mapping(source = "jobOffer.jobCity", target = "jobOfferCity")
	@Mapping(source = "jobOffer.jobCountry", target = "jobOfferCountry")
	@Mapping(source = "jobOffer.company.id", target = "companyId")
	@Mapping(source = "jobOffer.company.companyName", target = "companyName")
	@Mapping(source = "jobOffer.company.user.imageUrl", target = "imageUrl")
    AppliedJobItemDTO toDto(AppliedJob appliedJob);
}
