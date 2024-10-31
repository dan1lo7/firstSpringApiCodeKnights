package med.voll.api.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @CrossOrigin(origins = "*")  // Libera todos os origins
    @GetMapping
    public String olaMundo() {
        return "Peixes " +
                ", fala comigo nojento";
    }
}
