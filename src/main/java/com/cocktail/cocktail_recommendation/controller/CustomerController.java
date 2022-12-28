package com.cocktail.cocktail_recommendation.controller;

import com.cocktail.cocktail_recommendation.dto.Customer;
import com.cocktail.cocktail_recommendation.dto.CustomerDto;
import com.cocktail.cocktail_recommendation.repository.CustomerRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.util.Optional;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    @GetMapping("/mypage.do")
    public String mypage(@SessionAttribute(required = false) Customer loginUser,
                         HttpServletResponse resp,
                         Model model
    ) throws Exception {
        Optional<Customer> customer = customerRepository.getByCstIdAndCstPw(loginUser.getCstId(), loginUser.getCstPw());
        if (loginUser == null) {
            resp.setContentType("text/html; charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("<script>alert('로그인을 해주세요.'); location.href='/signup/login.do';</script>");
            out.flush();
        } else if (!loginUser.equals(customer)) {
            resp.setContentType("text/html; charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("<script>alert('잘못된 접근 입니다.'); location.href=';</script>");
            out.flush();
        }
        model.addAttribute("customer", customer);
        return "/customer/mypage";
    }

    @GetMapping("/update.do")
    public String customerUpdate(@SessionAttribute(required = false) Customer loginUser,
                                 HttpServletResponse resp,
                                 Model model) throws Exception {
        Optional<Customer> customerOpt = customerRepository.getByCstIdAndCstPw(loginUser.getCstId(), loginUser.getCstPw());
        if(customerOpt.isPresent()) {
            if (customerOpt.get().getCstId().equals(loginUser.getCstId()))  {
                model.addAttribute("customer", customerOpt.get());
                return "/customer/update";
            } else {
                resp.setContentType("text/html; charset=UTF-8");
                PrintWriter out = resp.getWriter();
                out.println("<script>alert('잘못된 접근 입니다.'); location.href=';</script>");
                out.flush();
                return "redirect:/";
            }
        } else {
            resp.setContentType("text/html; charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("<script>alert('잘못된 접근 입니다.'); location.href=';</script>");
            out.flush();
            return "redirect:/";
        }
    }

    @PostMapping("/update.do")
    public String customerUpdate(Customer customer, String cstPw) {
        Customer saveCustomer = null;

        try {
            customer.setCstPw(encoder.encode(cstPw));
            saveCustomer = customerRepository.save(customer);
        } catch (Exception e)   {
            e.printStackTrace();
        }
        if (saveCustomer != null)   {
            return "redirect:/signup/logout.do";
        } else {
            return "redirect:/customer/update.do?cstId=" + customer.getCstId();
        }
    }
}
