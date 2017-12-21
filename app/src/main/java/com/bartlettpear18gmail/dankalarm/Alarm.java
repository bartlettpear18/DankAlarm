package com.bartlettpear18gmail.dankalarm;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Alarm extends AppCompatActivity {

    private String tag = "debug";

    private NotificationManager notificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        Log.d(tag, "Alarm activity started");

        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);


    }
}
