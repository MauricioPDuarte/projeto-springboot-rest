package curso.api.rest.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import curso.api.rest.ApplicationContextLoad;
import curso.api.rest.model.Usuario;
import curso.api.rest.repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtTokenAutenticacaoService {

	// Tempo de validade do Token - 2 dias
	private static final long EXPIRATION_TIME = 172800000;

	// Uma senha única para compor a autenticação e ajudar na segurança
	private static final String SECRET = "senhaForteSecreta";

	// Prefixo padrão de Token
	private static final String TOKEN_PREFIX = "Bearer";

	private static final String HEADER_STRING = "Authorization";

	// Gerando Token de autenticação e adicionando ao cabeçalho a resposta Http
	public void addAuthentication(HttpServletResponse response, String username) throws Exception {
		// Montage do Token
		String JWT = Jwts.builder() // Chama o gerador de Token
				.setSubject(username) // Adiciona o usuário
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Tempo de expiração
				.signWith(SignatureAlgorithm.HS512, SECRET) // Compactação e algoritmo de geração de senha
				.compact();

		// Junta o token com o prefixo
		String token = TOKEN_PREFIX + " " + JWT; // Ex: Bearer 2342fd23e2d23d23d23d23

		// Adiciona no cabeçalho Http
		response.addHeader(HEADER_STRING, token); // Ex: Authorization: Bearer 2342fd23e2d23d23d23d23

		// Escreve token como resposta no corpo do Http
		response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
	}

	// Retorna o usuário validado com token ou caso não seja válido retorna null
	public Authentication getAuthentication(HttpServletRequest request) {
		// Pega o token enviado no cabeçalho http
		String token = request.getHeader(HEADER_STRING);

		if (token != null) {
			// Faz a validação do token do usuário na requisição
			String user = Jwts.parser()
					.setSigningKey(SECRET) // Bearer 2342fd23e2d23d23d23d23
					.parseClaimsJws(token.replace(TOKEN_PREFIX, "")) // 342fd23e2d23d23d23d23
					.getBody() // Usuario Ex: Mauricio
					.getSubject();

			if (user != null) {
				Usuario usuario = ApplicationContextLoad.getApplicationContext().getBean(UsuarioRepository.class)
						.findUserByLogin(user);
				// Retorna o usuário logado
				return new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getSenha(),
						usuario.getAuthorities());
			}
			
		}
		return null;

	}
}
