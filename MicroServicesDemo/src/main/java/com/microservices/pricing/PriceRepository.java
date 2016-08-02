package com.microservices.pricing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/*
 * Price repository
 * 
 */
public interface PriceRepository extends JpaRepository<Pricing, Integer>{
  
  @Query("select p.productPrice from Pricing p where p.productName = ?1")
  public Integer getPrice(String productName);

}
