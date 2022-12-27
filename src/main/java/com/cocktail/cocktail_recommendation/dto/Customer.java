package com.cocktail.cocktail_recommendation.dto;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Data
@Entity //TABLE을 Repository가 맵핑하겠다.
@Table(name="customer")  //jpa가 class명을 테이블 이름으로 맵핑하기 때문에 이름이 다를때 명시
public class Customer {
   
   @Id 
   @Column(name = "CST_ID",nullable = false, length = 30, unique = true)
   private String cstId;
   @Column(name = "CST_PW",length = 100)
   private String cstPw;
   @Column(name = "CST_EMAIL",nullable = false, length = 50, unique = true)
   private String cstEmail;
   @Column(name = "CST_YEAR",unique = true)
   private String cstYear;
   @Column(name = "CST_FLAVOR")
   private String cstFlavor;
   @Column(name = "CST_NICKNAME",nullable = false, length = 50, unique = true)
   private String cstNickname;
}