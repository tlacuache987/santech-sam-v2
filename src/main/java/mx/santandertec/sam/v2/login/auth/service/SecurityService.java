package mx.santandertec.sam.v2.login.auth.service;

public interface SecurityService {

	boolean isAuthenticated();

	void autoLogin(String username, String password);

}