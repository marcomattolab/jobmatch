package it.aranciaict.jobmatch.service.mapper;

import java.util.Locale;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import it.aranciaict.jobmatch.domain.CompanySector;
import it.aranciaict.jobmatch.service.dto.CompanySectorDTO;

/**
 * Mapper for the entity CompanySector and its DTO CompanySectorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CompanySectorMapper extends EntityMapper<CompanySectorDTO, CompanySector> {
	

	@Override
	@Mapping(source = "descriptionEN", target = "description")
    CompanySectorDTO toDto(CompanySector entity);


	/**
     * To dto.
     *
     * @param entity the entity
     * @param locale the locale
     * @return the d
     */
    CompanySectorDTO toDto(CompanySector entity, Locale locale);


    /**
     * From id.
     *
     * @param id the id
     * @return the company sector
     */
    default CompanySector fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompanySector companySector = new CompanySector();
        companySector.setId(id);
        return companySector;
    }
    
    
	/**
	 * Sets the description.
	 *
	 * @param entity       the entity
	 * @param dto          the dto
	 * @param userLanguage the user language
	 */
	@AfterMapping
	default void setDescription(CompanySector entity, @MappingTarget CompanySectorDTO dto, Locale userLanguage) {
		if (Locale.ITALIAN.equals(userLanguage)) {
			dto.setDescription(entity.getDescriptionIT());
		} else {
			dto.setDescription(entity.getDescriptionEN());
		}
	}
}
