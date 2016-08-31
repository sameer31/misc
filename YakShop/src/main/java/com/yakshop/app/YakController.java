package com.yakshop.app;

import java.io.File;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yakshop.utility.Constants;
import com.yakshop.utility.ReadWriteJSON;

/**
 * @author Sunny Mourya
 * 
 *         This class is the REST controller class containing all the REST
 *         services defined for YakShop application
 *
 */
@RestController
public class YakController {

	@Autowired
	YakService yakService;

	JSONParser jsonParser = new JSONParser();

	/**
	 * @param stockDetails
	 * @return JSONObject
	 * 
	 *         This REST call is used to create the initial inventory for
	 *         YakShop application
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/yak-shop/herd", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject setHerd(@RequestBody String stockDetails) {
		JSONObject jsonResponse = new JSONObject();
		try {
			JSONObject inputJson = (JSONObject) jsonParser.parse(stockDetails);
			JSONArray jsonArray = (JSONArray) inputJson.get(Constants.HERD);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject yak = (JSONObject) jsonArray.get(i);
				double ageIndays = (Double) yak.get(Constants.AGE) * 100;
				yak.put(Constants.AGE, ageIndays);
				yak.put(Constants.AGE_LAST_SHAVED, ageIndays);
			}
			ReadWriteJSON.writeJSON(Constants.INPUT_FILE, inputJson);
			jsonResponse.put("message", "success");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return jsonResponse;
	}

	/**
	 * @param days
	 * @return JSONObject
	 * 
	 *         This REST call provides a view of the YakShop herd on the day
	 *         specified in the request
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/yak-shop/herd/{days}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getHerd(@PathVariable("days") int days) {
		JSONObject jsonResponse = ReadWriteJSON.readJSON(Constants.INPUT_FILE);
		JSONArray jsonArray = (JSONArray) jsonResponse.get(Constants.HERD);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject yak = (JSONObject) jsonArray.get(i);
			double currentAge = (Double) yak.get(Constants.AGE);
			double newAge = (currentAge + days) / 100;
			double ageLastShaved = currentAge / 100;
			yak.put(Constants.AGE, newAge);
			yak.put(Constants.AGE_LAST_SHAVED, ageLastShaved);
		}
		return jsonResponse;
	}

	/**
	 * @param days
	 * @return JSONObject
	 * 
	 *         This REST call provides a view of the stock available at the
	 *         YakShop on the day specified in the request
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/yak-shop/stock/{days}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getStock(@PathVariable("days") int days) {
		JSONObject herdJson = ReadWriteJSON.readJSON(Constants.INPUT_FILE);
		JSONArray jsonArray = (JSONArray) herdJson.get(Constants.HERD);

		double totalMilkAvailable = yakService.getTotalMilkProduced(jsonArray,
				days - 1);
		double totalSkinsAvailable = yakService.getTotalSkinsProduced(
				jsonArray, days - 1);

		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put(Constants.MILK, totalMilkAvailable);
		jsonResponse.put(Constants.SKINS, totalSkinsAvailable);
		return jsonResponse;
	}

	/**
	 * @param days
	 * @param orderDetails
	 * @param response
	 * @return JSONObject
	 * 
	 *         This REST call is used to accept customer orders for milk and
	 *         skins at the YakShop
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/yak-shop/order/{days}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject orderStock(@PathVariable("days") int days,
			@RequestBody String orderDetails, HttpServletResponse response) {
		JSONObject jsonResponse = new JSONObject();
		try {
			JSONObject inputJson = (JSONObject) jsonParser.parse(orderDetails);
			JSONObject order = (JSONObject) inputJson.get("order");
			long milkOrder = (long) order.get(Constants.MILK);
			long skinOrder = (long) order.get(Constants.SKINS);

			JSONObject herdJson = ReadWriteJSON.readJSON(Constants.INPUT_FILE);
			JSONArray jsonArray = (JSONArray) herdJson.get(Constants.HERD);

			double totalMilkAvailable = yakService.getTotalMilkProduced(
					jsonArray, days - 1);
			long totalSkinsAvailable = yakService.getTotalSkinsProduced(
					jsonArray, days - 1);

			File orderFile = new File(Constants.ORDERS_FILE);
			if (orderFile.exists()) {
				JSONObject orderJson = ReadWriteJSON
						.readJSON(Constants.ORDERS_FILE);
				double milkOrdered = (double) orderJson.get(Constants.MILK);
				long skinsOrdered = (long) orderJson.get(Constants.SKINS);

				double availableMilk = totalMilkAvailable - milkOrdered;
				long availableSkins = totalSkinsAvailable - skinsOrdered;

				if (milkOrder <= availableMilk && skinOrder <= availableSkins) {
					milkOrdered = milkOrdered + milkOrder;
					skinsOrdered = skinsOrdered + skinOrder;
					jsonResponse.put(Constants.MILK, (double) milkOrder);
					jsonResponse.put(Constants.SKINS, skinOrder);
					response.setStatus(HttpServletResponse.SC_CREATED);
				} else if (milkOrder <= availableMilk) {
					milkOrdered = milkOrdered + milkOrder;
					jsonResponse.put(Constants.MILK, (double) milkOrder);
					response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
				} else if (skinOrder <= availableSkins) {
					skinsOrdered = skinsOrdered + skinOrder;
					jsonResponse.put(Constants.SKINS, skinOrder);
					response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
				} else {
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				}
				orderJson.put(Constants.MILK, milkOrdered);
				orderJson.put(Constants.SKINS, skinsOrdered);
				ReadWriteJSON.writeJSON(Constants.ORDERS_FILE, orderJson);
			} else {
				if (milkOrder <= totalMilkAvailable
						&& skinOrder <= totalSkinsAvailable) {
					jsonResponse.put(Constants.MILK, (double) milkOrder);
					jsonResponse.put(Constants.SKINS, skinOrder);
					response.setStatus(HttpServletResponse.SC_CREATED);
				} else if (milkOrder <= totalMilkAvailable) {
					jsonResponse.put(Constants.MILK, (double) milkOrder);
					response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
				} else if (skinOrder <= totalSkinsAvailable) {
					jsonResponse.put(Constants.SKINS, skinOrder);
					response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
				} else {
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				}
				ReadWriteJSON.writeJSON(Constants.ORDERS_FILE, jsonResponse);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return jsonResponse;
	}
}
