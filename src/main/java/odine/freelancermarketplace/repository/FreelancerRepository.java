package odine.freelancermarketplace.repository;

import odine.freelancermarketplace.dto.projection.FreelancerDetailsProj;
import odine.freelancermarketplace.model.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface FreelancerRepository extends JpaRepository<Freelancer, Long> {
    Optional<Freelancer> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Freelancer f SET f.evaluationScore = :score WHERE f.email = :email")
    int updateEvaluationScore(@Param("email") String email, @Param("score") int score);

    @Query("""
            SELECT f
            FROM Freelancer f
            LEFT JOIN FETCH SoftwareDeveloper sd ON f.id = sd.id
            LEFT JOIN sd.specialties spec
            LEFT JOIN FETCH Designer d ON f.id = d.id
            LEFT JOIN d.designTools tools
            WHERE (:name IS NULL OR f.name ILIKE %:name%)
            AND (:city IS NULL OR f.city ILIKE %:city%)
            AND (:type IS NULL OR f.freelancerType = :type)
            AND (:specialties IS NULL OR spec.name IN :specialties)
            AND (:designTools IS NULL OR tools.name IN :designTools)
            """)
    List<Freelancer> search(
            @Param("name") String name,
            @Param("city") String city,
            @Param("type") String type,
            @Param("specialties") List<String> specialties,
            @Param("designTools") List<String> designTools
    );

    // Need plugin for eager fetch on ManyToMany associations
    @Query("""
            SELECT f
            FROM Freelancer f
            LEFT JOIN FETCH SoftwareDeveloper sd ON f.id = sd.id
            LEFT JOIN sd.specialties spec
            LEFT JOIN FETCH Designer d ON f.id = d.id
            LEFT JOIN d.designTools tools
            LEFT JOIN FETCH f.jobs
            """)
    List<FreelancerDetailsProj> findAllFreelancers();
}
