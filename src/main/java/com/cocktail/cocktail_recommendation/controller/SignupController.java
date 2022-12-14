package com.cocktail.cocktail_recommendation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cocktail.cocktail_recommendation.dto.CustomerDto;
import com.cocktail.cocktail_recommendation.repository.SignupRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/signup")
public class SignupController {
	
	@Autowired
	SignupRepository signupRepository;

	//약관동의페이지
	@GetMapping("/agreed")
	public void AgreedPage() {}
	
	//약관동의페이지
	@GetMapping("/insert")
	public String InsertPage(
			CustomerDto customer, HttpSession session
			) {
		CustomerDto saveCustomer=null;
		try {
			saveCustomer=signupRepository.save(customer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(saveCustomer==null) {
			return "redirect:/login/login.do";			
		}else {
			return "redirect:/signup/insert.do";
		}
	}
}
