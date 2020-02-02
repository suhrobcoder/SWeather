package uz.suhrob.sweather.models;

/**
 * Created by User on 28.01.2020.
 */

public class City {

    private int id;
    private String name;
    private Coordinate coord;
    private String country;
    private long timezone;

    public City() {
    }

    public City(int id, String name, Coordinate coord, String country, long timezone) {

        this.id = id;
        this.name = name;
        this.coord = coord;
        this.country = country;
        this.timezone = timezone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinate getCoord() {
        return coord;
    }

    public void setCoord(Coordinate coord) {
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getTimezone() {
        return timezone;
    }

    public void setTimezone(long timezone) {
        this.timezone = timezone;
    }
}
