package com.cocktail.cocktail_recommendation.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.cocktail.cocktail_recommendation.dto.CustmerLikeDto;
import com.cocktail.cocktail_recommendation.dto.Customer;
import com.cocktail.cocktail_recommendation.dto.IngredientDto;
import com.cocktail.cocktail_recommendation.dto.ReplyDto;
import com.cocktail.cocktail_recommendation.repository.ReplyRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/reply")
public class ReplyController {
	
	@Autowired
	ReplyRepository replyRepository;
	
	@GetMapping("/insert.do")
	public void  replyinsert() {}
	
	@PostMapping("/insert.do")
	public String replyinsert(
			@SessionAttribute(required=false) Customer loginUser,
			HttpSession session,
			ReplyDto ingreDto
			
			) {
		ReplyDto saveBoard=null;
		try {
			saveBoard=replyRepository.save(ingreDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(saveBoard==null) {
			return "redirect:/cocktail/list.do";			
		}else {
			return "redirect:/cocktail/detail.do?ctNo="+saveBoard.getCtNo();			
		}
	}
	
	@GetMapping("/replyDelete.do")
	public String replyDelete(
			@SessionAttribute(required = false) Customer loginUser, 
			 int replyNo,
			HttpServletResponse resp
			) throws IOException
	{
		Optional<ReplyDto> custmerLikeDtoOpt 
		= replyRepository.getByCstIdAndReplyNo(loginUser.getCstId(), replyNo);
		if (custmerLikeDtoOpt.isPresent()) {
			if (loginUser == null) {
				resp.setContentType("text/html; charset=UTF-8");
				PrintWriter out = resp.getWriter();
				out.println("<script>alert('로그인을 해주세요.'); location.href='/signup/login.do';</script>");
				out.flush();
			} else if (!loginUser.getCstId().equals(custmerLikeDtoOpt.get().getCustomer().getCstId())) {
				resp.setContentType("text/html; charset=UTF-8");
				PrintWriter out = resp.getWriter();
				out.println("<script>alert('회원 정보가 다릅니다.'); location.href='/signup/login.do';</script>");
				out.flush();
			}
			replyRepository.deleteById(custmerLikeDtoOpt.get().getReplyNo());
		} 
		return "redirect:/cocktail/detail.do?ctNo="+custmerLikeDtoOpt.get().getCtNo();		
	}
	
}