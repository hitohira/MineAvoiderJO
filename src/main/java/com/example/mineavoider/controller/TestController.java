package com.example.mineavoider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.mineavoider.authentication.DbUserDetails;
import com.example.mineavoider.dao.UserDAO;

@Controller
public class TestController {

	@Autowired
	UserDAO userDAO;
	  
	  @GetMapping("/hello")
	  public String helloOpen(Model model) {
	    String str = "Hello World";
	    
	    model.addAttribute("value", str);
	    return "hello";
	  }

	  @GetMapping("/hello2")
	  public String helloOpen(Model model,@AuthenticationPrincipal DbUserDetails dbUserDetails) {
	    String str = "Hello World " + dbUserDetails.getUser().getUserNo();
	    
	    model.addAttribute("value", str);
	    return "hello";
	  }
	  
}
