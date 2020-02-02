package uz.suhrob.sweather;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import uz.suhrob.sweather.api.ApiHelper;
import uz.suhrob.sweather.models.City;
import uz.suhrob.sweather.models.Coordinate;
import uz.suhrob.sweather.models.CurrentWeather;
import uz.suhrob.sweather.models.FiveDayForecast;

public class CitySearchActivity extends AppCompatActivity implements LocationListener {

    List<City> cityList;
    ListView listView;
    ProgressBar progressBar;
    SearchView searchView;
    Button searchByLocationBtn;
    LocationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_search);

        cityList = new ArrayList<>();
        List<String> cityNames = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("citylist"), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] line1 = line.split("[:]");
                City city = new City();
                city.setId(Integer.parseInt(line1[0]));
                city.setName(line1[1]);
                cityList.add(city);
                cityNames.add(line1[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        listView = findViewById(R.id.search_city_list);
        searchView = findViewById(R.id.search_view);
        searchByLocationBtn = findViewById(R.id.search_by_location_btn);
        progressBar = findViewById(R.id.location_progressbar);

        final ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, cityNames);
        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = getIntent();
                intent.putExtra("city_id", cityList.get(position).getId());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        searchByLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    progressBar.setVisibility(View.VISIBLE);
                    manager = (LocationManager) getSystemService(LOCATION_SERVICE);
                    manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, CitySearchActivity.this);
                } else {
                    requestPermission();
                }
            }
        });

    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(CitySearchActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                searchByLocationBtn.callOnClick();
            } else {
                Toast.makeText(this, "Siz ruxsat bermadingiz!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        progressBar.setVisibility(View.GONE);
        manager.removeUpdates(new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
        new ApiHelper().getCurrentWeatherByCoord(new Coordinate(location.getLongitude(), location.getLatitude()), new ApiInterface() {
            @Override
            public void currentWeatherLoaded(CurrentWeather weather) {
                Intent intent = getIntent();
                intent.putExtra("city_id", (int)weather.getId());
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onFailure() {

            }

            @Override
            public void forecastLoaded(FiveDayForecast forecast) {

            }
        });
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
