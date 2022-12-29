package com.cocktail.cocktail_recommendation.dto;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "REPLY")
public class ReplyDto {
	@Id
	@Column(name = "REPLY_NO")
	private int replyNo; //PK 
	@Column(name = "CT_NO")
	private int ctNo; //FK: baord.baord_no 
	@Column(name = "TITLE")
	private String title;    
	@Column(name = "CONTENTS")
	private String contents; 
	@Column(name = "POST_TIME")
	private Date postTime;
	@Column(name = "CST_ID")
	private String cstId ;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="CST_ID",insertable=false,updatable=false)
	private Customer customer;
	
	@ManyToOne
    @JoinColumn(name = "CT_NO", insertable=false, updatable=false)
    private CocktailDto cocktailLike;
	
	
	
}
