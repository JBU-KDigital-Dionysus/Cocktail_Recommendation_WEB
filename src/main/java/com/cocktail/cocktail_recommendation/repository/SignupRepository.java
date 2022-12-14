package com.cocktail.cocktail_recommendation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cocktail.cocktail_recommendation.dto.CustomerDto;

import java.util.Optional;

public interface SignupRepository extends JpaRepository<CustomerDto, String>{
    Optional<CustomerDto> getByCstIdAndCstPw(String cstId, String cstPw);
}
