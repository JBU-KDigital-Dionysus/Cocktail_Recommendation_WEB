package com.cocktail.cocktail_recommendation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/search")
public class SearchCocktailController {

	@GetMapping("/cocktailsearch")
	public void SearchCocktail() {
		
	}
}
