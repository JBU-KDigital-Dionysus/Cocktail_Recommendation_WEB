package com.cocktail.cocktail_recommendation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cocktail.cocktail_recommendation.dto.CocktailFlavorDto;
import com.cocktail.cocktail_recommendation.repository.CocktailFlavorRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/reco")
public class CocktailRecoController {
    @Autowired
    private CocktailFlavorRepository cocktailFlavorRepository;

    @GetMapping("/cocktailreco")
    public void recommandtion() {
    }

    @GetMapping("/cocktailreco1")
    public void recommandtion1() {
    }

    @PostMapping("/cocktailreco1")
    public String recommandtion1(CocktailFlavorDto cocktailFlavorDto, HttpSession session) {
        try {
            session.setAttribute("cocktailFlavorDto", cocktailFlavorDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/reco/cocktailreco2";
    }


    @GetMapping("/cocktailreco2")
    public void recommandtion2() {
    }

    @PostMapping("/cocktailreco2")
    public String recommandtion2(CocktailFlavorDto cocktailFlavorDto, HttpSession session) {
        try {
            session.setAttribute("cocktailFlavorDto", cocktailFlavorDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/reco/cocktailreco3";
    }

    @GetMapping("/cocktailreco3")
    public void recommandtion3() {
    }

    @PostMapping("/cocktailreco3")
    public String recommandtion3(CocktailFlavorDto cocktailFlavorDto, HttpSession session) {
        try {
            session.setAttribute("cocktailFlavorDto", cocktailFlavorDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/reco/results";
    }

    @GetMapping("/results")
    public String results() {
        return "/reco/results";
    }

    @PostMapping("/results")
    public String resultsP() {
        return "/reco/results";
    }

    @GetMapping("/cocktailreco3425")
    public void recommandtion2(HttpSession session) {
        CocktailFlavorDto reservation = (CocktailFlavorDto) session.getAttribute("cocktailFlavorDto");
        cocktailFlavorRepository.save(reservation);
    }
}
