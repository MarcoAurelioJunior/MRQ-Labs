package mrqlab.bank.Controllers;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mrqlab.bank.Repository.UserRepository;
import mrqlab.bank.Services.userService;
import mrqlab.bank.models.User;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final userService userService;

    public AuthController(userService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public void signup(@RequestBody User user) {
        userService.saveUser(user);
    }

    @PostMapping("/signin")
    public boolean signin(@RequestBody User user) {
        return userService.signinUser(user.getEmail(), user.getPassword());
    }
}
    
