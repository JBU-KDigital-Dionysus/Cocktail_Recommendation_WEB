package com.cocktail.cocktail_recommendation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cocktail.cocktail_recommendation.dto.CustmerLikeDto;
import com.cocktail.cocktail_recommendation.dto.Customer;


public interface CustmerLikeRepository  extends JpaRepository<CustmerLikeDto, String>  {
	

}
