package com.microservices.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/*
 * Product repository
 * 
 */
public interface ProductRepository extends JpaRepository<Product, Integer>{

	  @Modifying
	  @Query("delete from Product p where p.productName = ?1")
	  public int deleteByProductName(String productName);
	  
	  @Query("select p from Product p where p.productType = ?1")
	  public List<Product> getProductsbyProductType(String productType);
	  
	  @Query("select p from Product p where p.productName = ?1")
	  public Product getProductbyProductName(String productName);
}

