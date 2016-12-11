package com.kjhapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

	@GetMapping("/helloworld")
	public String welcome(String name,int age, Model model) { 
		System.out.println(name + age);
		model.addAttribute("modelname", name); // 모델에 네임이라는 이름으로 파라미터 name을 저장한다. html로 저장이된다.
		model.addAttribute("age", age);
		
		return "welcome";
	}
}