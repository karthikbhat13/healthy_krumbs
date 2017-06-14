package com.example.karthik.steps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.R.attr.tag;


public class MainActivity extends Activity {

    private long timestamp;

    private TextView textViewStepCounter;

    private TextView textViewStepDetector;


    private TextView test;

    private Button pedo;

    private Button pulse;


    private Thread detectorTimeStampUpdaterThread;

    private Handler handler;

    private boolean isRunning = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        pedo = (Button)findViewById(R.id.pedoB);

        pedo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,pedoMeter.class);
                startActivity(i);
            }
        });
        pulse = (Button)findViewById(R.id.pulse);
        pulse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this , pulseRate.class);
                startActivity(in);
            }
        });
    }



    @Override
    protected void onPause() {
        super.onPause();
    }

}