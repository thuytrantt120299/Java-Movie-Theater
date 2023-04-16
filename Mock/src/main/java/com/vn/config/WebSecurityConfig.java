package com.vn.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.vn.service.auth.CustomAccountDetailservice;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private DataSource dataSource;
	

	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomAccountDetailservice();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().antMatchers("/accounts/**").authenticated().anyRequest().permitAll().and().formLogin()
//				.usernameParameter("username").loginPage("/login").defaultSuccessUrl("/home").permitAll()
//				
	            
		
	    		http.authorizeRequests()
	    		.antMatchers(
//	    				"/js/**",
//			            "/css/**",
			            "/img/**",
	    				"/",
	    				"/login",
	    				"/registration",
	    				"/logout"
		                ).permitAll();

	    		http.authorizeRequests().antMatchers("/employeeMNG/**").hasAuthority("Admin");

	    		http.authorizeRequests().antMatchers("/promotionMNG/**").hasAuthority("Admin");


	    		http.authorizeRequests().antMatchers("/roomMNG/**").hasAuthority("Admin");

	    		http.authorizeRequests().antMatchers("/movieMNG/**").hasAuthority("Admin");

	    		http.authorizeRequests().antMatchers("/home").hasAnyAuthority("Member", "Admin", "Employee")
	    		.and()
	    		.formLogin()
	    			.loginPage("/login")
	    			.usernameParameter("username")
	    			.defaultSuccessUrl("/home")
	    			
				.and()
				.rememberMe().tokenRepository(persistentTokenRepository())
				.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/")
				.permitAll();
	}
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepo = new JdbcTokenRepositoryImpl();
		tokenRepo.setDataSource(dataSource);
		return tokenRepo;
	}
}
