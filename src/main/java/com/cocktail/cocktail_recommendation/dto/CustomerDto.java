package com.cocktail.cocktail_recommendation.dto;


import jakarta.persistence.*;
import lombok.*;

@Data  //POJO 형식의 get set을 class로 자동 컴파일
@Entity //TABLE을 Repository가 맵핑하겠다.
@Table(name="customer")  //jpa가 class명을 테이블 이름으로 맵핑하기 때문에 이름이 다를때 명시
public class CustomerDto {
   @Id
   @Column(name = "CST_ID")
   private String cstId;
   @Column(name = "CST_PW")
   private String cstPw;
   @Column(name = "CST_EMAIL")
   private String cstEmail;
   @Column(name = "CST_YEAR")
   private String cstYear;
   @Column(name = "CST_FLAVOR")
   private String cstFlavor;
   @Column(name = "CST_NICKNAME")
   private String cstNickname;
}

