package kr.jm.utils.helper;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Jm distance.
 */
public class JMDistance {

    private final double AVERAGE_RADIUS_OF_EARTH_KM;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static JMDistance getInstance() {
        return JMDistance.LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final JMDistance INSTANCE = new JMDistance();
    }

    /**
     * Instantiates a new Jm distance.
     */
    public JMDistance() {
        this.AVERAGE_RADIUS_OF_EARTH_KM = 6371.0087714D;
    }

    /**
     * Cal kilometer double.
     *
     * @param latitude   the latitude
     * @param longitude  the longitude
     * @param latitude2  the latitude 2
     * @param longitude2 the longitude 2
     * @return the double
     */
    public double calKilometer(double latitude, double longitude, double latitude2,
            double longitude2) {
        return calKilometer(catDistance(latitude, latitude2, Math.toRadians(latitude - latitude2),
                Math.toRadians(longitude - longitude2)));
    }

    private double calKilometer(double a) {
        return AVERAGE_RADIUS_OF_EARTH_KM * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }

    private double catDistance(double latitude, double latitude2, double latDistance, double lngDistance) {
        return Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(latitude2))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);
    }

    /**
     * Nearest key distance by km map list.
     *
     * @param <K>            the type parameter
     * @param latLonEntryMap the lat lon entry map
     * @param latitude       the latitude
     * @param longitude      the longitude
     * @param radiusByKm     the radius by km
     * @param size           the size
     * @return the list
     */
    public <K> List<Map.Entry<K, Double>> nearestKeyDistanceByKmMap(Map<K, Map.Entry<Double, Double>> latLonEntryMap,
            double latitude, double longitude, float radiusByKm, int size) {
        return latLonEntryMap.entrySet().stream().map(entry -> Map.entry(entry.getKey(),
                        calKilometer(latitude, longitude, entry.getValue().getKey(), entry.getValue().getValue())))
                .filter(entry -> entry.getValue() <= radiusByKm).sorted(Comparator.comparingDouble(Map.Entry::getValue))
                .limit(size < 1 ? latLonEntryMap.size() : size).collect(Collectors.toList());
    }


}
