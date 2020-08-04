package com.user.info.util;

import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.GeodesicData;
import net.sf.geographiclib.GeodesicMask;

public class ServiceUtil {

	public static final int CONVERT_METER_TO_MILES = 1609;

	public static final int CONVERT_METER_TO_KM = 1000;

	public static String KM_UNIT = "KM";

	public static String METER_UNIT = "METER";

	public static double distance(double cityLatitude, double cityLongtitude, double userLat, double userLon,
			String unit) {
		GeodesicData g = Geodesic.WGS84.Inverse(cityLatitude, cityLongtitude, userLat, userLon, GeodesicMask.DISTANCE);

		if (unit.equalsIgnoreCase(KM_UNIT))
			return g.s12 / CONVERT_METER_TO_KM;

		if (unit.equalsIgnoreCase(METER_UNIT))
			return g.s12;

		// Default convert into Mile
		return g.s12 / CONVERT_METER_TO_MILES;
	}

	public static boolean isStringOnlyAlphabet(String str) {
		return ((str != null) && (!str.equals("")) && (str.matches("^[a-zA-Z]*$")));
	}

	public static String capitalize(final String city) {
		return Character.toUpperCase(city.charAt(0)) + city.substring(1).toLowerCase();
	}

}
