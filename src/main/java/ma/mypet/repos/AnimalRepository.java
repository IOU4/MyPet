package ma.mypet.repos;

import ma.mypet.domain.Animal;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
