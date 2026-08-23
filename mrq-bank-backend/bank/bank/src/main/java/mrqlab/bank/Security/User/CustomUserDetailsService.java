package mrqlab.bank.Security.User;

import mrqlab.bank.Repository.UserRepository;
import mrqlab.bank.Security.User.*;

public class CustomUserDetailsService implements UserDetailsServicevice {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
