package it.aranciaict.jobmatch.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.aranciaict.jobmatch.domain.Skill;
import it.aranciaict.jobmatch.service.dto.SkillDTO;

/**
 * Mapper for the entity Skill and its DTO SkillDTO.
 */
@Mapper(componentModel = "spring", uses = {SkillTagMapper.class, CandidateMapper.class})
public interface SkillMapper extends EntityMapper<SkillDTO, Skill> {

	@Override
    @Mapping(source = "skillTag.id", target = "skillTagId")
    @Mapping(source = "skillTag.name", target = "skillTagName")
    @Mapping(source = "skillTag.type", target = "skillTagType") 
    @Mapping(source = "candidate.id", target = "candidateId")
    SkillDTO toDto(Skill skill);

    @Override
    @Mapping(source = "skillTagId", target = "skillTag.id")
    @Mapping(source = "candidateId", target = "candidate")
    Skill toEntity(SkillDTO skillDTO);

    /**
     * From id.
     *
     * @param id the id
     * @return the skill
     */
    default Skill fromId(Long id) {
        if (id == null) {
            return null;
        }
        Skill skill = new Skill();
        skill.setId(id);
        return skill;
    }
}
