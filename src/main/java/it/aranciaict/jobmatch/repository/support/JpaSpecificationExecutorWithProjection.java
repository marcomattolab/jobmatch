package it.aranciaict.jobmatch.repository.support;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.Nullable;

/**
 * The Interface JpaSpecificationExecutorWithProjection.
 *
 * @param <T> the generic type
 */
@NoRepositoryBean
public interface JpaSpecificationExecutorWithProjection<T> {
	
	/**
	 * Find one.
	 *
	 * @param <R> the generic type
	 * @param spec the spec
	 * @param projectionClass the projection class
	 * @return the optional
	 */
	<R> Optional<R> findOne(Specification<T> spec, Class<R> projectionClass);

	/**
	 * Find all.
	 *
	 * @param <R> the generic type
	 * @param spec the spec
	 * @param projectionType the projection type
	 * @return the page
	 */
	<R>List<R> findAll(Specification<T> spec, Class<R> projectionType);
	
	/**
	 * Find all.
	 *
	 * @param <R> the generic type
	 * @param spec the spec
	 * @param projectionClass the projection class
	 * @param pageable the pageable
	 * @return the page
	 */
	<R> Page<R> findAll(Specification<T> spec, Class<R> projectionClass, Pageable pageable);


	/**
	 * Count.
	 *
	 * @param spec the spec
	 * @return the long
	 */
	public long count(@Nullable Specification<T> spec);


	

}
