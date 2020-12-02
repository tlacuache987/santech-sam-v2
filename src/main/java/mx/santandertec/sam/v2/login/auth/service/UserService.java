package mx.santandertec.sam.v2.login.auth.service;

import mx.santandertec.sam.v2.login.auth.entities.User;

public interface UserService {

	void save(User user);

	User findByUsername(String username);

}