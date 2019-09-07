package curso.api.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import curso.api.rest.model.Usuario;

@RestController
@RequestMapping("/usuario")
public class IndexController {

	@GetMapping("/")
	public ResponseEntity<Usuario> init() {
		
		List<Usuario> usuarios = new ArrayList<>();
		
		Usuario usuario = new Usuario();
		usuario.setId(1L);
		usuario.setLogin("prussdev@gmail.com");
		usuario.setSenha("Mauricio@123");
		usuario.setNome("Mauricio");
		usuarios.add(usuario);
		
		Usuario usuario2 = new Usuario();
		usuario2.setId(2L);
		usuario2.setLogin("julia.pruss@gmail.com");
		usuario2.setSenha("Julia@123");
		usuario2.setNome("Julia");
		usuarios.add(usuario2);
		
		return new ResponseEntity(usuarios, HttpStatus.OK);
	}
	
}
