package com.cocktail.cocktail_recommendation.repository;

import com.cocktail.cocktail_recommendation.dto.CocktailDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CocktailRepository extends JpaRepository<CocktailDto, Integer> {
}
