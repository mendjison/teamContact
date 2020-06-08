package de.computacenter.team.contact.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.computacenter.team.contact.model.entities.Address;
import de.computacenter.team.contact.model.entities.Contact;
import de.computacenter.team.contact.model.service.IContactService;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class BeanConfig {

	@Bean
	public Docket swaggerConfiguration() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.ant("/api/*"))
				.apis(RequestHandlerSelectors.basePackage("de.computacenter.team.contact"))
				.build()
				.apiInfo(apiEndPointsInfo());
	}
	
	private ApiInfo apiEndPointsInfo() {
		return new ApiInfoBuilder().title("Team's Contacts REST API")
				.description("Teamcontacts  REST API")
				.contact(new springfox.documentation.service.Contact("mendji",  "www.javaguides.net", "k.mendji@yahoo.de"))
				.license("Apache 2.0")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
				.version("1.0.0")
				.build();
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
