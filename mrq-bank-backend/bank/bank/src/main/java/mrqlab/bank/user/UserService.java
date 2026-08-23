package mrqlab.bank.User;

import java.lang.foreign.Linker.Option;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
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
