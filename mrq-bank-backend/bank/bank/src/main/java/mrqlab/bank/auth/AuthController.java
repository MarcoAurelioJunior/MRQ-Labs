package mrqlab.bank.auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mrqlab.bank.user.User;
import mrqlab.bank.user.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/signup")
    public void signup(@RequestBody User user) {
        userService.saveUser(user);
        
    }

    @PostMapping("/signin")
    public boolean signin(@RequestBody User user) {
        return authService.signin(user.getEmail(), user.getPassword());
    }
}
    
