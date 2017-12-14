package com.bartlettpear18gmail.dankalarm;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import java.util.Calendar;
import java.util.Date;

import android.os.SystemClock;
import android.provider.AlarmClock;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {

    private String tag = "debug";

    private TimePicker timeInput;
    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeInput = (TimePicker) findViewById(R.id.timeInput);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }


    public void setAlarm(View view) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, timeInput.getHour());
            calendar.set(Calendar.MINUTE, timeInput.getMinute());
//            calendar.add(Calendar.SECOND, 2);

            Intent intent = new Intent(this, Alarm.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
        }
    }

}

