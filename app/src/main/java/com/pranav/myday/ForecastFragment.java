package com.pranav.myday;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.text.WordUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;


public class ForecastFragment extends Fragment {

    TextView selectCity, cityField, detailsField, currentTemperatureField, humidity_field, pressure_field, weatherIcon, updatedField;
    ProgressBar loader;
    Typeface weatherFont;
    String city = "Burnham, GB";
    String OPEN_WEATHER_MAP_API = "1c710a0d829d792924811be8d81eb340";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_forecast, container, false);
        loader = view.findViewById(R.id.loader);
        selectCity = view.findViewById(R.id.selectCity);
        cityField = view.findViewById(R.id.city_field);
        updatedField = view.findViewById(R.id.updated_field);
        detailsField = view.findViewById(R.id.details_field);
        currentTemperatureField = view.findViewById(R.id.current_temperature_field);
        humidity_field = view.findViewById(R.id.humidity_field);
        pressure_field = view.findViewById(R.id.pressure_field);
        weatherIcon = view.findViewById(R.id.weather_icon);
        weatherFont = Typeface.createFromAsset(requireContext().getAssets(), "fonts/weathericons-regular-webfont.ttf");
        weatherIcon.setTypeface(weatherFont);

        taskLoadUp(city);

        selectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireContext());
                alertDialog.setTitle("Change City");
                final EditText input = new EditText(requireContext());
                input.setText(city);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);

                alertDialog.setPositiveButton("Change",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                city = input.getText().toString();
                                taskLoadUp(city);
                            }
                        });
                alertDialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                alertDialog.show();
            }
        });
        return view;
    }

    public void taskLoadUp(String query) {
        if (Function.isNetworkAvailable(requireContext())) {
            DownloadWeather task = new DownloadWeather();
            task.execute(query);
        } else {
            Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }
    //@SuppressWarnings("StaticFieldLeak")
    class DownloadWeather extends AsyncTask< String, Void, String > {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loader.setVisibility(View.VISIBLE);

        }
        protected String doInBackground(String...args) {
            return Function.executeGet("http://api.openweathermap.org/data/2.5/weather?q=" + args[0] + "&units=metric&appid=" + OPEN_WEATHER_MAP_API);
        }


        @SuppressWarnings("all")
        @Override
        protected void onPostExecute(String xml) {
            try {
                JSONObject json = new JSONObject(xml);
                if (json != null) {
                    JSONObject details = json.getJSONArray("weather").getJSONObject(0);
                    JSONObject main = json.getJSONObject("main");
                    DateFormat df = DateFormat.getDateTimeInstance();

                    cityField.setText(WordUtils.capitalizeFully(json.getString("name").toUpperCase(Locale.US)) + ", " + json.getJSONObject("sys").getString("country"));
                    detailsField.setText(WordUtils.capitalizeFully(details.getString("description").toUpperCase(Locale.US)));
                    currentTemperatureField.setText(String.format("%.0f", main.getDouble("temp")) + "°");
                    humidity_field.setText("Humidity: " + main.getString("humidity") + "%");
                    pressure_field.setText("Pressure: " + main.getString("pressure") + " hPa");
                    updatedField.setText(df.format(new Date(json.getLong("dt") * 1000)));
                    weatherIcon.setText(Html.fromHtml(Function.setWeatherIcon(details.getInt("id"),
                            json.getJSONObject("sys").getLong("sunrise") * 1000,
                            json.getJSONObject("sys").getLong("sunset") * 1000)));

                    loader.setVisibility(View.GONE);

                }
            } catch (JSONException e) {
                Toast.makeText(getContext(), "Error, Check City", Toast.LENGTH_SHORT).show();
            }
        }
    }
}