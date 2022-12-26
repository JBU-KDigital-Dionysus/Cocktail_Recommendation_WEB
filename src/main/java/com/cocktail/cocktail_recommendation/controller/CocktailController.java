package com.cocktail.cocktail_recommendation.controller;


import com.cocktail.cocktail_recommendation.repository.CocktailRepository;

import com.cocktail.cocktail_recommendation.dto.CocktailDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@Controller
@RequestMapping("/cocktail")
public class CocktailController {

    @Autowired
    CocktailRepository newCocktailRepository;

    @GetMapping("/detail.do")
    public String detail(@RequestParam(required = true) int ctNo,
                         Model model
                         ) {
        Optional<CocktailDto> newCocktailOpt = newCocktailRepository.findById(ctNo);
        if (newCocktailOpt.isPresent()) {
            model.addAttribute("cocktail", newCocktailOpt.get());
            return "cocktail/detail";
        } else {
            return "cocktail/list.do";
        }
    }

    @GetMapping("/list.do")
    public String list(Model model,
                       @RequestParam(defaultValue = "0") int page
    ) {
        final int ROWS = 20;
        Pageable pageable = PageRequest.of(page, ROWS, Sort.by("ctNo").ascending());
        Page<CocktailDto> cocktailList = newCocktailRepository.findAll(pageable);

        model.addAttribute("cocktailList", cocktailList);
        return "/cocktail/cocktailList";
    }

    @GetMapping(value = "/search.do", produces = "application/text; charset=UTF-8")
    public String search(Model model,
                         @RequestParam(defaultValue = "0") int page,
                         @RequestParam(required = false) String ctName,
                         @RequestParam(required = false) String ctKindEng,
                         HttpSession session,
                         HttpServletResponse resp
    ) throws Exception {
        session.removeAttribute("ctName");
        session.removeAttribute("ctKindEng");
        final int ROWS = 20;
        Pageable pageable = PageRequest.of(page, ROWS, Sort.by("ctNo").ascending());
        if (!StringUtils.hasText(ctName) && ctKindEng == null) {
            Page<CocktailDto> noCtNameCtKindEng = newCocktailRepository.findAll(pageable);
            model.addAttribute("cocktailList", noCtNameCtKindEng);
        } else if (StringUtils.hasText(ctName) && ctKindEng == null) {
            Page<CocktailDto> noCtKindEng = newCocktailRepository.findByCtNameContaining(ctName, pageable);
            if (noCtKindEng.isEmpty()) {
                resp.setContentType("text/html; charset=UTF-8");
                PrintWriter out = resp.getWriter();
                out.println("<script>alert('검색결과가 없습니다.'); location.href='/cocktail/list.do';</script>");
                out.flush();
            } else {
                session.setAttribute("ctName", ctName);
                model.addAttribute("cocktailList", noCtKindEng);
            }
        } else if (!StringUtils.hasText(ctName) && ctKindEng != null) {
            Page<CocktailDto> noCtName = newCocktailRepository.findByCtKindEng(ctKindEng, pageable);
                session.setAttribute("ctKindEng", ctKindEng);
                model.addAttribute("cocktailList", noCtName);

        } else {
            Page<CocktailDto> allYes = newCocktailRepository.findByCtNameContainingAndCtKindEng(ctName, ctKindEng, pageable);
            if (allYes.isEmpty()) {
                resp.setContentType("text/html; charset=UTF-8");
                PrintWriter out = resp.getWriter();
                out.println("<script>alert('검색결과가 없습니다.'); location.href='/cocktail/list.do';</script>");
                out.flush();
            } else {
            session.setAttribute("ctName", ctName);
            session.setAttribute("ctKindEng", ctKindEng);
            model.addAttribute("cocktailList", allYes);
            }
        }

        return "/search/cocktailSearch";
    }

    @GetMapping("/bar.do")
    public void bar(){}
    
    @GetMapping("/cocktailLike.do")
	 public void like() {}

}


