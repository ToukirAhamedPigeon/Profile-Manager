package com.example.touki.profilemanager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.touki.profilemanager.MyService.MyLocalBinder;

public class MainActivity extends AppCompatActivity {

    MyService myService;
    boolean isBound=false;

    public void start(View view)
    {
        myService.onCreate();
        TextView myText=(TextView) findViewById(R.id.test);
        myText.setText("Service is Started");
    }
    public void stop(View view)
    {
        myService.onDestroy();
        TextView myText=(TextView) findViewById(R.id.test);
        myText.setText("Service is Stopped");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i= new Intent(this,MyService.class);
        bindService(i,myConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection myConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyLocalBinder binder=(MyLocalBinder) service;
            myService=binder.getService();
            isBound=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
           isBound=false;
        }
    };
}
