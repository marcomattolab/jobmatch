package it.aranciaict.jobmatch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.aranciaict.jobmatch.domain.Skill;

/**
 * Spring Data repository for the Skill entity.
 */
@Repository
public interface SkillRepository extends JpaRepository<Skill, Long>, JpaSpecificationExecutor<Skill> {

	/**
	 * Find all by candidate id.
	 *
	 * @param candidateId the id candidate
	 * @return the list
	 */
	List<Skill> findAllByCandidateId(Long candidateId);
	
	/**
	 * Find all by candidate id.
	 *
	 * @param candidateId the id candidate
	 * @return the list
	 */
	@Query(value="SELECT st.id FROM skill s inner join skill_tag st on s.skill_tag_id=st.id WHERE s.candidate_id=?1 and st.skill_type='OTHER'", nativeQuery = true)
	List<Long> findAllTagIdByCandidateId(Long candidateId);

//	/**
//	 * Find all skill name by candidate id.
//	 *
//	 * @param candidateId the candidate id
//	 * @return the list
//	 */
//	@Query(value = "SELECT st.name FROM skill s inner join skill_tag st on s.skill_tag_id=st.id WHERE s.candidate_id=1", nativeQuery = true)
//	List<String> findAllSkillNamesByCandidateId(Long candidateId);

}
