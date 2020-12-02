package mx.santandertec.sam.v2.login.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import mx.santandertec.sam.v2.login.auth.handlers.SamV2AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	//@Qualifier("userDetailsServiceImpl")
	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public LdapShaPasswordEncoder ldapShaPasswordEncoder() {
		return new LdapShaPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/login/css/**", "/login/js/**", "/registration", "/favicon.ico").permitAll()
				.antMatchers("/ldap/**").permitAll()
				.anyRequest().authenticated()
			.and()
			.formLogin().loginPage("/login").permitAll().successHandler(successHandler())
			.and()
			.logout().permitAll()
			.and()
			.csrf().disable();
	}

	@Bean
	public AuthenticationSuccessHandler successHandler() {
		return new SamV2AuthenticationSuccessHandler();
	}

	@Bean
	public AuthenticationManager customAuthenticationManager() throws Exception {
		return authenticationManager();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		//auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
		auth.userDetailsService(userDetailsService).passwordEncoder(ldapShaPasswordEncoder());
	}
}