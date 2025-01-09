package odine.freelancermarketplace.repository;

import odine.freelancermarketplace.model.DesignTool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DesignToolRepository extends JpaRepository<DesignTool, Long> {
    Optional<DesignTool> findByName(String name);
}
