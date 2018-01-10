package com.matic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.matic.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping("/print")
	public String print(){
		return userService.getStr();
	}

}
