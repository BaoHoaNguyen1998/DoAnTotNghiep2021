package com.iuh.ABCStore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.iuh.ABCStore.services.Imls.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	};

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService()).passwordEncoder(bCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.authorizeRequests().antMatchers("/quan-ly/**")
		.access("hasAnyRole('admin')");
		
		httpSecurity.authorizeRequests().antMatchers("/ban-hang/**")
		.access("hasAnyRole('seller')");
		
		httpSecurity.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

		httpSecurity.authorizeRequests()
				.antMatchers("/", "/dang-nhap", "dang-ky", "/xac-nhan-email", "/quen-mat-khau", "/lay-lai-mat-khau","/tai-khoan")
				.permitAll()
				.and()
				.authorizeRequests()
				.antMatchers("/quan-ly/**","/gio-hang","/dat-hang","/san-pham-danh-gia/{id}","/ban-hang/**").authenticated() // trang nao can dang
				.and().formLogin().loginPage("/dang-nhap").permitAll().and().logout().logoutUrl("/dang-xuat")
				.permitAll() 
		;

	}

	public void configure(WebSecurity webSecurity) throws Exception {
		webSecurity.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/img/** ",
				"/fonts/**", "/bootstrap/***");

	}

	public AuthenticationManager customAuthenticationManager() throws Exception {
		return authenticationManager();
	}

}
