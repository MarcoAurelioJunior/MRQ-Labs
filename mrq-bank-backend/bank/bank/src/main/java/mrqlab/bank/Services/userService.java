package mrqlab.bank.Services;

import java.lang.foreign.Linker.Option;
import java.util.Optional;

import org.springframework.stereotype.Service;

import mrqlab.bank.Repository.UserRepository;
import mrqlab.bank.models.User;

@Service
public class userService {
    private final UserRepository userRepository;

    public userService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user) {
        try {
            userRepository.save(user);
        } catch (Exception e) {
            System.err.println("Error saving user: " + e.getMessage());
        }
    }

    public boolean signinUser(String email, String password) {

        Optional<User> foundUser = userRepository.findByEmail(email);

        if (foundUser.isEmpty()) {
            return false;
        }

        User user = foundUser.get();

        return passwordEncoder.matches(
                password,
                user.getPassword());
    }
}
