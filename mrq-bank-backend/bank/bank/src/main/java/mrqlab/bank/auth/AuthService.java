package mrqlab.bank.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authManager;

    public AuthService(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    public boolean signin(String email, String password) {

        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));
            return true;
        } catch (AuthenticationException e) {
            System.out.println("Erro ao gerar token de usuário: " + e);
            return false;
        }

    }
}
