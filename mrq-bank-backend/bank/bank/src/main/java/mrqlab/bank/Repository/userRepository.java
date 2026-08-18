package mrqlab.bank.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mrqlab.bank.models.User;

//Connection with DataBase
public interface userRepository extends JpaRepository<User, Long> {
    
    // List<User> search(String term);

    // Optional<User> findByUserAndPassword(String email, String password);

    // Optional<User> findByEmail(String email);
}
