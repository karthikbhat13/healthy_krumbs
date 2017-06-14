package com.example.karthik.steps;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class pedoMeter extends AppCompatActivity {


    private long timestamp;

    private TextView steps;

    private TextView dist;

    private final float NOISE = (float) 2.0;

    private Thread detectorTimeStampUpdaterThread;

    private Handler handler;

    private boolean isRunning = true;

    private long lastStepTime;

    private double temp;

    private long pace;

//    private long[] lastList = {-1, -1, -1, -1};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedo_meter);

        steps = (TextView) findViewById( R.id.steps);

        dist = (TextView) findViewById( R.id.dist);

        registerForSensorEvents();
        setupDetectorTimestampUpdaterThread();



    }


    public long getPace(long tstamp,long lTime,double steps){
        long temp1 = tstamp - lTime;


    }


    public void registerForSensorEvents() {


        SensorManager sManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);



        // Step Counter
        sManager.registerListener(new SensorEventListener() {

                                      @Override
                                      public void onSensorChanged(SensorEvent event) {
                                          // Time is in nanoseconds, convert to millis
                                          temp = event.values[0];
                                          steps.setText(String.valueOf(temp));

                                          float temp1 = (float) (temp * 78) / (float) 100000;

                                          dist.setText(String.valueOf(temp1));

                                      }

                                      @Override
                                      public void onAccuracyChanged(Sensor sensor, int accuracy) {

                                      }
                                  }, sManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),
                SensorManager.SENSOR_DELAY_UI);



        sManager.registerListener(new SensorEventListener() {

                                      @Override
                                      public void onSensorChanged(SensorEvent event) {
                                          // Time is in nanoseconds, convert to millis
                                          long timestamp = event.timestamp * 1000;

                                          pace = getPace(timestamp,lastStepTime,temp);



                                      }

                                      @Override
                                      public void onAccuracyChanged(Sensor sensor, int accuracy) {

                                      }
                                  }, sManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR),
                SensorManager.SENSOR_DELAY_UI);
    }

    private void setupDetectorTimestampUpdaterThread() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

            }
        };

        detectorTimeStampUpdaterThread = new Thread() {
            @Override
            public void run() {
                while (isRunning) {
                    try {
                        Thread.sleep(5000);
                        handler.sendEmptyMessage(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        detectorTimeStampUpdaterThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRunning = false;
        detectorTimeStampUpdaterThread.interrupt();
    }


}