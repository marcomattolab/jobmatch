package it.aranciaict.jobmatch.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.aranciaict.jobmatch.domain.DirectApplication;
import it.aranciaict.jobmatch.service.dto.DirectApplicationDTO;

/**
 * Mapper for the entity DirectApplication and its DTO DirectApplicationDTO.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class, CandidateMapper.class})
public interface DirectApplicationMapper extends EntityMapper<DirectApplicationDTO, DirectApplication> {

	@Override
    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "candidate.id", target = "candidateId")
    DirectApplicationDTO toDto(DirectApplication directApplication);

	@Override
    @Mapping(source = "companyId", target = "company")
    @Mapping(source = "candidateId", target = "candidate")
    DirectApplication toEntity(DirectApplicationDTO directApplicationDTO);

    /**
     * From id.
     *
     * @param id the id
     * @return the direct application
     */
    default DirectApplication fromId(Long id) {
        if (id == null) {
            return null;
        }
        DirectApplication directApplication = new DirectApplication();
        directApplication.setId(id);
        return directApplication;
    }
}
