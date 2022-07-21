package com.cogent.banking.api.security;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.cogent.banking.api.service.CustomerService;

@Configuration
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

	

    private final UserDetailsService userDetailsService;
    
   




	public MySecurityConfig(UserDetailsService userDetailsService) {
		super();
		this.userDetailsService = userDetailsService;
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		  CorsConfiguration corsConfiguration = new CorsConfiguration();
	        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
	        corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200"));
	        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT","OPTIONS","PATCH", "DELETE"));
	        corsConfiguration.setAllowCredentials(true);
	        corsConfiguration.setExposedHeaders(List.of("Authorization"));
		
		

	
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/api/login/**", "/api/token/refresh/**").permitAll();
        http.authorizeRequests().antMatchers("/api/customer/**").permitAll();
        http.authorizeRequests().antMatchers("/api/staff/**").permitAll();
        http.authorizeRequests().antMatchers("/api/admin/staff/**").permitAll();
        http.authorizeRequests().antMatchers("/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated().and()
        .cors().configurationSource(request-> corsConfiguration);


	}
	

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }
	

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
