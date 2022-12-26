package com.cocktail.cocktail_recommendation.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cocktail.cocktail_recommendation.dto.CocktailDto;
import com.cocktail.cocktail_recommendation.dto.Customer;
import com.cocktail.cocktail_recommendation.repository.CustomerRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/like")
public class CocktailLike {
	@Autowired
    private CustomerRepository customerRepository;
	
	 @GetMapping("/like.do")
		public void like() {}
	 
	
	 @GetMapping("/cocktailLike.do")
	    public String cocktailLike( HttpSession session, Model model
	    		){
		 String cstId = (String) session.getAttribute("cstId");
		 Optional<Customer> customer = customerRepository.findById(cstId);
		 if (customer.isPresent()) {
	            model.addAttribute("cocktail", customer.get());
	            return "like/cocktailLike";
	        } else {
	            return "/";
	        }
	 }
	    
	    
}
