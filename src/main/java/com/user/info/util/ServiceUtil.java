package com.user.info.util;


public class ServiceUtil {

	public static String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static String DEFAULT_DISTANCE_UNIT =  "MILE";
    public static float LONDON_LATITUDE = 51.508530f;
    public static float LONDON_LONGTITUDE = -0.076132f;
	
	public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
		  double theta = lon1 - lon2;
		  double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		  dist = Math.acos(dist);
		  dist = rad2deg(dist);
		  dist = dist * 60 * 1.1515;
		  if (unit.equalsIgnoreCase("Km") || unit.equalsIgnoreCase("Kilometer")) {
			  dist = dist * 1.609344;
		  }else if (unit.equalsIgnoreCase("Meter")) {
			  dist = (dist  * 1.609344) * 1000;
		  }else {
			  dist = dist * 0.8684; // default by mile;
		    }
		  return dist;
		
		/*lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double earthRadius = 6371.01; //Kilometers
        return earthRadius * Math.acos(Math.sin(lat1)*Math.sin(lat2) + Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon1 - lon2));
	*/
	}
	
      private static double deg2rad(double deg) {
	  return (deg * Math.PI / 180.0);
      }
	
     private static  double rad2deg(double rad) {
	  return (rad * 180.0 / Math.PI);
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
