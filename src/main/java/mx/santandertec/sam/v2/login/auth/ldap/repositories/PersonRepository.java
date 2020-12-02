package mx.santandertec.sam.v2.login.auth.ldap.repositories;

import java.util.List;

import mx.santandertec.sam.v2.login.auth.ldap.entities.Person;

public interface PersonRepository {

	public List<Person> retrieve();

	public String create(Person p);

	public String update(Person p);

	public String remove(String userId);

	public List<Person> getAllPersons();

	public List<String> getAllPersonNames();

	public Person getPersonNamesByUid(String userId);

}