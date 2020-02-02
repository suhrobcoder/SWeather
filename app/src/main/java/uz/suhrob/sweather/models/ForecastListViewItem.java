package uz.suhrob.sweather.models;

/**
 * Created by User on 29.01.2020.
 */

public class ForecastListViewItem {

    private String img;
    private String date;
    private String weatherType;
    private int temp;

    public ForecastListViewItem() {
    }

    public ForecastListViewItem(String img, String date, String weatherType, int temp) {

        this.img = img;
        this.date = date;
        this.weatherType = weatherType;
        this.temp = temp;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(String weatherType) {
        this.weatherType = weatherType;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }
}
