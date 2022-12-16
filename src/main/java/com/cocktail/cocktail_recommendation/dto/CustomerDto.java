package com.cocktail.cocktail_recommendation.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CustomerDto {
	
	/* 회원 서비스 요청 RequestDTO 클래스 */
	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Builder
	@Setter
	public static class RequestCustomerDto{
	
		
		@NotBlank(message = "아이디는 필수 입력값입니다.")
		@Pattern(regexp = "^[a-z0-9]{4,20}$", message = "아이디는 영어 소문자와 숫자만 사용하여 4~20자리여야 합니다.")
		private String cstId;
		
		@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$", message = "비밀번호는 8~16자리수여야 합니다. 영문 대소문자, 숫자, 특수문자를 1개 이상 포함해야 합니다.")
		private String cstPw;
		
		@NotBlank(message = "닉네임은 필수 입력값입니다.")
		@Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,10}$" , message = "닉네임은 특수문자를 포함하지 않은 2~10자리여야 합니다.")
		private String cstNickname;
		
		@NotBlank(message = "이메일은 필수 입력값입니다.")
		@Email(message = "이메일 형식이 올바르지 않습니다.")
		private String cstEmail;
		@Pattern(regexp = "^[0-9]{4}$" , message = "출생연도는 숫자만 사용하여 4자리 형태로 입력하세요. ex) 1993")
		private String cstYear;
		
		private String cstFlavor;
		
	
		/* 암호화된 password */
		public void encryptPassword(String BCryptpassword) {
			this.cstPw = BCryptpassword;
		}
		
		/* DTO -> Entity */
		public Customer toEntity() {
			Customer customer = Customer.builder()
						.cstId(cstId)
						.cstPw(cstPw)
						.cstNickname(cstNickname)
						.cstEmail(cstEmail)
						.cstYear(cstYear)
						.cstFlavor(cstFlavor)
						.build();
			return customer;
		}
	}
	
}