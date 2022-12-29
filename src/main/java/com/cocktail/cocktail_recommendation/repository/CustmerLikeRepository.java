package com.cocktail.cocktail_recommendation.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cocktail.cocktail_recommendation.dto.CustmerLikeDto;


public interface CustmerLikeRepository  extends JpaRepository<CustmerLikeDto, Integer>  {
	
	Optional<CustmerLikeDto> getByCstIdAndCtNo(String cstId, int ctNo);

	List<CustmerLikeDto> getByCstId(String cstId, Sort sort);

	Optional<CustmerLikeDto> findByCstId(String cstId);
}