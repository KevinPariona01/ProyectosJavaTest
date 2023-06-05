package com.pe.proyecto006.project;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Proyecto006Application {
    
    
    //CUANDO USAMOS EL MODELMAPPER PRIMERO LO AÑADIMOS AQUI
    //PARA PODER INYECTARLO
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();//ME RETORNAR UN MODEL MAPPER
    }

	public static void main(String[] args) {
		SpringApplication.run(Proyecto006Application.class, args);
                System.out.println("CARGO EXITOSAMENTE");
                System.out.println("Contra: " + new BCryptPasswordEncoder().encode("gatomontes001#"));
	}

}
