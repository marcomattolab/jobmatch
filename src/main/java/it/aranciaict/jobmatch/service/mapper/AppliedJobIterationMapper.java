package it.aranciaict.jobmatch.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.aranciaict.jobmatch.domain.AppliedJobIteration;
import it.aranciaict.jobmatch.service.dto.AppliedJobIterationDTO;

/**
 * Mapper for the entity AppliedJobIteration and its DTO AppliedJobIterationDTO.
 */
@Mapper(componentModel = "spring", uses = {AppliedJobMapper.class})
public interface AppliedJobIterationMapper extends EntityMapper<AppliedJobIterationDTO, AppliedJobIteration> {

	@Override
    @Mapping(source = "appliedJob.id", target = "appliedJobId")
    AppliedJobIterationDTO toDto(AppliedJobIteration appliedJobIteration);

	@Override
    @Mapping(source = "appliedJobId", target = "appliedJob")
    AppliedJobIteration toEntity(AppliedJobIterationDTO appliedJobIterationDTO);

    /**
     * From id.
     *
     * @param id the id
     * @return the applied job iteration
     */
    default AppliedJobIteration fromId(Long id) {
        if (id == null) {
            return null;
        }
        AppliedJobIteration appliedJobIteration = new AppliedJobIteration();
        appliedJobIteration.setId(id);
        return appliedJobIteration;
    }
}
