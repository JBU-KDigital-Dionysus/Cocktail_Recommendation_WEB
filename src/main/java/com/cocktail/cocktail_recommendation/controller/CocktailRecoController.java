package com.cocktail.cocktail_recommendation.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.cocktail.cocktail_recommendation.dto.CocktailFlavorDto;
import com.cocktail.cocktail_recommendation.repository.CocktailFlavorRepository;

import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.http.HttpRequest;
import java.util.Scanner;

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
    public String recommandtion1(@RequestParam("ctFlavor") String ctKind, HttpSession session) {
        try {
            session.setAttribute("ctKind", ctKind);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/reco/cocktailreco2";
    }


    @GetMapping("/cocktailreco2")
    public void recommandtion2() {
    }

    @PostMapping("/cocktailreco2")
    public String recommandtion2(@RequestParam("ctFlavor") String ctFlavor, HttpSession session) {
        try {
            session.setAttribute("ctFlavor", ctFlavor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/reco/cocktailreco3";
    }

    @GetMapping("/cocktailreco3")
    public void recommandtion3() {
    }
    @GetMapping("/cocktailreco4")
    public void recommandtio4() {
    }

    @PostMapping("/cocktailreco3")
    public String recommandtion3(@RequestParam("SEASON") String ctSeason, @RequestParam("TIME") String ctTime, @RequestParam("CERTIFIED") String ctCertified, @RequestParam("ctAlcohol") String ctAlcohol, HttpSession session) {
        try {
            session.setAttribute("ctSeason", ctSeason);
            session.setAttribute("ctTime", ctTime);
            session.setAttribute("ctCertified", ctCertified);
            session.setAttribute("ctAlcohol", ctAlcohol);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/CONT/upload";
    }


    @GetMapping("/results")
    public String results() {
        return "/reco/results";
    }

    @PostMapping("/results")
    public String resultsP(HttpSession session) {
        System.out.println(session.getAttribute("ctSeason"));
        return "/reco/results";
    }

    @GetMapping("/cocktailreco3425")
    public void recommandtion2(HttpSession session) {
        CocktailFlavorDto reservation = (CocktailFlavorDto) session.getAttribute("cocktailFlavorDto");
        cocktailFlavorRepository.save(reservation);
    }

}
