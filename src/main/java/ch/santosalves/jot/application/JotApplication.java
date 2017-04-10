package ch.santosalves.jot.application;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import ch.santosalves.jot.controllers.AdminController;
import ch.santosalves.jot.controllers.JotController;
import ch.santosalves.jot.entities.Level;
import ch.santosalves.jot.repositories.LevelsRepository;
import ch.santosalves.jot.services.LevelsService;
import ch.santosalves.jot.services.SendmailService;

@SpringBootApplication
@ComponentScan(basePackageClasses = { LevelsRepository.class, AdminController.class, JotController.class,
        LevelsService.class, SendmailService.class })
@EnableJpaRepositories(basePackageClasses = { Level.class, LevelsRepository.class })
@EnableWebSecurity
@PropertySource("classpath:application.properties")
public class JotApplication extends WebSecurityConfigurerAdapter {

    private @Value("${jot.admin.username}") String jotAdminUsername;
    private @Value("${jot.admin.password}") String jotAdminPassword;

    public static void main(String[] args) {
        SpringApplication.run(JotApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
		http
		    //#1
		    .formLogin()
		        //#2
		        .loginPage("/jot/login")
		        .failureUrl("/jot/login?error")
		        //#3
		        .and()
	        //#4
		    .authorizeRequests()
		        //#5
		        .antMatchers("/jot/**", "/css/**", "/js/**", "/images/**").permitAll()
		        .antMatchers("/admin/**").hasRole("ADMIN").anyRequest().authenticated()
		        .and()
			.logout()
			    .clearAuthentication(true)
			    .logoutUrl("/jot/logout")
			    .permitAll();
		// @formatter:on
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        Logger.getLogger(JotApplication.class)
                .info("In memory authentication with user " + jotAdminUsername + " and pass " + jotAdminPassword);
        auth.inMemoryAuthentication().withUser(jotAdminUsername).password(jotAdminPassword).roles("ADMIN");
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
