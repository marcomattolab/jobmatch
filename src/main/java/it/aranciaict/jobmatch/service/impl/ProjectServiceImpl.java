package it.aranciaict.jobmatch.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.aranciaict.jobmatch.domain.Project;
import it.aranciaict.jobmatch.repository.ProjectRepository;
import it.aranciaict.jobmatch.service.ProjectService;
import it.aranciaict.jobmatch.service.dto.ProjectDTO;
import it.aranciaict.jobmatch.service.mapper.ProjectMapper;

/**
 * Service Implementation for managing Project.
 */
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(ProjectServiceImpl.class);

    /** The project repository. */
    private final ProjectRepository projectRepository;

    /** The project mapper. */
    private final ProjectMapper projectMapper;

    /**
     * Instantiates a new project service impl.
     *
     * @param projectRepository the project repository
     * @param projectMapper the project mapper
     */
    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    /**
     * Save a project.
     *
     * @param projectDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProjectDTO save(ProjectDTO projectDTO) {
        log.debug("Request to save Project : {}", projectDTO);
        Project project = projectMapper.toEntity(projectDTO);
//        if(!ProjectActionsUtils.isEditAvailable(project)) {
//        	throw new IllegalArgumentException("L'utente non ha i permessi per modificare questo Project");
//        }
        project = projectRepository.save(project);
        return projectMapper.toDto(project);
    }

    /**
     * Get all the projects.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProjectDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Projects");
        return projectRepository.findAll(pageable)
            .map(projectMapper::toDto);
    }


    /**
     * Get one project by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectDTO> findOne(Long id) {
        log.debug("Request to get Project : {}", id);
        return projectRepository.findById(id)
            .map(projectMapper::toDto);
    }

    /**
     * Delete the project by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Project : {}", id);
        Project project = projectRepository.findById(id).orElse(null);
//        if(!ProjectActionsUtils.isEditAvailable(project)) {
//        	throw new IllegalArgumentException("L'utente non ha i permessi per cancellare questo Project");
//        }
        projectRepository.deleteById(id);
    }
}
