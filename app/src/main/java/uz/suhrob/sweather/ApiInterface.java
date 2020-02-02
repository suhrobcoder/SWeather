package uz.suhrob.sweather;

import uz.suhrob.sweather.models.CurrentWeather;
import uz.suhrob.sweather.models.FiveDayForecast;

/**
 * Created by User on 27.01.2020.
 */

public interface ApiInterface {

    void currentWeatherLoaded(CurrentWeather weather);
    void onFailure();
    void forecastLoaded(FiveDayForecast forecast);

}
