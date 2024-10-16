package com.jobapplictiontrackingsystem.jats.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.jobapplictiontrackingsystem.jats.services.impl.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private CustomUserDetailService customUserDetailsService;

	@Bean
	PasswordEncoder getPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
		
		return httpSecurity
				.authorizeHttpRequests(request -> request
														.requestMatchers("/register-as-recruiter","/register-as-seeker","/register","/css/**", "image/**").permitAll()
														.requestMatchers("/recruiter/**").hasRole("RECRUITER")
														.requestMatchers("/jobseeker/**").hasRole("JOB_SEEKER")
														.anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/login").permitAll()
										.successHandler(customAuthenticationSuccessHandler()))
				.build();
	}

	@Bean
	DaoAuthenticationProvider authenticationProvider(){
		DaoAuthenticationProvider provider =  new DaoAuthenticationProvider();
		provider.setPasswordEncoder(new BCryptPasswordEncoder());
		provider.setUserDetailsService(customUserDetailsService);
		return provider;
	}

}
