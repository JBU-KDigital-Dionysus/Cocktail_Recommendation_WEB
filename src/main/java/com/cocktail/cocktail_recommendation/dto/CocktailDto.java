package com.cocktail.cocktail_recommendation.dto;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;


@Getter
@Entity
@Table(name="cocktail_m")
public class CocktailDto {

    @Id
    @Column(name = "CT_NO")
    private String ctNo;
    @Column(name = "CT_NAME")
    private String ctName;
    @Column(name = "CT_IMAGE")
    private String ctImage;
    @Column(name = "CT_ALCOHOL")
    private float ctAlcohol;
    @Column(name = "CT_KIND")
    private String ctKind;
    @Column(name = "CT_KIND_ENG")
    private String ctKindEng;
    @Column(name = "CT_SEASON")
    private String ctSeason;
    @Column(name = "CT_TIME")
    private String ctTime;
    @Column(name = "CT_CERTIFIED")
    private String ctCertified;
    @Column(name = "CT_RECIPE")
    private String ctRecipe;
    @Column(name = "CT_RECIPE_LINK")
    private String ctRecipeLink;
    @OneToOne
    @JoinColumn(name = "CT_NO", updatable = false)
    private IngredientDto ingredient;


}
