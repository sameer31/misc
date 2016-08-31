package com.yakshop.app;

import org.junit.Test;
import org.junit.Assert;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.yakshop.utility.Constants;

public class YakServiceTest {

	YakService yakService = new YakService();
	
	@SuppressWarnings("unchecked")
	private JSONArray createTestData(){
		JSONArray jsonArray = new JSONArray();

		JSONObject jsonObject1 = new JSONObject();
		JSONObject jsonObject2 = new JSONObject();
		JSONObject jsonObject3 = new JSONObject();

		jsonObject1.put(Constants.AGE, 400.0);
		jsonObject2.put(Constants.AGE, 800.0);
		jsonObject3.put(Constants.AGE, 950.0);
		
		jsonArray.add(jsonObject1);
		jsonArray.add(jsonObject2);
		jsonArray.add(jsonObject3);
		
		return jsonArray;
	}
	
	@Test
	public void testGetTotalMilkProducedSuccess() {
		JSONArray herdArray = createTestData();
		double milkProduced = yakService.getTotalMilkProduced(herdArray, 10);
		Assert.assertEquals(milkProduced, 850.05, 0.00);
	}

	@Test
	public void testGetTotalSkinsProducedSuccess() {
		JSONArray herdArray = createTestData();
		long skinsProduced = yakService.getTotalSkinsProduced(herdArray, 20);
		Assert.assertEquals(skinsProduced, 6, 0.00);
	}

}
