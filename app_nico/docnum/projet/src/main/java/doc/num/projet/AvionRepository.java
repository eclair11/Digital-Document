package doc.num.projet;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

/**
 * AvionRepository
 */
public interface AvionRepository extends CrudRepository<Avion, Long>{

    Optional<Avion> findById(Long id);
    
}