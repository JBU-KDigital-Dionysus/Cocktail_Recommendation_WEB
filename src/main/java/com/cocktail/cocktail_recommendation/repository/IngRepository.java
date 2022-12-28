package com.cocktail.cocktail_recommendation.repository;


import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cocktail.cocktail_recommendation.dto.IngredientDto;

//JpaRepository<dto(entity),pk type>: 자동으로 쿼리 생성
@Repository
public interface IngRepository extends JpaRepository<IngredientDto, Integer>{

	

}