package mrqlab.bank.security;

import mrqlab.bank.security.*;
import mrqlab.bank.user.UserRepository;

public class CustomUserDetailsService implements UserDetailsServicevice {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
