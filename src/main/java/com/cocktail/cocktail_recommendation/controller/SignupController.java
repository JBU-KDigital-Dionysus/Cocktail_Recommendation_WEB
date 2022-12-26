package com.cocktail.cocktail_recommendation.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.cocktail.cocktail_recommendation.dto.Customer;
import com.cocktail.cocktail_recommendation.repository.CustomerRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;


import com.cocktail.cocktail_recommendation.dto.CustomerDto;
import com.cocktail.cocktail_recommendation.service.CustomerService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/signup")
public class SignupController {


    @Autowired
    CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;


    //약관동의페이지
    @GetMapping("/agreed.do")
    public void AgreedPage() {
    }


    @GetMapping("/joinProc")
    public String joinProc2(CustomerDto.RequestCustomerDto customerDto) {
        return "/signup/joinProc";
    }

    @PostMapping("/joinProc")
    public String joinProc(@Valid CustomerDto.RequestCustomerDto customerDto, BindingResult bindingResult, Model model) {

        /* 검증 */
        if (bindingResult.hasErrors()) {
            /* 회원가입 실패 시 입력 데이터 값 유지 */
            model.addAttribute("customerDto", customerDto);

            /* 유효성 검사를 통과하지 못 한 필드와 메시지 핸들링 */
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put("valid_" + error.getField(), error.getDefaultMessage());
            }

            /* Model에 담아 view resolve */
            for (String key : errorMap.keySet()) {
                model.addAttribute(key, errorMap.get(key));
            }

            /* 회원가입 페이지로 리턴 */
            return "/signup/joinProc";
        }

        // 회원가입 성공 시
        customerService.join(customerDto);
        return "redirect:/";
    }

    @PostMapping("/checkId")
    @ResponseBody
    public int checkid(@RequestParam("cstId") String cstId) {
        if (cstId.equals(customerService.checkID(cstId))) {
            return 1;
        }
        return 0;
    }

    @GetMapping("/login.do")
    public void login(
            HttpServletRequest req,
            HttpSession session
    ) {
        String refererPage = req.getHeader("Referer");//로그인 폼 오기 전 페이지
        session.setAttribute("redirectPage", refererPage);

    }

    @PostMapping("/login.do")
    public String login(@RequestParam(required = true) String cstId,
    		@RequestParam(required = true) String cstPw,
    		HttpSession session,
            @SessionAttribute(required = false) String redirectPage,
                        
                        HttpServletResponse resp) throws IOException {
        Optional<Customer> customer = customerRepository.findById(cstId);

        if (customer.isEmpty()) {
            resp.setContentType("text/html; charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("<script>alert('아이디와 비밀번호를 확인해주세요.'); location.href='/signup/login.do';</script>");
            out.flush();
        }
        boolean result = encoder.matches(cstPw, customer.get().getCstPw());
        if (result == true) {
        	session.setAttribute("cstId", cstId);
            session.setAttribute("loginUser", customer);
            return "redirect:/";
        } else {
            resp.setContentType("text/html; charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("<script>alert('아이디와 비밀번호를 확인해주세요.'); location.href='/signup/login.do';</script>");
            out.flush();
            return "redirect:/signup/login.do";
        }
    }

    @GetMapping("/logout.do")
    public String logout(HttpSession session) {
        session.removeAttribute("loginUser");
        return "redirect:/";
    }
    
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
          return "signup/login.do";
      }
  }
    

}


