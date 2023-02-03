package ma.mypet.repos;

import ma.mypet.domain.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  boolean existsByEmailIgnoreCase(String email);

  boolean existsByPhoneIgnoreCase(String phone);

  Optional<User> findByName(String name);

  Optional<User> findByEmail(String email);

}
