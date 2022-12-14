package com.cocktail.cocktail_recommendation.controller;


import com.cocktail.cocktail_recommendation.Repository.NewCocktailRepository;

import com.cocktail.cocktail_recommendation.dto.NewCocktailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cocktail")
public class CocktailController {
    /*@Autowired
    CocktailRepository cocktailRepository;

    @Autowired
    IngredientRepository ingredientRepository;*/

    @Autowired
    NewCocktailRepository newCocktailRepository;

    @GetMapping("/detail.do")
    public String detail(@RequestParam(required = true) int ctNo, Model model) {
        Optional<NewCocktailDto> newCocktailOpt = newCocktailRepository.findById(ctNo);
        if (newCocktailOpt.isPresent()) {
            model.addAttribute("cocktail", newCocktailOpt.get());
            return "cocktail/detail";
        } else {
            return "cocktail/list.do";
        }
    }

    @GetMapping("/list.do")
    public String list(Model model) {
        List<NewCocktailDto> cocktailList = newCocktailRepository.findAll();
        model.addAttribute("cocktailList", cocktailList);
        return "/cocktail/list";
    }
}
