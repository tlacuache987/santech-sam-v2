package mx.santandertec.sam.v2.login.auth.ldap.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.santandertec.sam.v2.login.auth.ldap.entities.Person;
import mx.santandertec.sam.v2.login.auth.ldap.repositories.PersonRepository;

@RestController
@RequestMapping("/ldap")
public class PersonLdapController {

	@Autowired
	private PersonRepository personRepository;

	@PostMapping("/add-user")
	public ResponseEntity<String> bindLdapPerson(@RequestBody Person person) {
		String result = personRepository.create(person);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PutMapping("/update-user")
	public ResponseEntity<String> rebindLdapPerson(@RequestBody Person person) {
		String result = personRepository.update(person);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/retrieve-users")
	public ResponseEntity<List<Person>> retrieve() {
		return new ResponseEntity<List<Person>>(personRepository.retrieve(), HttpStatus.OK);
	}

	@GetMapping("/remove-user")
	public ResponseEntity<String> unbindLdapPerson(@RequestParam(name = "userId") String userId) {
		String result = personRepository.remove(userId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/get-user-names")
	public ResponseEntity<List<String>> getLdapUserNames() {
		return new ResponseEntity<>(personRepository.getAllPersonNames(), HttpStatus.OK);
	}

	@GetMapping("/get-users")
	public ResponseEntity<List<Person>> getLdapUsers() {
		return new ResponseEntity<>(personRepository.getAllPersons(), HttpStatus.OK);
	}

	@GetMapping("/get-user")
	public ResponseEntity<Person> findLdapPerson(@RequestParam(name = "user-id") String userId) {
		return new ResponseEntity<>(personRepository.getPersonNamesByUid(userId), HttpStatus.OK);
	}
}
