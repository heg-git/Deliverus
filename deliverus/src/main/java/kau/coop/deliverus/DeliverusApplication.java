package kau.coop.deliverus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class DeliverusApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliverusApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping( "/**")
						.allowedOrigins("http://localhost:3000", "chrome-extension://aejoelaoggembcahagimdiliamlcdmfm") //talend api
						.allowedMethods("*")
						.maxAge(3000)
						.allowCredentials(true);
			}
		};
	}

}
