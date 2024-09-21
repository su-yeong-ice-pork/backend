package develop.grassserver;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/api")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok().body("Hello, World!!");
    }
}
