package com.user.info.util;



import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.GeodesicData;
import net.sf.geographiclib.GeodesicMask;

public class ServiceUtil {

	public static String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static String DEFAULT_DISTANCE_UNIT =  "MILE";
    
	
	public static double distance(double cityLatitude, double cityLongtitude, double userLat, double userLon, String unit) {
		GeodesicData g = Geodesic.WGS84.Inverse(cityLatitude, cityLongtitude, userLat,
				userLon,
                GeodesicMask.DISTANCE);
		
		if(unit.equalsIgnoreCase("km") || unit.equalsIgnoreCase("kilometer"))
		  return g.s12/1000;
		
		if(unit.equalsIgnoreCase("meter"))
			 return g.s12;
		
		//Default convert into Mile
		 return g.s12/1609;
	}
	
	
    public static boolean isStringOnlyAlphabet(String str) 
    { 
        return ((str != null) 
                && (!str.equals("")) 
                && (str.matches("^[a-zA-Z]*$"))); 
    } 
    
    
    public static String capitalize(final String city) {
	return Character.toUpperCase(city.charAt(0)) + city.substring(1).toLowerCase();
    }
	
}
