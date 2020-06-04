package de.computacenter.team.contact.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import de.computacenter.team.contact.model.entities.Address;
import de.computacenter.team.contact.model.entities.Contact;
import de.computacenter.team.contact.model.service.IContactService;


@Configuration
@EnableWebMvc
public class BeanConfig implements WebMvcConfigurer{

	@Override public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") .allowedOrigins("**") .allowedMethods("PUT",
				"DELETE", "POST", "GET"); 
		}
	
	@Bean
	public CommandLineRunner loadData(IContactService contactService) {
		
		return (arg) -> {
			Address address = new Address("Otto-Brenner-str.", "23", 57078, "Siegen");
			contactService.saveContact(new Contact("mendji", "mendjison", "m.mendjison@yahoo.de", "004917635351631", address));
			contactService.saveContact(new Contact("Mendji", "Sob", "m.sob@yahoo.de", "004917635351630", address));
			contactService.saveContact(new Contact("Fany", "Sob", "f.sob@yahoo.de", "004917635351632", address));
			contactService.saveContact(new Contact("Manuel", "Nueller", "m.nueller@outlook.de", "004917635351633"));
			contactService.saveContact(new Contact("Markus", "Sieber", "m-Sieber@outlook.de", "004917635351634"));
			contactService.saveContact(new Contact("Nina", "Hausmann", "n.hausmann@outlook.de", "004917635351635"));
			contactService.saveContact(new Contact("Mandy", "Hausmann", "m.hausmann@outlook.de", "004917635351636"));
		};
	}
}
