package com.microservices.product;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/*
 * Product REST controller
 * 
 */
@RestController
public class ProductController {

	ProductRepository productRepository;

	@Autowired
	public ProductController(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	/*
	 * This service adds a new product to the products table and returns a
	 * success or failure response
	 */
	@RequestMapping(value = "/product/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String saveProduct(@RequestBody String productDetails) {
		JSONObject inputJson = new JSONObject(productDetails);
		Product product = new Product();
		JSONObject jsonResponse = new JSONObject();
		product.setProductName(inputJson.getString("product_name"));
		product.setProductType(inputJson.getString("product_type"));
		Product returnProduct = productRepository.save(product);
		if (returnProduct.getProductId() > 0) {
			jsonResponse.put("message", "success");
		} else {
			jsonResponse.put("message", "failure");
		}
		return jsonResponse.toString();

	}

	/*
	 * This service deletes a product based on the produc name
	 */
	@Transactional
	@RequestMapping(value = "/product/{product_name}/delete", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String deleteProduct(@PathVariable("product_name") String productName) {
		JSONObject jsonResponse = new JSONObject();
		int result = productRepository.deleteByProductName(productName);
		if (result == 1) {
			jsonResponse.put("message", "success");
		} else {
			jsonResponse.put("message", "failure");
		}
		return jsonResponse.toString();
	}

	/*
	 * This service returns all products based on the product type
	 */
	@RequestMapping(value = "/product/{product_type}/getallproducts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Product> getAllProducts(
			@PathVariable("product_type") String searchCriteria) {
		List<Product> products = productRepository
				.getProductsbyProductType(searchCriteria);
		if (products.size() > 0) {
			return products;
		} else {
			return new ArrayList<Product>();
		}
	}

	/*
	 * This service return a product based on the product name
	 */
	@RequestMapping(value = "/product/{product_name}/getproduct", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getProduct(@PathVariable("product_name") String productName) {
		JSONObject jsonResponse = new JSONObject();
		Product product = productRepository
				.getProductbyProductName(productName);
		if (product != null) {
			jsonResponse.put("product_name", product.getProductName());
			jsonResponse.put("product_type", product.getProductType());
			jsonResponse.put("product_id", product.getProductId());
		} else {
			jsonResponse.put("message",
					"No products found with the given product name");
		}
		return jsonResponse.toString();
	}

}
