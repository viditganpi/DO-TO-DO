package com.example.vidit.do_to_do;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class RegisterTask extends AppCompatActivity {
    private TimePicker timePicker;
    private Button button;
    private Button place_button;
    private Task task;
    private int hour;
    private int min;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));

    int PLACE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registertask);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        button = (Button) findViewById(R.id.button);
        place_button = (Button) findViewById(R.id.place_button);
        task = new Task();
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hour = timePicker.getCurrentHour();
                        min = timePicker.getCurrentMinute();
                        task.setHour(hour);
                        task.setMinute(min);
                        Toast.makeText(getBaseContext(),hour + ":" + min,Toast.LENGTH_SHORT).show();
                    }
                }
        );
        place_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                        intentBuilder.setLatLngBounds(BOUNDS_MOUNTAIN_VIEW);
                        Intent i;
                        try {
                            i = intentBuilder.build(RegisterTask.this);
                            startActivityForResult(i,PLACE_REQUEST);
                        } catch (GooglePlayServicesRepairableException e) {
                            e.printStackTrace();
                        } catch (GooglePlayServicesNotAvailableException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }
    protected void onActivityResult(int request_code,int result_code, Intent data){
        if(request_code==PLACE_REQUEST){
            if(result_code==RESULT_OK){
                Place place = PlacePicker.getPlace(this,data);
                String place_addr = place.toString();
                Toast.makeText(getBaseContext(),place_addr,Toast.LENGTH_LONG).show();
            }
        }
    }
}
