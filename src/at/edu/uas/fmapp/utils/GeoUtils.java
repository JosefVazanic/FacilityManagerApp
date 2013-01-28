package at.edu.uas.fmapp.utils;

import android.location.Location;

import com.mapquest.android.maps.GeoPoint;

public class GeoUtils {

	/** Radius of the earth in kilometers. */
	public static final double EARTH_RADIUS = 6367;

	public static final double ONE_MILLION = 1E6;

	private static final int TWO_MINUTES = 1000 * 60 * 2;

	/**
	 * Calculate haversine distance for linear distance.
	 * 
	 * @param latitude1
	 *            latitude coordinate for point 1
	 * @param longitude1
	 *            longitude coordinate for point 1
	 * @param latitude2
	 *            latitude coordinate for point 2
	 * @param longitude2
	 *            longitude coordinate for point 2
	 * 
	 * @return haversine distance in kilometers between the passed points
	 */
	public static double calculateHaversineDistance(double latitude1,
			double longitude1, double latitude2, double longitude2) {

		double deltaLongitude = (longitude2 - longitude1) * (Math.PI / 180);
		double deltaLatitude = (latitude2 - latitude1) * (Math.PI / 180);

		double result = Math.pow(Math.sin(deltaLatitude / 2.0), 2)
				+ Math.cos(latitude1 * (Math.PI / 180))
				* Math.cos(latitude2 * (Math.PI / 180))
				* Math.pow(Math.sin(deltaLongitude / 2.0), 2);

		result = 2 * Math.atan2(Math.sqrt(result), Math.sqrt(1 - result));
		result = EARTH_RADIUS * result;

		return result;
	}

	/**
	 * Determines whether one Location reading is better than the current
	 * Location fix
	 * 
	 * @param location
	 *            The new Location that you want to evaluate
	 * @param currentBestLocation
	 *            The current Location fix, to which you want to compare the new
	 *            one
	 */
	public static boolean isBetterLocation(Location location,
			Location currentBestLocation) {
		if (currentBestLocation == null) {
			// A new location is always better than no location
			return true;
		} else if (location == null) {
			// The old location is always better than no location
			return false;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = location.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
		boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use
		// the new location
		// because the user has likely moved
		if (isSignificantlyNewer) {
			return true;
			// If the new location is more than two minutes older, it must be
			// worse
		} else if (isSignificantlyOlder) {
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation
				.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(location.getProvider(),
				currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and
		// accuracy
		if (isMoreAccurate) {
			return true;
		} else if (isNewer && !isLessAccurate) {
			return true;
		} else if (isNewer && !isSignificantlyLessAccurate
				&& isFromSameProvider) {
			return true;
		}
		return false;
	}

	/** Checks whether two providers are the same */
	private static boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}

	/**
	 * Converts a coordinate (latitude or longitude) from its integer
	 * representation to its float representation.
	 * 
	 * 48239573 -> 48.239573
	 * 
	 * @param coordinate
	 * @return
	 */
	public static double convertCoordinateToFloat(double coordinate) {
		return coordinate / ONE_MILLION;
	}
	
	/**
	 * Converts a geo point to a string representation:
	 * 48.239573,16.345344
	 * 
	 * @param geoPoint
	 * @return
	 */
	public static String getCoordinateStringFromGeoPoint(GeoPoint geoPoint) {
		return Double.toString(geoPoint.getLatitude()) + ","
				+ Double.toString(geoPoint.getLongitude());
	}
}
