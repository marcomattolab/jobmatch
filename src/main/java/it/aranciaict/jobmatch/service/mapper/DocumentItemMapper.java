package it.aranciaict.jobmatch.service.mapper;

import org.mapstruct.Mapper;

import it.aranciaict.jobmatch.repository.result.DocumentItem;
import it.aranciaict.jobmatch.service.dto.DocumentDTO;

/**
 * Mapper for the entity Document and its DTO DocumentDTO.
 */
@Mapper(componentModel = "spring", uses = {CandidateMapper.class})
public interface DocumentItemMapper extends ItemMapper<DocumentDTO, DocumentItem> {

	@Override
    DocumentDTO toDto(DocumentItem documentItem);

}
