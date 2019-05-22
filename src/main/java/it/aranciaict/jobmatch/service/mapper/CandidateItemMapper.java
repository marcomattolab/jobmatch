package it.aranciaict.jobmatch.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.aranciaict.jobmatch.domain.Candidate;
import it.aranciaict.jobmatch.service.dto.CandidateItemDTO;

/**
 * Mapper for the entity Candidate and its DTO CandidateDTO.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class, SkillMapper.class })
public interface CandidateItemMapper extends ItemMapper<CandidateItemDTO, Candidate> {

	@Override
	@Mapping(source = "user.id", target = "userId")
	@Mapping(source = "user.login", target = "userName")
	@Mapping(source = "user.imageUrl", target = "imageUrl")
	@Mapping(source = "currentJobExperience", target = "currentJobExperience")
	CandidateItemDTO toDto(Candidate candidate);
	
	
}
