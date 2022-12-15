package com.cocktail.cocktail_recommendation.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cocktail.cocktail_recommendation.dto.CustomerDto;
import com.cocktail.cocktail_recommendation.service.CustomerService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/signup")
public class SignupController {
	

	@Autowired
	CustomerService customerService;

	//약관동의페이지
	@GetMapping("/agreed.do")
	public void AgreedPage() {}
	
	
	@GetMapping("/joinProc")
	public String joinProc2(CustomerDto.RequestCustomerDto customerDto) {
		return "/signup/joinProc";
	}
	@PostMapping("/joinProc")
	public String joinProc(@Valid CustomerDto.RequestCustomerDto customerDto, BindingResult bindingResult, Model model) {
		
		/* 검증 */
		if(bindingResult.hasErrors()) {
			/* 회원가입 실패 시 입력 데이터 값 유지 */
			model.addAttribute("customerDto", customerDto);
			
			/* 유효성 검사를 통과하지 못 한 필드와 메시지 핸들링 */
			Map<String, String> errorMap = new HashMap<>();
			
			for(FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put("valid_"+error.getField(), error.getDefaultMessage());
			}
			
			/* Model에 담아 view resolve */
			for(String key : errorMap.keySet()) {
				model.addAttribute(key, errorMap.get(key));
			}

			/* 회원가입 페이지로 리턴 */
			return "/signup/joinProc";
		}
		
		// 회원가입 성공 시
		customerService.join(customerDto);
		return "redirect:/";
	}
}


