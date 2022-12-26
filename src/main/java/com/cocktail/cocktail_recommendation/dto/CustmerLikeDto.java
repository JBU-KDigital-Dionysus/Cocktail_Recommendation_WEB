package com.cocktail.cocktail_recommendation.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name="custmer_like")
public class CustmerLikeDto {
	 	@Id
	    @Column(name = "LIKED_DT")
	    private String likedDt;
	 	
	 	@Column(name = "LIKE_YN")
	    private String likeYn;
	 	
	 	@ManyToOne
	    @JoinColumn(name = "CT_NO")
	    private CocktailDto cocktailLike;
	 	
	 	@OneToOne
	    @JoinColumn(name = "CST_ID" )
	    private Customer customerLike;
	 	
	 	
}
