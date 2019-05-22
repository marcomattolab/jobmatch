package it.aranciaict.jobmatch.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.aranciaict.jobmatch.domain.Education;
import it.aranciaict.jobmatch.service.dto.EducationDTO;

/**
 * Mapper for the entity Education and its DTO EducationDTO.
 */
@Mapper(componentModel = "spring", uses = {CandidateMapper.class})
public interface EducationMapper extends EntityMapper<EducationDTO, Education> {


	@Override
    @Mapping(source = "candidate.id", target = "candidateId")
    EducationDTO toDto(Education education);

	@Override
    @Mapping(source = "candidateId", target = "candidate")
    Education toEntity(EducationDTO educationDTO);

    /**
     * From id.
     *
     * @param id the id
     * @return the education
     */
    default Education fromId(Long id) {
        if (id == null) {
            return null;
        }
        Education education = new Education();
        education.setId(id);
        return education;
    }
}
