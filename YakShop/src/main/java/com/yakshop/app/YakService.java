package com.yakshop.app;

import java.text.DecimalFormat;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.yakshop.utility.Constants;

/**
 * @author Sunny Mourya
 * 
 *         This class contains utility methods which help the REST WebServices
 *
 */
@Service
public class YakService {

	/**
	 * @param herdArray
	 * @param totalDays
	 * @return double
	 * 
	 *         This method provides a view of total milk available in the stock
	 *         at any particular day in YakShop
	 */
	public double getTotalMilkProduced(JSONArray herdArray, int totalDays) {
		double totalMilkAvailable = 0;
		DecimalFormat df = new DecimalFormat("#.##");
		for (int i = 0; i < herdArray.size(); i++) {
			JSONObject yak = (JSONObject) herdArray.get(i);
			double currentAge = (Double) yak.get(Constants.AGE);
			for (int day = 1; day <= totalDays; day++) {
				double yakAge = currentAge + day;
				if (yakAge <= Constants.YAK_DEATH_AGE) {
					totalMilkAvailable = totalMilkAvailable
							+ (50 - ((yakAge) * 0.03));
				}
			}
		}
		return Double.valueOf(df.format(totalMilkAvailable));
	}

	/**
	 * @param herdArray
	 * @param totalDays
	 * @return long
	 * 
	 *         This method provides a view of total skins available in the stock
	 *         at any particular day in YakShop
	 */
	public long getTotalSkinsProduced(JSONArray herdArray, int totalDays) {
		long initialSkins = herdArray.size();
		long totalSkinsAvailable = initialSkins;
		for (int i = 0; i < herdArray.size(); i++) {
			JSONObject yak = (JSONObject) herdArray.get(i);
			double currentAge = (Double) yak.get(Constants.AGE);
			int loopCount = 0;
			if (totalDays > 100) {
				loopCount = totalDays / 100;
			}
			for (int count = 0; count <= loopCount; count++) {
				double yakAge = currentAge + count * 100;
				int revisedTotalDays = 0;
				if (count < loopCount) {
					revisedTotalDays = 100;
				} else {
					revisedTotalDays = totalDays - count * 100;
				}
				if (yakAge >= Constants.YAK_SHAVING_AGE
						&& (yakAge + revisedTotalDays) <= Constants.YAK_DEATH_AGE) {
					double shavingDayGap = Math
							.round((8 + (yakAge * 0.01)) + 1);
					totalSkinsAvailable = totalSkinsAvailable
							+ (int) ((int) revisedTotalDays / shavingDayGap);
				} else if (yakAge >= Constants.YAK_SHAVING_AGE
						&& (yakAge + revisedTotalDays) > Constants.YAK_DEATH_AGE) {
					revisedTotalDays = (int) ((yakAge + revisedTotalDays) - Constants.YAK_DEATH_AGE);
					double shavingDayGap = Math
							.round((8 + (yakAge * 0.01)) + 1);
					totalSkinsAvailable = totalSkinsAvailable
							+ (int) ((int) revisedTotalDays / shavingDayGap);
					break;
				}

			}
		}
		return totalSkinsAvailable;
	}
}
