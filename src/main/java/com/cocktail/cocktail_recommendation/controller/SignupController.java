package com.cocktail.cocktail_recommendation.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cocktail.cocktail_recommendation.dto.CustomerDto;
import com.cocktail.cocktail_recommendation.repository.SignupRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Optional;

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

	@GetMapping("/login.do")
	public void login(HttpServletRequest req,
					  HttpSession session)	{
		String refererPage = req.getHeader("Referer");//로그인 폼 오기 전 페이지
		session.setAttribute("redirectPage", refererPage);
	}

	@PostMapping("login.do")
	public String login(String cstId, String cstPw, HttpSession session,
						@SessionAttribute(required = false)String redirectPage)	{
		Optional<CustomerDto> customerOpt = null;
		//loginUserOpt = userRepository.findOptionalByUserIdAndPw(userId, pw);
		customerOpt = signupRepository.getByCstIdAndCstPw(cstId, cstPw);
		if (customerOpt.isEmpty()) {
			return "redirect:/signup/login.do";
		} else {
			session.setAttribute("loginUser", customerOpt.get());
			if (redirectPage!=null) {
				return "redirect:"+redirectPage;
			}
			return "redirect:/";
		}
	}

	@GetMapping("/logout.do")
	public String logout(HttpSession session) {
		session.removeAttribute("loginUser");
		return "redirect:/";
	}
}
