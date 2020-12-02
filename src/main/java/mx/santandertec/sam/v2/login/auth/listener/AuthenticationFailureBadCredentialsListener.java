package mx.santandertec.sam.v2.login.auth.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthenticationFailureBadCredentialsListener
		implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

	@Override
	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
		Object userName = event.getAuthentication().getPrincipal();
		Object credentials = event.getAuthentication().getCredentials();
		log.info("Failed login using USERNAME [" + userName + "]");
		log.info("Failed login using PASSWORD [" + credentials + "]");
	}
}