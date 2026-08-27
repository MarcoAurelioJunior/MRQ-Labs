package mrqlab.bank.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

//Connection with DataBase
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
