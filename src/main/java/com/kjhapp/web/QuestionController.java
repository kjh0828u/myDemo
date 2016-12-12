package com.kjhapp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kjhapp.domain.Question;
import com.kjhapp.domain.QuestionRepository;
import com.kjhapp.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {

	//원래 리파지토리 저 리파지토리 쓰면 널포인트가 발생한다. 아무것도없으니까..
	//그래서 Autowired 어노테이션을 이용해서 스프링에 구체적인 클래스를 만들어서 
	//활용하게 해달라고 요청하는 것이다.
	@Autowired
	private QuestionRepository questionRepository;
	
	@GetMapping("/form")
	public String form(HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}
		return "/qna/form";
	}

	@PostMapping("")
	public String write(String title, String contents, HttpSession session){
		if (!HttpSessionUtils.isLoginUser(session)){
			return "/users/loginForm";
		}
		
		//글쓴이 가져올 user를 구함
		User sessionUser = HttpSessionUtils.getUserFormSession(session);
		//이렇게 question 만들었고
		Question newQuestion = new Question(sessionUser, title, contents);
		//리파지토리 이용해서 데이터베이스에 저장할 수 있겠지.
		questionRepository.save(newQuestion);
		return "redirect:/";
	}

	@GetMapping("/{id}")
	public String show (@PathVariable Long id, Model model){
		Question question = questionRepository.findOne(id);
		model.addAttribute("question", question);
		return "/qna/show";
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model){
		//id에 해당하는 question(게시판 db)을 updateform에 전달하는거지
		Question question = questionRepository.findOne(id);
		model.addAttribute("question", question);
		return"/qna/updateForm";
	}
	

	
	
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, Question newQuestion, String title, String contents){
	
		Question question = questionRepository.findOne(id);
		question.update(newQuestion);
		questionRepository.save(question);
		return String.format("redirect:/questions/%d", id);
	}
	
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id){
		questionRepository.delete(id);
			
		return "redirect:/";
	}
	
	
	
	
	
	
}

