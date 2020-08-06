package com.tcs.security.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;


@Configuration
@EnableWebSecurity
public class BigWConfiguration extends WebSecurityConfigurerAdapter {
	
	//Getting values from properties file
	@Value("${ldap.urls}")
	private String ldapUrls;
	
	@Value("${ldap.base.dn}")
	private String ldapBaseDn;
	
	@Value("${ldap.user.dn.pattern}")
	private String ldapUserDnPattern;
	
	@Value("${ldap.enabled}")
	private String ldapEnabled;
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("Inside configure.....");
	auth
		    .ldapAuthentication()//.userSearchFilter("(uid={0})")
		    .userDnPatterns(ldapUserDnPattern)
		    .groupSearchBase("ou=groups")
		    .contextSource()
		    .url(ldapUrls + ldapBaseDn)
		    .and()
		    .passwordCompare().passwordAttribute("userPassword")
		    .passwordEncoder(new LdapShaPasswordEncoder())
		    ;
		
	}
	
	
	//Authorization Basic
	@Override
	  protected void configure(HttpSecurity http) throws Exception {
	    /**http
	      .authorizeRequests()
	      .anyRequest().fullyAuthenticated()
	      .and()
	      .formLogin();**/
	    
	    http 
		   .csrf() 
		   .disable()
		   .authorizeRequests()
		   .antMatchers("/newUser/**","/bigw/add/**","/registerSuccess/**","/jbigw/add","**/validateUser/**").permitAll()
		   .anyRequest().fullyAuthenticated()
		   .and()
		   .formLogin().loginPage("/login").permitAll();
	  }
	 
	 
	/*
	 * @Override public void configure(WebSecurity web) throws Exception {
	 * web.ignoring().antMatchers("/newUser/**"); }
	 */

}
