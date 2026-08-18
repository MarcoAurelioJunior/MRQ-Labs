package mrqlab.bank.Controllers;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/api")
public class mainController {
    
    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }   
    
}
