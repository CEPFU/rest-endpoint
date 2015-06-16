package de.fu_berlin.agdb.crepe.json.util;

/**
 * Simple latitude/longitude point
 * @author Simon Kalt
 */
public class SimplePoint {
    private double latitude;
    private double longitude;

    public SimplePoint(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
