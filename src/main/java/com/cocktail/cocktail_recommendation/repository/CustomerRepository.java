package com.cocktail.cocktail_recommendation.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.cocktail.cocktail_recommendation.dto.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String>{
    Optional<Customer> findCustomerByCstId(String cstId);
}
