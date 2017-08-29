package com.example.touki.profilemanager;

import android.app.Service;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.media.AudioManager;
import android.os.Binder;
import android.os.IBinder;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import java.util.Locale;

public class MyService extends Service implements SensorEventListener{



    private SensorManager SM;
    private Sensor accelometerSensor;
    private Sensor lightSensor;
    private Sensor proximitySensor;
    private AudioManager audioManager;




    private  final IBinder myBinder=new MyLocalBinder();
    public MyService() {
    }

    public class MyLocalBinder extends Binder {

        MyService getService()
        {
            return MyService.this;
        }
    }
    @Override
    public void onCreate() {

        super.onCreate();
        SM=(SensorManager)getSystemService(SENSOR_SERVICE);
        accelometerSensor=SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lightSensor=SM.getDefaultSensor(Sensor.TYPE_LIGHT);
        proximitySensor=SM.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        audioManager=(AudioManager) getSystemService(getApplicationContext().AUDIO_SERVICE);
        SM.registerListener(this,accelometerSensor,SensorManager.SENSOR_DELAY_NORMAL);
        SM.registerListener(this,lightSensor,SensorManager.SENSOR_DELAY_NORMAL);
        SM.registerListener(this,proximitySensor,SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        SM.unregisterListener(this);
    }
    public void startService()
    {
        SM.registerListener(this,accelometerSensor,SensorManager.SENSOR_DELAY_NORMAL);
        SM.registerListener(this,lightSensor,SensorManager.SENSOR_DELAY_NORMAL);
        SM.registerListener(this,proximitySensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stopService()
    {
        SM.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor=event.sensor;
        if(sensor.getType()==Sensor.TYPE_ACCELEROMETER) {
            if (event.values[0] >= 0 && event.values[1] >= 0 && event.values[2] <= 0) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            }
        }
        else if(sensor.getType()==Sensor.TYPE_LIGHT)
        {
            if(event.values[0]<=0)
            {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            }

        }
        else if(sensor.getType()==Sensor.TYPE_PROXIMITY)
        {
            if(event.values[0]<=2) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    public IBinder onBind(Intent intent) {
      return myBinder;
    }
}
