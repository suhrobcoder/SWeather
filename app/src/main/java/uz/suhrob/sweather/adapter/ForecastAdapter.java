package uz.suhrob.sweather.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Locale;

import uz.suhrob.sweather.R;
import uz.suhrob.sweather.api.ApiHelper;
import uz.suhrob.sweather.models.ForecastListViewItem;

/**
 * Created by User on 29.01.2020.
 */

public class ForecastAdapter extends ArrayAdapter<ForecastListViewItem> {

    private Context context;
    private ForecastListViewItem[] objects;
    private TextView forecastDate, forecastWeather, forecastTemp;
    private ImageView forecastImg;

    public ForecastAdapter(@NonNull Context context, @NonNull ForecastListViewItem[] objects) {
        super(context, R.layout.forecast_item, objects);
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.forecast_item, null, true);
        forecastDate = rowView.findViewById(R.id.forecast_item_date);
        forecastImg = rowView.findViewById(R.id.forecast_item_img);
        forecastWeather = rowView.findViewById(R.id.forecast_item_weather);
        forecastTemp = rowView.findViewById(R.id.forecast_item_temp);
        forecastWeather.setText(objects[position].getWeatherType());
        forecastTemp.setText(String.format(Locale.getDefault(), "%d℃", objects[position].getTemp()));
        Glide.with(context).load(String.format(Locale.getDefault(), ApiHelper.imgUrl, objects[position].getImg())).into(forecastImg);
        forecastDate.setText(objects[position].getDate());
        return rowView;
    }
}
