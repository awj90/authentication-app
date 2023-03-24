package ibf2022.batch2.ssf.frontcontroller.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ibf2022.batch2.ssf.frontcontroller.models.Captcha;
import ibf2022.batch2.ssf.frontcontroller.models.User;
import ibf2022.batch2.ssf.frontcontroller.services.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class FrontController {

	@Autowired
	AuthenticationService authenticationService;

	// TODO: Task 2, Task 3, Task 4, Task 6
	
	@GetMapping(path="/")
	public String renderLandingPage(Model model, HttpSession session) {
		
		Captcha captcha = new Captcha();
		captcha.initialize();
		session.setAttribute("captcha", captcha);
		model.addAttribute("captcha", captcha);
		
		model.addAttribute("user", new User());

		return "view0";
	}

	@PostMapping(path="/login")
	public String logInHandler(Model model, HttpSession session, @Valid User user, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return "view0";
		}

		if (authenticationService.isLocked(user.getUsername())) {
			model.addAttribute("username", user.getUsername());
			return "view2";
		}

		Captcha c = (Captcha) session.getAttribute("captcha");
		User u = (User) session.getAttribute(user.getUsername());

		if (u != null) {
			user.setRemainingAttempts(u.getRemainingAttempts());
		}

		boolean success = false; 

		if (user.getRemainingAttempts() == 3 || user.getCaptchaAnswer() == c.getAnswer()) {	
			try {
				success = authenticationService.authenticate(user.getUsername(), user.getPassword());
			} catch (Exception e) {
				success = false;
			}
		}

		if (success) {
			user.setAuthenticated(true);
			user.setRemainingAttempts(3);
			session.setAttribute(user.getUsername(), user);
			return "view1";
		}

		user.setAuthenticated(false);
		user.decrementRemainingAttempts();
		session.setAttribute(user.getUsername(), user);
		
		if (user.getRemainingAttempts() < 0) {
			authenticationService.disableUser(user.getUsername());
			user.setRemainingAttempts(3);
			session.setAttribute(user.getUsername(), user);
			model.addAttribute("username", user.getUsername());
			return "view2";
		}
		FieldError err = new FieldError("user", "password", "Invalid username/password, Remaining attempts for %s: %d".formatted(user.getUsername(), user.getRemainingAttempts()));
		bindingResult.addError(err);

		Captcha captcha = new Captcha();
		captcha.initialize();
		session.setAttribute("captcha", captcha);
		model.addAttribute("captcha", captcha);

		return "view0";
	}

	@GetMapping(path="/logout")
	public String logOut(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}
