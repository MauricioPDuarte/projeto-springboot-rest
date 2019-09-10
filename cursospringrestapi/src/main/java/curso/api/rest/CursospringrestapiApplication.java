package curso.api.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class CursospringrestapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CursospringrestapiApplication.class, args);
	}

}
