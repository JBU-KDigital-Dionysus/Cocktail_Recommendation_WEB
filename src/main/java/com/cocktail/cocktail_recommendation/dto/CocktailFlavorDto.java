package com.cocktail.cocktail_recommendation.dto;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="flavor")
public class CocktailFlavorDto {
    @Id
    @Column(name = "CT_FLAVOR")
    private String  ctFlavor;
   
  

}
