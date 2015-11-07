package kax.team;

/**
 * Created by zenbook on 7/11/15.
 */
public class Position {
    private double lat, longitude;

    public Position(double lat, double longitude) {
        this.lat = lat;
        this.longitude = longitude;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
