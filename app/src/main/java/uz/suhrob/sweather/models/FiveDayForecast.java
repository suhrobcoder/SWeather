package uz.suhrob.sweather.models;

import java.util.List;

/**
 * Created by User on 28.01.2020.
 */

public class FiveDayForecast {

    private String cod;
    private double message;
    private City city;
    private int cnt;
    private List<ForecastListItem> list;

    public FiveDayForecast(String cod, double message, City city, int cnt, List<ForecastListItem> list) {
        this.cod = cod;
        this.message = message;
        this.city = city;
        this.cnt = cnt;
        this.list = list;
    }

    public FiveDayForecast() {
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public List<ForecastListItem> getList() {
        return list;
    }

    public void setList(List<ForecastListItem> list) {
        this.list = list;
    }
}
