package com.kjhapp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kjhapp.domain.User;
import com.kjhapp.domain.UserRepository;

@Controller
@RequestMapping(value = "/users", produces = "application/mustache; charset=utf8")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@PostMapping("")
	public String create(User user) {
		System.out.println("user" + user);
		userRepository.save(user);
		return "redirect:/users";
	}

	@GetMapping("")
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}

	@GetMapping("/form")
	public String form() {
		return "/user/form";
	}

	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		Object tempUser = session.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
		if (tempUser == null){
			return "redirect:/users/loginForm";
		}
		
		User sessionedUser = (User)tempUser;
		if(!id.equals(sessionedUser.getId())){
			throw new IllegalStateException("you can update when you have your id");
		}
		
		model.addAttribute("user", userRepository.findOne(id));
		return "/user/updateForm";
	}

	@PutMapping("/{id}")
	public String update(@PathVariable Long id, User newUser, HttpSession session) {
		
		
		User user = userRepository.findOne(id);
		user.update(newUser);
		userRepository.save(user);
		return "redirect:/users";
	}

	@GetMapping("/loginForm")
	public String loginForm() {
		return "/user/loginForm";
	}

	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session) {
		User user = userRepository.findByUserId(userId);
		if (user == null) {
			System.out.println("Login Failure!");
			return "redirect:/users/loginForm";
		}
		if (!password.equals(user.getPassword())) {
			System.out.println("Login Failure!");
			return "redirect:/users/loginForm";
		}

		System.out.println("Login Success!");
		session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
		
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session){
		session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
		return "redirect:/";
	}
	
	
	
	
	
	
}
