package mx.santandertec.sam.v2.login.auth.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SamV2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		HttpSession session = request.getSession();
		User user = (User) authentication.getPrincipal();
		
		log.info("perro user {} logged successfuly", authentication.getPrincipal());
		
		session.setAttribute("ivGroup", queryIvGroup(user.getUsername()));
		session.setAttribute("ivUser", queryIvUser(user.getUsername()));
		 	
		
		response.sendRedirect(request.getContextPath());
	}

	private String queryIvUser(String username) {
		return username+"_ivUser";
	}

	private String queryIvGroup(String username) {
		return username+"_ivGroup";
	}

}
