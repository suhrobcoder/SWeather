package uz.suhrob.sweather.api;

import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import uz.suhrob.sweather.ApiInterface;
import uz.suhrob.sweather.models.Coordinate;
import uz.suhrob.sweather.models.CurrentWeather;
import uz.suhrob.sweather.models.FiveDayForecast;

/**
 * Created by User on 13.01.2020.
 */

public class ApiHelper {

    private final String appId = "354dff605bee0df435967ed8f2c8100e";
    public static final String imgUrl = "http://openweathermap.org/img/wn/%s@2x.png";

    public void getCurrentWeatherByCity(String city, final ApiInterface apiInterface) {
        RequestParams params = new RequestParams();
        params.add("q", city);
        params.add("units", "metric");
        params.add("appid", appId);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://api.openweathermap.org/data/2.5/weather", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                apiInterface.onFailure();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                apiInterface.currentWeatherLoaded(new Gson().fromJson(responseString, CurrentWeather.class));
            }
        });
    }

    public void getCurrentWeatherById(int id, final ApiInterface apiInterface) {
        RequestParams params = new RequestParams();
        params.add("id", String.valueOf(id));
        params.add("units", "metric");
        params.add("appid", appId);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://api.openweathermap.org/data/2.5/weather", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                apiInterface.onFailure();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                apiInterface.currentWeatherLoaded(new Gson().fromJson(responseString, CurrentWeather.class));
            }
        });
    }

    public void getCurrentWeatherByCoord(Coordinate coord, final ApiInterface apiInterface) {
        RequestParams params = new RequestParams();
        params.add("lon", String.valueOf(coord.getLon()));
        params.add("lat", String.valueOf(coord.getLat()));
        params.add("units", "metric");
        params.add("appid", appId);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://api.openweathermap.org/data/2.5/weather", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                apiInterface.onFailure();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("weather-api", responseString);

                apiInterface.currentWeatherLoaded(new Gson().fromJson(responseString, CurrentWeather.class));
            }
        });
    }

    public void getForecastByCity(String city, final ApiInterface apiInterface) {
        RequestParams params = new RequestParams();
        params.add("q", city);
        params.add("units", "metric");
        params.add("appid", appId);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://api.openweathermap.org/data/2.5/forecast", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                apiInterface.onFailure();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                apiInterface.forecastLoaded(new Gson().fromJson(responseString, FiveDayForecast.class));
            }
        });
    }

    public void getForecastById(int id, final ApiInterface apiInterface) {
        RequestParams params = new RequestParams();
        params.add("id", String.valueOf(id));
        params.add("units", "metric");
        params.add("appid", appId);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://api.openweathermap.org/data/2.5/forecast", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                apiInterface.onFailure();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                apiInterface.forecastLoaded(new Gson().fromJson(responseString, FiveDayForecast.class));
            }
        });
    }

}
