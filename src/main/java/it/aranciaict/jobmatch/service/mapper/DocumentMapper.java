package it.aranciaict.jobmatch.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.aranciaict.jobmatch.domain.Document;
import it.aranciaict.jobmatch.service.dto.DocumentDTO;

/**
 * Mapper for the entity Document and its DTO DocumentDTO.
 */
@Mapper(componentModel = "spring", uses = {CandidateMapper.class})
public interface DocumentMapper extends EntityMapper<DocumentDTO, Document> {


	@Override
    @Mapping(source = "candidate.id", target = "candidateId")
    DocumentDTO toDto(Document document);

	@Override
    @Mapping(source = "candidateId", target = "candidate")
    Document toEntity(DocumentDTO documentDTO);

    /**
     * From id.
     *
     * @param id the id
     * @return the document
     */
    default Document fromId(Long id) {
        if (id == null) {
            return null;
        }
        Document document = new Document();
        document.setId(id);
        return document;
    }
}
