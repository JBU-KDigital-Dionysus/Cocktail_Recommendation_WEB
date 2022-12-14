package com.cocktail.cocktail_recommendation.repository;

import com.cocktail.cocktail_recommendation.dto.NewCocktailDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewCocktailRepository extends JpaRepository<NewCocktailDto, Integer> {
}
