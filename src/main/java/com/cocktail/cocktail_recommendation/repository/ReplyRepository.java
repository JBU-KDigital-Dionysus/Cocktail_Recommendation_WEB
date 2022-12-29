package com.cocktail.cocktail_recommendation.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cocktail.cocktail_recommendation.dto.CustmerLikeDto;
import com.cocktail.cocktail_recommendation.dto.ReplyDto;

public interface ReplyRepository extends JpaRepository<ReplyDto, Integer>{
	
	Page<ReplyDto> findByCtNo(int ctNo, Pageable pageable);
	
	Optional<ReplyDto> getByCstIdAndReplyNo(String cstId, int replyNo);

}
