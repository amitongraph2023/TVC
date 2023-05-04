package com.tokens.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.csrf().disable().authorizeRequests()
		            .antMatchers("/user/registerUser", "/user/authenticate").permitAll()
		            .antMatchers("/**/*.*", "/signin").permitAll()
		            .antMatchers("/addMasterKey", "/user/getMasterKeyLogs/**","/transaction/generateToken", 
		            		"/transaction/getLogsTransactionToken").hasAnyRole("Admin", "User")
		            .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/signin")
                    .permitAll()
                    .failureUrl("/signin?error=true")
                    .defaultSuccessUrl("/home")
                    .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/home")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .deleteCookies("id-token")
                    .deleteCookies("refresh-token")
    				.and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);

		httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
