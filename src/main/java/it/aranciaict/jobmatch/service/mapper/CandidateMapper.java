package it.aranciaict.jobmatch.service.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import it.aranciaict.jobmatch.domain.Candidate;
import it.aranciaict.jobmatch.service.dto.CandidateDTO;
import it.aranciaict.jobmatch.service.util.actions.CandidateActionsUtils;

/**
 * Mapper for the entity Candidate and its DTO CandidateDTO.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface CandidateMapper extends EntityMapper<CandidateDTO, Candidate> {

	@Override
	@Mapping(source = "user.id", target = "userId")
	@Mapping(source = "user.login", target = "userName")
	@Mapping(source = "user.imageUrl", target = "imageUrl")
	CandidateDTO toDto(Candidate candidate);

	@Override
	@Mapping(source = "userId", target = "user")
	@Mapping(target = "documents", ignore = true)
	@Mapping(target = "jobExperiences", ignore = true)
	@Mapping(target = "educations", ignore = true)
	@Mapping(target = "skills", ignore = true)
	Candidate toEntity(CandidateDTO candidateDTO);

	/**
	 * From id.
	 *
	 * @param id the id
	 * @return the candidate
	 */
	default Candidate fromId(Long id) {
		if (id == null) {
			return null;
		}
		Candidate candidate = new Candidate();
		candidate.setId(id);
		return candidate;
	}
	
    /**
     * Sets the permissions.
     *
     * @param entity the entity
     * @param dto the dto
     */
    @AfterMapping
    default void setPermissions(Candidate entity, @MappingTarget CandidateDTO dto) {
    	dto.setEditAvailable(CandidateActionsUtils.isEditAvailable(entity));
    	dto.setDeleteAvailable(CandidateActionsUtils.isDeleteAvailable(entity));
    }
}
