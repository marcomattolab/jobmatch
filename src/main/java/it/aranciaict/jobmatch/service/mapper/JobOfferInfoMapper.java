package it.aranciaict.jobmatch.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.aranciaict.jobmatch.domain.JobOffer;
import it.aranciaict.jobmatch.domain.item.JobOfferInfo;
import it.aranciaict.jobmatch.service.dto.JobOfferDTO;

/**
 * Mapper for the entity JobOffer and its DTO JobOfferDTO.
 */
@Mapper(componentModel = "spring", uses = { CompanyMapper.class })
public interface JobOfferInfoMapper extends ItemMapper<JobOfferDTO, JobOfferInfo> {

	@Override
	@Mapping(source = "company.user.imageUrl", target = "imageUrl")
	@Mapping(source = "company.id", target = "companyId")
	@Mapping(source = "company.companyName", target = "companyName")
	@Mapping(source = "company.sponsoringInstitution.id", target = "sponsoringInstitutionId")
	JobOfferDTO toDto(JobOfferInfo jobOfferInfo);

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

}
