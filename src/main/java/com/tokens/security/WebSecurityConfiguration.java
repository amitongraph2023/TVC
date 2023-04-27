package com.tokens.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JwtFilter jwtFilter;

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable().authorizeRequests().antMatchers("/authenticate").permitAll().anyRequest().authenticated()
//				.and().exceptionHandling().and().sessionManagement()
//				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//
//	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.csrf().disable().authorizeRequests()
		            .antMatchers("/user/registerUser").permitAll()
		            .antMatchers("/user/authenticate").permitAll()
		            .anyRequest().authenticated()
                    .and()
                    .exceptionHandling()
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//		httpSecurity.cors();
//		httpSecurity.csrf().disable(). authorizeRequests()
//		        // .antMatchers("/", "/home").permitAll()
//				// .antMatchers("/cart", "/category/{categoryId}",
//				// "/product/{productId}").permitAll()
//				.antMatchers("/user/registerAdminUser").permitAll()
//				.antMatchers("/user/registerUser").permitAll()
//
//				.antMatchers("/updateMasterKey", "/admin/**").hasRole("Admin")
//				.antMatchers("/addMasterKey").hasRole("User")
//				.antMatchers(HttpHeaders.ALLOW).permitAll().anyRequest()
//				.authenticated()
//				.and()
//				.formLogin()
//				.loginPage("/signin").permitAll()
//				.failureUrl("/signin?error=true")
//				.defaultSuccessUrl("/home")
//				.and()
//				.logout()
//				.logoutUrl("/logout")
//				.logoutSuccessUrl("/home")
//    			.invalidateHttpSession(true)
//				.deleteCookies("JSESSIONID")
//				.deleteCookies("id-token")
//				.deleteCookies("refresh-token")
//				.and()
//				.sessionManagement()
//				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//				.and()
//				.exceptionHandling()
//				.authenticationEntryPoint(jwtAuthenticationEntryPoint);

		httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
