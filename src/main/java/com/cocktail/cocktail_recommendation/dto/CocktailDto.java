package com.cocktail.cocktail_recommendation.dto;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="newcocktail")
public class CocktailDto {

    @Id
    @Column(name = "CT_NO")
    private int ctNo;
    @Column(name = "CT_NAME")
    private String ctName;
    @Column(name = "CT_ALCOHOL")
    private float ctAlcohol;
    @Column(name = "CT_ALCOHOL_CLASS")
    private int ctAlcoholClass;
    @Column(name = "CT_FLAVOR_CLASS")
    private int ctFlavorClass;
    @Column(name = "CT_KIND")
    private String ctKind;
    @Column(name = "CT_SEASON")
    private String ctSeason;
    @Column(name = "CT_TIME")
    private String ctTime;
    @Column(name = "CT_CERTIFIED")
    private String ctCertified;
    @Column(name = "ING_NAME1")
    private String ingName1;
    @Column(name = "ING_NAME2")
    private String ingName2;
    @Column(name = "ING_NAME3")
    private String ingName3;
    @Column(name = "ING_NAME4")
    private String ingName4;
    @Column(name = "ING_NAME5")
    private String ingName5;
    @Column(name = "ING_NAME6")
    private String ingName6;
    @Column(name = "ING_NAME7")
    private String ingName7;
    @Column(name = "ING_NAME8")
    private String ingName8;
    @Column(name = "ING_NAME9")
    private String ingName9;
    @Column(name = "ING_NAME10")
    private String ingName10;
    @Column(name = "ING_NAME11")
    private String ingName11;
    @Column(name = "ING_NAME12")
    private String ingName12;
    @Column(name = "ING_UNIT1")
    private String ingUnit1;
    @Column(name = "ING_UNIT2")
    private String ingUnit2;
    @Column(name = "ING_UNIT3")
    private String ingUnit3;
    @Column(name = "ING_UNIT4")
    private String ingUnit4;
    @Column(name = "ING_UNIT5")
    private String ingUnit5;
    @Column(name = "ING_UNIT6")
    private String ingUnit6;
    @Column(name = "ING_UNIT7")
    private String ingUnit7;
    @Column(name = "ING_UNIT8")
    private String ingUnit8;
    @Column(name = "ING_UNIT9")
    private String ingUnit9;
    @Column(name = "ING_UNIT10")
    private String ingUnit10;
    @Column(name = "ING_UNIT11")
    private String ingUnit11;
    @Column(name = "ING_UNIT12")
    private String ingUnit12;

}
