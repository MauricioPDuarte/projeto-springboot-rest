package curso.api.rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import curso.api.rest.service.ImplUserDetailsService;

@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private ImplUserDetailsService implUserDetailsService;
	
	
	@Override
	//Configura as solicitações de acesso por HTTP
	protected void configure(HttpSecurity http) throws Exception {
		//Ativando a proteção conta usuário que nao estão validados por token
		http
		.csrf()
		.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		
		//Ativando a permissão para acesso a página inicial do sistema 
		.disable()
		.authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/index").permitAll()
		
		//URL de logout - redireciona após o user deslogar do sistema
		.anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")
		
		//Mapeia a URL de logout e invalida o usuário
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
		
		//Filtra requisições de login para autenticação
		
		//Filtra demais requisições para verificar a presença do TOKEN JWT no HEADER HTTP 
		}
	
	@Override
	//Service que irá consultar o usuário no DB
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(implUserDetailsService)
		//Padrão de codificação de senha para o usuário
		.passwordEncoder(new BCryptPasswordEncoder());
	}

}
