package uz.suhrob.sweather.models;

/**
 * Created by User on 12.01.2020.
 */

public class Coordinate {

    private double lon, lat;

    public Coordinate(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public Coordinate() {
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
