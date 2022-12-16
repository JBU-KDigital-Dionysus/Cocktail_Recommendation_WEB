package com.cocktail.cocktail_recommendation.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cocktail.cocktail_recommendation.dto.Customer;
import com.cocktail.cocktail_recommendation.dto.CustomerDto.RequestCustomerDto;
import com.cocktail.cocktail_recommendation.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomerService {
	private final CustomerRepository customerRepository;
	 
    private final BCryptPasswordEncoder encoder;
    
    @Transactional
    public String join(RequestCustomerDto dto) { 
    	dto.encryptPassword(encoder.encode(dto.getCstPw()));
		
		Customer customer = dto.toEntity();
		customerRepository.save(customer);
		
		return customer.getCstId();
    }

	@Transactional(readOnly = true)
	public String checkID(String cstId){
		Optional<Customer> customer = customerRepository.findCustomerByCstId(cstId);
		return customer.map(Customer::getCstId).orElse(null);
	}
}
