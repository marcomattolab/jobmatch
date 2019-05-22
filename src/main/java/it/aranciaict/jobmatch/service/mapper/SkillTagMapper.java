package it.aranciaict.jobmatch.service.mapper;

import org.mapstruct.Mapper;

import it.aranciaict.jobmatch.domain.SkillTag;
import it.aranciaict.jobmatch.service.dto.SkillTagDTO;

// TODO: Auto-generated Javadoc
/**
 * Mapper for the entity SkillTag and its DTO SkillTagDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SkillTagMapper extends EntityMapper<SkillTagDTO, SkillTag> {



    /**
     * From id.
     *
     * @param id the id
     * @return the skill tag
     */
    default SkillTag fromId(Long id) {
        if (id == null) {
            return null;
        }
        SkillTag skillTag = new SkillTag();
        skillTag.setId(id);
        return skillTag;
    }
}
