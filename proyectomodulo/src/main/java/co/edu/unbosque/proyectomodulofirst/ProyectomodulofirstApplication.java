package co.edu.unbosque.proyectomodulofirst;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProyectomodulofirstApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectomodulofirstApplication.class, args);
	}

	@Bean
	
	public ModelMapper getModelMapper() {
		return new ModelMapper ();
		
		
	}
}
