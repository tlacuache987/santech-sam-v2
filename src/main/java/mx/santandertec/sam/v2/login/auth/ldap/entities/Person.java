package mx.santandertec.sam.v2.login.auth.ldap.entities;

import org.springframework.data.annotation.Transient;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class Person {

	private String userId;

	private String fullName;

	private String lastName;

	private String givenName;

	private String mail;

	private String password;

	private String description;

	private boolean passwordIsPassword;

	@JsonIgnore
	private LdapShaPasswordEncoder encoder = new LdapShaPasswordEncoder();

	public boolean getPasswordIsPassword() {

		return encoder.matches("password", this.password);
	}

}