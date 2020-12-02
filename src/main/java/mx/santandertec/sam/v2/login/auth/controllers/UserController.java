package mx.santandertec.sam.v2.login.auth.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import mx.santandertec.sam.v2.login.auth.entities.User;
import mx.santandertec.sam.v2.login.auth.service.SecurityService;
import mx.santandertec.sam.v2.login.auth.service.UserService;
import mx.santandertec.sam.v2.login.auth.validators.UserValidator;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserValidator userValidator;

	private String server = "";// "http://localhost:8080";

	@GetMapping("/registration")
	public String registration(Model model) {

		if (securityService.isAuthenticated()) {
			return "redirect:" + server + "/";
		}

		model.addAttribute("userForm", new User());

		return "registration";
	}

	@PostMapping("/registration")
	public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
		userValidator.validate(userForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return "registration";
		}

		userService.save(userForm);

		securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

		return "redirect:" + server + "/welcome";
	}

	@GetMapping("/login")
	public String login(Model model, String error, String logout, HttpServletRequest request) {
		if (securityService.isAuthenticated()) {
			return "redirect:" + server + "/";
		}

		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");

		if (logout != null)
			model.addAttribute("message", "You have been logged out successfully.");

		String server = request.getScheme() + "://" + request.getServerName() + ':' + request.getServerPort();

		model.addAttribute("server", server);

		return "login";
	}

	/*@GetMapping({ "/", "/welcome" })
	public String welcome(Model model) {
		//return "welcome"; // Do not go to "welcome" it go to proxied
	}*/
}