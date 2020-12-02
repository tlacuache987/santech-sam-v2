package mx.santandertec.sam.v2.login.auth.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.santandertec.sam.v2.login.auth.ldap.entities.Person;
import mx.santandertec.sam.v2.login.auth.ldap.repositories.PersonRepository;

@Service
@Primary
public class UserDetailsLdapServiceImpl implements UserDetailsService {

	@Autowired
	private PersonRepository personRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) {

		Person person = personRepository.getPersonNamesByUid(username);
		if (person == null)
			throw new UsernameNotFoundException(username);

		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		/*
		 * for (Role role : person.getRoles()) { grantedAuthorities.add(new
		 * SimpleGrantedAuthority(role.getName())); }
		 */

		return new org.springframework.security.core.userdetails.User(person.getUserId(), person.getPassword(),
				grantedAuthorities);
	}
}