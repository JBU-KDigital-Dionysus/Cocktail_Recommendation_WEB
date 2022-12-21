package com.cocktail.cocktail_recommendation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cocktail.cocktail_recommendation.dto.CocktailFlavorDto;

public interface CocktailFlavorRepository extends JpaRepository<CocktailFlavorDto, String>{
	
}
