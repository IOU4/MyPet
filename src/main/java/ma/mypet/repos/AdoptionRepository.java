package ma.mypet.repos;

import ma.mypet.domain.Adoption;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdoptionRepository extends JpaRepository<Adoption, Long> {
}
