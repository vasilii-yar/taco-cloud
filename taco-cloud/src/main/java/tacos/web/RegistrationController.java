package tacos.web;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tacos.User;
import tacos.data.UserRepository;
import tacos.security.RegistrationForm;

@Controller
@RequestMapping("/register")
public class RegistrationController {
	private UserRepository userRep;
	private PasswordEncoder pass;
	
	public RegistrationController(UserRepository userRep, PasswordEncoder pass) {
		this.userRep = userRep;
		this.pass = pass;
	}
	
	@GetMapping
	public String registrationForm() {
		return "registration";
	}
	
	@PostMapping
	public String processRegistration(RegistrationForm form) {
		userRep.save(form.toUser(pass));
		return "redirect:/login";
	}
}
