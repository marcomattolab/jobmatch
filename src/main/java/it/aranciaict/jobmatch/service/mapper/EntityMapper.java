package it.aranciaict.jobmatch.service.mapper;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * Contract for a generic dto to entity mapper.
 *
 * @param <D> - DTO type parameter.
 * @param <E> - Entity type parameter.
 */

public interface EntityMapper <D, E> {

    /**
     * To entity.
     *
     * @param dto the dto
     * @return the e
     */
    E toEntity(D dto);

    /**
     * To dto.
     *
     * @param entity the entity
     * @return the d
     */
    D toDto(E entity);

    /**
     * To entity.
     *
     * @param dtoList the dto list
     * @return the list
     */
    List <E> toEntity(List<D> dtoList);

    /**
     * To dto.
     *
     * @param entityList the entity list
     * @return the list
     */
    List <D> toDto(List<E> entityList);
}
