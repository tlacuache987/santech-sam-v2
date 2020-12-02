package mx.santandertec.sam.v2.login.auth.entities;

import javax.persistence.*;

import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	private String password;

	@Transient
	private String passwordConfirm;

	@ManyToMany
	private Set<Role> roles;
}