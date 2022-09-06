package com.chat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
@EnableWebSecurity
@CrossOrigin
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsService userDetailsService;
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	/*
	 * password encoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	

	/**
	 * Authorization
	 * 
	 * @return
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		http.authorizeRequests()

				
				.antMatchers("/authenticate").permitAll()
				.antMatchers("/test/usertest/user").permitAll()
				
				//.antMatchers("/user/**").permitAll()
				//.antMatchers("/app/**").permitAll()
				.antMatchers("/ws/**").permitAll()
				.antMatchers("/staffs/ws/**").permitAll()
				.antMatchers("/reception/**").permitAll()
				//.antMatchers("/**").permitAll()
				/*
				 * .antMatchers("/testport").hasRole("ADMIN")
				 * .antMatchers("/user").hasAnyRole("USER", "ADMIN")
				 * .antMatchers("/admin").hasRole("ADMIN") .antMatchers("/**").hasRole("ADMIN")
				 */
				//.antMatchers("/testport").hasRole("ADMIN")
				.antMatchers("/admin").permitAll()
				.anyRequest().authenticated();
				
		
		http.csrf().disable();
		http.cors();
		http.formLogin();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(userDetailsService);
	}

	@Override
	@Bean
	@Primary
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub

		return super.authenticationManager();
	}



	@Override
	
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		super.configure(web);
	}

	
}
