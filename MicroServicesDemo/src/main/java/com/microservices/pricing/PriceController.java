package com.microservices.pricing;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/*
 * Pricing REST controller
 * 
 */
@RestController
public class PriceController {

	PriceRepository priceRepository;

	@Autowired
	public PriceController(PriceRepository priceRepository) {
		this.priceRepository = priceRepository;
	}

	/*
	 * This service returns the price of product based on the product name
	 * 
	 */
	@RequestMapping(value = "/pricing/{product_name}/getprice", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getPrice(@PathVariable("product_name") String productName) {
		JSONObject jsonResponse = new JSONObject();
		Integer price = priceRepository.getPrice(productName);
		if (price == null) {
			jsonResponse.put("price", "product name not found");
		} else {
			jsonResponse.put("price", price);
			jsonResponse.put("product_name", productName);
		}
		return jsonResponse.toString();
	}

	/*
	 * This service adds pricing information to a 
	 * 
	 */
	@RequestMapping(value = "/pricing/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String saveProduct(@RequestBody String productDetails) {
		JSONObject inputJson = new JSONObject(productDetails);
		Pricing pricing = new Pricing();
		JSONObject jsonResponse = new JSONObject();
		pricing.setProductName(inputJson.getString("product_name"));
		pricing.setProductPrice(inputJson.getInt("product_price"));
		Pricing returnPricing = priceRepository.save(pricing);
		if (returnPricing.getPriceId() > 0) {
			jsonResponse.put("message", "success");
		} else {
			jsonResponse.put("message", "failure");
		}
		return jsonResponse.toString();

	}
}
