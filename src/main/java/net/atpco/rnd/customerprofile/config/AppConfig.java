package net.atpco.rnd.customerprofile.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import net.atpco.rnd.customerprofile.service.CommandService;
import net.atpco.rnd.customerprofile.service.ProfileService;
import net.atpco.rnd.customerprofile.util.FileManager;
import net.atpco.rnd.customerprofile.util.HashManager;

@Configuration
public class AppConfig {
 
	@Value("${public.key}")
	private String publicKey;
	

	@Value("${private.phrase}")
	private String privatePhrase;

	@Value("${swarm.path}")
	private String swarmPath;
	
	@Bean
	public ProfileService getProfileService() {
		return new ProfileService(publicKey, privatePhrase, commandService(), fileManager(), hashManager());
	}

	@Bean 
	public CommandService commandService() {
		return new CommandService(swarmPath);
	}

	@Bean 
	public FileManager fileManager() {
		return new FileManager();
	}
	
	@Bean 
	public HashManager hashManager() {
		return new HashManager();
	}
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
            	 registry.addMapping("/**");
            }
        };
    }
}
