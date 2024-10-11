package com.example.batch.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//	code to have jdbc authentication
//	@Autowired
//	private DataSource datasource;

	@Autowired
	private BCryptPasswordEncoder encoder;

	/* this method defines which url are need to be authenticated */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		code to have jdbc authentication	
//		http.authorizeRequests().antMatchers("/security/balance").authenticated()
//		.antMatchers("/security/myloan").hasAuthority("ADMIN")
//		.antMatchers(("/security/contact").hasAnyAuthority("ADMIN","EMPLOYEE")
//		.antMatchers("/security/contact").permitAll().and().formLogin().and().httpBasic();
//		

		http.authorizeRequests().antMatchers("/security/balance").authenticated().antMatchers("/security/statement")
				.authenticated().antMatchers("/security/myloan").authenticated().antMatchers("/security/home")
				.permitAll().antMatchers("/security/contact").permitAll().antMatchers("/security/welcome").permitAll()
				.and().formLogin().and().httpBasic();
	}

	/* this method tells which users have access for the api's */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/* this is the code for in memory authentication */
		auth.inMemoryAuthentication().withUser("admin").password("admin").authorities("admin").and().withUser("abhi")
				.password("abhi123").authorities("read").and().withUser("tarun").password("tarun123")
				.authorities("read").and().passwordEncoder(encoder);
//		this is the code  to have jdbc authentication
//		auth.jdbcAuthentication()
//		.dataSource(datasource)
//		.usersByUsernameQuery("select user_name,user_pwd,user_enabled from user_dtls where user_name=?")
//		.authoritiesByusernameQuery("select user_name,user_role from user_dtls where user_name= ?")
//		.passwordEncoder(encoder);

	}
}
