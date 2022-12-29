package com.cocktail.cocktail_recommendation.dto;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data 
@Entity
@Table(name="customer_like")
public class CustmerLikeDto {
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "LIKE_PERFER_NO")
		private int likePreferNo; 
		
		@Column(name = "CST_ID", length = 30)
		private String cstId;
		
		@Column(name = "CT_NO")
		private int ctNo;
		
	    @Column(name = "LIKED_DT")
	    private String likedDt;
	 	
	    @Column(name = "LIKE_PREFER")
	    private boolean preferBtn;  

	 	
	 	@OneToOne
	 	@JoinColumn(name = "CST_ID" ,insertable=false, updatable=false )
	 	private Customer customerLike;
	 	
	 	@ManyToOne
	    @JoinColumn(name = "CT_NO", insertable=false, updatable=false)
	    private CocktailDto cocktailLike;

		
	 	
	 	
	 	
}