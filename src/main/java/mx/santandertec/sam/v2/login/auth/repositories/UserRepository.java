package mx.santandertec.sam.v2.login.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.santandertec.sam.v2.login.auth.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

}