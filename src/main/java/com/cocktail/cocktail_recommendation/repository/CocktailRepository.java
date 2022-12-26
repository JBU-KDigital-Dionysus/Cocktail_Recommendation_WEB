package com.cocktail.cocktail_recommendation.repository;

import com.cocktail.cocktail_recommendation.dto.CocktailDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CocktailRepository extends JpaRepository<CocktailDto, String> {
    Page<CocktailDto> findByCtNameContainingAndCtKindEng(String ctName, String ctKindEng,Pageable pageable);
    Page<CocktailDto> findByCtKindEng(String ctKindEng, Pageable pageable);
    Page<CocktailDto> findByCtNameContaining(String ctName, Pageable pageable);
}
