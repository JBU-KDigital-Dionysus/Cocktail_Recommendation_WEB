package com.cocktail.cocktail_recommendation.controller;

import java.io.PrintWriter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.cocktail.cocktail_recommendation.dto.CocktailDto;
import com.cocktail.cocktail_recommendation.dto.CustmerLikeDto;
import com.cocktail.cocktail_recommendation.dto.Customer;
import com.cocktail.cocktail_recommendation.dto.CustomerDto;
import com.cocktail.cocktail_recommendation.repository.CocktailRepository;
import com.cocktail.cocktail_recommendation.repository.CustmerLikeRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cocktail")
public class CocktailController {

	@Autowired
	CocktailRepository newCocktailRepository;

	@Autowired
	CustmerLikeRepository likeRepository;

	@GetMapping("/detail.do")
	public String detail(
			@RequestParam(required = true) int ctNo, Model model, HttpSession session,
			@SessionAttribute(required = false) Customer loginUser) {
		String cstId = (String) session.getAttribute("cstId");
		
		Optional<CocktailDto> newCocktailOpt = newCocktailRepository.findById(ctNo);

		if (loginUser != null) {
			Optional<CustmerLikeDto> likePreferOpt = likeRepository.getByCstIdAndCtNo(cstId, ctNo);
			if (likePreferOpt.isPresent()) {
				model.addAttribute("likePrefer", likePreferOpt.get());
			}
		}

		if (newCocktailOpt.isPresent()) {
			model.addAttribute("cocktail", newCocktailOpt.get());
			return "cocktail/detail";
		} else {
			return "cocktail/list.do";
		}
	}

	@GetMapping("/prefer/{ctNo}/{preferBtn}")
	public String preferManagin(
			@PathVariable int ctNo, @PathVariable boolean preferBtn, Model model,
			
			HttpSession session) {
		String cstId = (String) session.getAttribute("cstId");
		
		if (cstId.isEmpty()) {
			session.setAttribute("msg", "찜 이용은 로그인 시 가능!");
			return "redirect:/signup/login.do";
		}
		Optional<CustmerLikeDto> custmerLikeDtoOpt = likeRepository.getByCstIdAndCtNo(cstId, ctNo);
		try {
			if (custmerLikeDtoOpt.isEmpty()) {
				CustmerLikeDto like = new CustmerLikeDto();
				like.setPreferBtn(preferBtn);
				like.setCstId(cstId);
				like.setCtNo(ctNo);
				likeRepository.save(like);
			} else {
				// 좋아요를 했었는데 다시 좋아요를 누른 (삭제)
				if (custmerLikeDtoOpt.get().isPreferBtn() == preferBtn) {
					likeRepository.deleteById(custmerLikeDtoOpt.get().getLikePreferNo());
				} else { // 좋아요를 했었는데 싫어요를 누른(수정)
					custmerLikeDtoOpt.get().setPreferBtn(preferBtn);
					likeRepository.save(custmerLikeDtoOpt.get());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/cocktail/detail.do?ctNo=" + ctNo;
	}

	@GetMapping("/list.do")
	public String list(Model model, @RequestParam(defaultValue = "0") int page) {
		final int ROWS = 20;
		Pageable pageable = PageRequest.of(page, ROWS, Sort.by("ctNo").ascending());
		Page<CocktailDto> cocktailList = newCocktailRepository.findAll(pageable);

		model.addAttribute("cocktailList", cocktailList);
		return "/cocktail/cocktailList";
	}

	@GetMapping(value = "/search.do", produces = "application/text; charset=UTF-8")
	public String search(Model model, @RequestParam(defaultValue = "0") int page,
			@RequestParam(required = false) String ctName, @RequestParam(required = false) String ctKindEng,
			HttpSession session, HttpServletResponse resp) throws Exception {
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
			Page<CocktailDto> allYes = newCocktailRepository.findByCtNameContainingAndCtKindEng(ctName, ctKindEng,
					pageable);
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
	public void bar() {
	}
	
	@GetMapping("/cocktailLike.do")
	public void like() {
	}


}
