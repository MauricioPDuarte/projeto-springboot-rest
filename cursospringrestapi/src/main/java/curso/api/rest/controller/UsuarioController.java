package curso.api.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import curso.api.rest.model.Usuario;
import curso.api.rest.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("/{id}/relatoriopdf")
	public ResponseEntity<Usuario> relatorio(@PathVariable("id") Long id) {
		
		//Retorno seria um relatório...
		Usuario usuario = usuarioRepository.findById(id).get();
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> buscarPorId(@PathVariable("id") Long id) {
		Usuario usuario = usuarioRepository.findById(id).get();
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Usuario> deletarPorId(@PathVariable("id") Long id) {
		usuarioRepository.deleteById(id);
		return new ResponseEntity<Usuario>(HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<Iterable<Usuario>> listarTodos() {
		return new ResponseEntity<Iterable<Usuario>>(usuarioRepository.findAll(), HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario){
		
		usuario.getTelefones().forEach(t -> t.setUsuario(usuario));
		usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
		
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);
	}
	
	@PutMapping("/")
	public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario){
		
		usuario.getTelefones().forEach(t -> t.setUsuario(usuario));
		
		Usuario usuarioTemp = usuarioRepository.findUserByLogin(usuario.getLogin());
		
		if(!usuario.getSenha().equals(usuarioTemp.getSenha())) {
			usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
		}
		
		Usuario usuarioAtualizado = usuarioRepository.save(usuario);
		return new ResponseEntity<Usuario>(usuarioAtualizado, HttpStatus.OK);
	}
	
}
