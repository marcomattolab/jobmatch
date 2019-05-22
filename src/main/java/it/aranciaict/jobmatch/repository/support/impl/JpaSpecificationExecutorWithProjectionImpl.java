package it.aranciaict.jobmatch.repository.support.impl;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import it.aranciaict.jobmatch.repository.support.JpaSpecificationExecutorWithProjection;

public class JpaSpecificationExecutorWithProjectionImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
		implements JpaSpecificationExecutorWithProjection<T> {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(JpaSpecificationExecutorWithProjectionImpl.class);

	/** The entity manager. */
	private final EntityManager entityManager;

	/** The projection factory. */
	private final ProjectionFactory projectionFactory = new SpelAwareProxyProjectionFactory();

	/** The entity information. */
	private final JpaEntityInformation entityInformation;

	private @Nullable CrudMethodMetadata metadata;

	@Override
	public void setRepositoryMethodMetadata(CrudMethodMetadata crudMethodMetadata) {
		this.metadata = crudMethodMetadata;
	}

	@Override
	@Nullable
	protected CrudMethodMetadata getRepositoryMethodMetadata() {
		return metadata;
	}

	/**
	 * Instantiates a new jpa specification executor with projection impl.
	 *
	 * @param entityInformation the entity information
	 * @param entityManager     the entity manager
	 */
	public JpaSpecificationExecutorWithProjectionImpl(JpaEntityInformation entityInformation,
			EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityManager = entityManager;
		this.entityInformation = entityInformation;
	}

	@Override
	public <R> Optional<R> findOne(Specification<T> spec, Class<R> projectionType) {
		TypedQuery<T> query = getQuery(spec, Sort.unsorted());
		try {
			T result = query.getSingleResult();
			return Optional.ofNullable(projectionFactory.createProjection(projectionType, result));
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}

	@Override
	public <R> List<R> findAll(Specification<T> spec, Class<R> projectionType) {
		TypedQuery<R> query = getProjectionQuery(spec, projectionType, Sort.unsorted());
		return readProjection(spec, projectionType, query);
	}

	@Override
	public <R> Page<R> findAll(Specification<T> spec, Class<R> projectionType, Pageable pageable) {
		TypedQuery<R> query = getProjectionQuery(spec, projectionType, pageable);
		return readPageProjection(spec, projectionType, pageable, query);
	}

//	/**
//	 * Read page with projection.
//	 *
//	 * @param                <R> the generic type
//	 * @param spec           the spec
//	 * @param projectionType the projection type
//	 * @param query          the query
//	 * @return the page
//	 */
//	private <R> List<R> readWithProjection(Specification<T> spec, Class<R> projectionType, TypedQuery<T> query) {
//		if (LOG.isDebugEnabled()) {
//			query.getHints().forEach((key, value) -> LOG.info("apply query hints -> {} : {}", key, value));
//		}
//		List<T> resultList = query.getResultList();
//		return (List<R>) resultList.stream().map(item -> projectionFactory.createProjection(projectionType, item));
//	}

	/**
	 * Read projection.
	 *
	 * @param                <R> the generic type
	 * @param spec           the spec
	 * @param projectionType the projection type
	 * @param query          the query
	 * @return the list
	 */
	private <R> List<R> readProjection(Specification<T> spec, Class<R> projectionType, TypedQuery<R> query) {
		if (LOG.isDebugEnabled()) {
			query.getHints().forEach((key, value) -> LOG.info("apply query hints -> {} : {}", key, value));
		}
		List<R> resultList = query.getResultList();
		return (List<R>) resultList;
	}

	/**
	 * Read page with projection.
	 *
	 * @param                <R> the generic type
	 * @param spec           the spec
	 * @param projectionType the projection type
	 * @param pageable       the pageable
	 * @param query          the query
	 * @return the page
	 */
	private <R> Page<R> readPageProjection(Specification<T> spec, Class<R> projectionType, Pageable pageable,
			TypedQuery<R> query) {
		if (LOG.isDebugEnabled()) {
			query.getHints().forEach((key, value) -> LOG.info("apply query hints -> {} : {}", key, value));
		}
		Page<R> result = pageable.isUnpaged() ? new PageImpl<>(query.getResultList())
				: readPageProjection(query, pageable, spec);
		return result;
	}

	/**
	 * Read page projection.
	 *
	 * @param          <R> the generic type
	 * @param query    the query
	 * @param pageable the pageable
	 * @param spec     the spec
	 * @return the page
	 */
	protected <R> Page<R> readPageProjection(TypedQuery<R> query, Pageable pageable, @Nullable Specification<T> spec) {

		if (pageable.isPaged()) {
			query.setFirstResult((int) pageable.getOffset());
			query.setMaxResults(pageable.getPageSize());
		}
		return PageableExecutionUtils.getPage(query.getResultList(), pageable,
				() -> executeCountQuery(getCountQuery(spec, getDomainClass())));

	}

	/**
	 * Executes a count query and transparently sums up all values returned.
	 *
	 * @param query must not be {@literal null}.
	 * @return the long
	 */
	private static long executeCountQuery(TypedQuery<Long> query) {

		Assert.notNull(query, "TypedQuery must not be null!");

		List<Long> totals = query.getResultList();
		long total = 0L;

		for (Long element : totals) {
			total += element == null ? 0 : element;
		}

		return total;
	}

	/**
	 * Gets the projection query.
	 *
	 * @param                <R> the generic type
	 * @param spec           the spec
	 * @param projectionType the projection type
	 * @param pageable       the pageable
	 * @return the projection query
	 */
	protected <R> TypedQuery<R> getProjectionQuery(@Nullable Specification<T> spec, Class<R> projectionType,
			Pageable pageable) {
		Sort sort = pageable.isPaged() ? pageable.getSort() : Sort.unsorted();
		return getQuery(spec, getDomainClass(), projectionType, sort);
	}

	/**
	 * Creates a new {@link TypedQuery} from the given {@link Specification}.
	 *
	 * @param                <R> the generic type
	 * @param spec           can be {@literal null}.
	 * @param projectionType the projection type
	 * @param sort           must not be {@literal null}.
	 * @return the query
	 */
	protected <R> TypedQuery<R> getProjectionQuery(@Nullable Specification<T> spec, Class<R> projectionType,
			Sort sort) {
		return getQuery(spec, getDomainClass(), projectionType, sort);
	}

	/**
	 * Gets the query.
	 *
	 * @param                <R> the generic type
	 * @param                <S> the generic type
	 * @param spec           the spec
	 * @param projectionType the projection type
	 * @param domainClass    the domain class
	 * @param sort           the sort
	 * @return the query
	 */
	protected <R, S extends T> TypedQuery<R> getQuery(@Nullable Specification<S> spec, Class<S> domainClass,
			Class<R> projectionType, Sort sort) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<R> query = builder.createQuery(projectionType);

		// FROM e WHERE
		Root<S> root = applySpecificationToCriteria(spec, domainClass, query);

		List<Selection<Object>> selections = new ArrayList<Selection<Object>>();
		try {
			for (Field projectionField : projectionType.getDeclaredFields()) {
				PropertyDescriptor domainPropertyDescriptor = BeanUtils.getPropertyDescriptor(domainClass,
						projectionField.getName());
				if (domainPropertyDescriptor != null) {
					selections.add(root.get(domainPropertyDescriptor.getName()));
				}
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("Error reading properties of projection for select: " + e.getMessage(),
					e);
		}
		// SELECT
		if (!CollectionUtils.isEmpty(selections)) {
			query.multiselect(selections.toArray(new Selection[selections.size()]));
		} else {
			throw new IllegalArgumentException("No field for select");
		}

		if (sort.isSorted()) {
			query.orderBy(toOrders(sort, root, builder));
		}

		return applyRepositoryMethodMetadata(entityManager.createQuery(query));
	}

	/**
	 * Applies the given {@link Specification} to the given {@link CriteriaQuery}.
	 *
	 * @param             <S> the generic type
	 * @param             <U> the generic type
	 * @param spec        can be {@literal null}.
	 * @param domainClass must not be {@literal null}.
	 * @param query       must not be {@literal null}.
	 * @return the root
	 */
	private <S, U extends T> Root<U> applySpecificationToCriteria(@Nullable Specification<U> spec, Class<U> domainClass,
			CriteriaQuery<S> query) {

		Assert.notNull(domainClass, "Domain class must not be null!");
		Assert.notNull(query, "CriteriaQuery must not be null!");

		Root<U> root = query.from(domainClass);

		if (spec == null) {
			return root;
		}

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		Predicate predicate = spec.toPredicate(root, query, builder);

		if (predicate != null) {
			query.where(predicate);
		}

		return root;
	}

	/**
	 * Apply repository method metadata.
	 *
	 * @param       <S> the generic type
	 * @param query the query
	 * @return the typed query
	 */
	private <S> TypedQuery<S> applyRepositoryMethodMetadata(TypedQuery<S> query) {

		if (metadata == null) {
			return query;
		}

		LockModeType type = metadata.getLockModeType();
		TypedQuery<S> toReturn = type == null ? query : query.setLockMode(type);

//		applyQueryHints(toReturn);

		return toReturn;
	}

}
