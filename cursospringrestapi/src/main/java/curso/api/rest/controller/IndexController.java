package curso.api.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class IndexController {

	@GetMapping("/")
	public ResponseEntity init() {
		return new ResponseEntity("Olá usuário REST Spring Boot", HttpStatus.OK);
	}
	
}
