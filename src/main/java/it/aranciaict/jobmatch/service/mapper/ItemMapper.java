package it.aranciaict.jobmatch.service.mapper;

import java.util.List;

/**
 * Contract for a generic dto to entity mapper.
 *
 * @param <D> - DTO type parameter.
 * @param <E> - Entity type parameter.
 */

public interface ItemMapper <D, E> {

    /**
     * To dto.
     *
     * @param entity the entity
     * @return the d
     */
    D toDto(E entity);

    /**
     * To dto.
     *
     * @param entityList the entity list
     * @return the list
     */
    List <D> toDto(List<E> entityList);
}
