package uz.suhrob.sweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import uz.suhrob.sweather.adapter.ForecastAdapter;
import uz.suhrob.sweather.api.ApiHelper;
import uz.suhrob.sweather.models.CurrentWeather;
import uz.suhrob.sweather.models.FiveDayForecast;
import uz.suhrob.sweather.models.ForecastListItem;
import uz.suhrob.sweather.models.ForecastListViewItem;
import uz.suhrob.sweather.storage.SharedPreferencesHelper;

public class MainActivity extends AppCompatActivity {

    TextView todayDate, weatherTypeText, todayTemp, cityName, todayPressure;
    ImageView weatherTypeImg;
    Button enterCityBtn;
    ListView listView;
    ProgressBar loadingProgressbar;
    LinearLayout todayContainer;
    String[] months = {"yanvar", "fevral", "mart", "aprel", "may", "iyun", "iyul", "avgust", "sentabr", "oktabr", "noyabr", "dekabr"};
    SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todayDate = findViewById(R.id.today_date);
        weatherTypeText = findViewById(R.id.weather_type_text);
        todayTemp = findViewById(R.id.today_temp);
        cityName = findViewById(R.id.city_name);
        todayPressure = findViewById(R.id.today_pressure);
        weatherTypeImg = findViewById(R.id.weather_type_img);
        enterCityBtn = findViewById(R.id.enter_city_btn);
        loadingProgressbar = findViewById(R.id.loading_progressbar);
        todayContainer = findViewById(R.id.today_container);

        sharedPreferencesHelper = new SharedPreferencesHelper(this);

        listView = findViewById(R.id.list_view);

        if (sharedPreferencesHelper.getCityId() != -1) {
            loadData();
        } else {
            todayContainer.setVisibility(View.INVISIBLE);
            enterCityBtn.setVisibility(View.VISIBLE);
            enterCityBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(new Intent(getApplicationContext(), CitySearchActivity.class), 1);
                }
            });
        }
    }

    public void loadData() {
        loadingProgressbar.setVisibility(View.VISIBLE);
        todayContainer.setVisibility(View.INVISIBLE);
        listView.setAdapter(new ForecastAdapter(getApplicationContext(), new ForecastListViewItem[]{}));
        ApiInterface apiInterface = new ApiInterface() {
            @Override
            public void currentWeatherLoaded(CurrentWeather weather) {
                Calendar calendar = Calendar.getInstance();
                todayDate.setText(String.format(Locale.getDefault(),"%d-%s", calendar.get(Calendar.DAY_OF_MONTH), months[calendar.get(Calendar.MONTH)]));
                weatherTypeText.setText(weather.getWeather().get(0).getMain());
                todayTemp.setText(String.format("%s℃", String.valueOf((int)weather.getMain().getTemp())));
                Glide.with(getApplicationContext()).load(String.format(Locale.getDefault(), ApiHelper.imgUrl, weather.getWeather().get(0).getIcon())).into(weatherTypeImg);
                cityName.setText(weather.getName());
                todayPressure.setText(String.format(Locale.getDefault(), "%d kPa", (int)weather.getMain().getPressure()));
                loadingProgressbar.setVisibility(View.GONE);
                todayContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure() {
                Toast.makeText(getApplicationContext(), "Xatolik", Toast.LENGTH_SHORT).show();
                loadingProgressbar.setVisibility(View.GONE);
            }

            @Override
            public void forecastLoaded(FiveDayForecast forecast) {
                List<ForecastListItem> forecastItems = forecast.getList();
                ForecastListViewItem[] forecasts = new ForecastListViewItem[forecastItems.size()];
                for (int i=0; i<forecastItems.size(); i++) {
                    forecasts[i] = new ForecastListViewItem();
                    forecasts[i].setImg(forecastItems.get(i).getWeather().get(0).getIcon());
                    forecasts[i].setTemp((int)forecastItems.get(i).getMain().getTemp());
                    forecasts[i].setWeatherType(forecastItems.get(i).getWeather().get(0).getMain());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(forecastItems.get(i).getDt()*1000);
                    forecasts[i].setDate(String.format(Locale.getDefault(), "%d-%s %d:%d", calendar.get(Calendar.DAY_OF_MONTH), months[calendar.get(Calendar.MONTH)], calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));
                }
                listView.setAdapter(new ForecastAdapter(getApplicationContext(), forecasts));
            }
        };
        ApiHelper apiHelper = new ApiHelper();
        apiHelper.getCurrentWeatherById(sharedPreferencesHelper.getCityId(), apiInterface);
        apiHelper.getForecastById(sharedPreferencesHelper.getCityId(), apiInterface);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                int id = data.getIntExtra("city_id", -1);
                if (id != -1) {
                    sharedPreferencesHelper.setCityId(id);
                    todayContainer.setVisibility(View.VISIBLE);
                    enterCityBtn.setVisibility(View.GONE);
                    loadData();
                } else {
                    todayContainer.setVisibility(View.INVISIBLE);
                    enterCityBtn.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.change_city_menu) {
            startActivityForResult(new Intent(getApplicationContext(), CitySearchActivity.class), 1);
        } else if (item.getItemId() == R.id.refresh_menu) {
            loadData();
        }
        return super.onOptionsItemSelected(item);
    }
}
