package com.example.mineavoider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.mineavoider.form.LoginForm;
import com.example.mineavoider.service.RegisterUserService;



@Controller
public class LoginController {
	@Autowired
	RegisterUserService registerUserService;
	
	@GetMapping("/login")
	public String loginGet(@Validated LoginForm loginForm, BindingResult bindingResult, Model model) {
		model.addAttribute("loginForm",loginForm);
	return "login";
	}
	@PostMapping("/login")
	public String loginPost(@Validated LoginForm loginForm, BindingResult bindingResult, Model model) {
		return loginGet(loginForm,bindingResult,model);
	}
	  
	@GetMapping("/register")
	public String registerGet(@Validated LoginForm loginForm, BindingResult bindingResult, Model model) {
		return "register_user";
	}
	
	@PostMapping("/register")
	public String registerPost(@Validated LoginForm loginForm, BindingResult bindingResult, Model model) {
		model.addAttribute("loginForm",loginForm);
	return "register_user";
	}  
	
	@PostMapping("/register_confirm")
	public String registerConfirm(@Validated LoginForm loginForm, BindingResult bindingResult, Model model) {
		model.addAttribute("loginForm",loginForm);
	return "register_user_confirm";
	}
	
	@PostMapping("/register_result")
	public String registerResult(@Validated LoginForm loginForm, BindingResult bindingResult, Model model) {
		try {
			registerUserService.registerUser(loginForm);
			model.addAttribute("loginForm",loginForm);
		}
		catch(Exception e) {
			 model.addAttribute("errorMessage",e.getMessage());
		}
	
	 return "register_user_result";
	 }
	  
	 @GetMapping("/")
	 public String homeGet() {
		 return "home";
	 }
	 @PostMapping("/")
	 public String homePost() {
		 return "home";
	 }
}
