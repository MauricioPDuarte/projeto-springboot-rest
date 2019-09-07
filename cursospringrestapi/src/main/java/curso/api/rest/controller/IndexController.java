package curso.api.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class IndexController {

	@GetMapping("/")
	public ResponseEntity init(@RequestParam(value="nome", defaultValue = "Usuário") String nome) {
		System.out.println("Parametro recebido: " + nome);
		return new ResponseEntity("Olá " + nome + ". Primeira aula de REST Spring Boot", HttpStatus.OK);
	}
	
}
