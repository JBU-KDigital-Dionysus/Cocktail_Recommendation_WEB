package com.cocktail.cocktail_recommendation.controller;

import com.cocktail.cocktail_recommendation.dto.CocktailDto;
import com.cocktail.cocktail_recommendation.repository.CocktailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchCocktailController {
	@Autowired
	CocktailRepository cocktailRepository;

	@GetMapping("/main")
	public void SearchCocktail() {

	}
	
	@GetMapping("/cocktailsearch")
	public void Cocktailsearch() {

	}


}
