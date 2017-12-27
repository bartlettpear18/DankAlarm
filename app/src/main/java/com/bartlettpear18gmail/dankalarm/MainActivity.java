package com.bartlettpear18gmail.dankalarm;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;

import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String tag = "debug";

    private TimePicker timeInput;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private AudioManager audioManager;

    private final double MILL_PER_DAY = 86400000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeInput = (TimePicker) findViewById(R.id.timeInput);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);


        Intent intent = new Intent(this, Alarm.class);
        pendingIntent = PendingIntent.getActivity(this, 12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }


    public void setAlarm(View view) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            Calendar calendar = Calendar.getInstance();
            long currentTime = calendar.getTimeInMillis();
            Log.d(tag, "Current time at button press: " + currentTime);

            calendar.set(Calendar.HOUR_OF_DAY, timeInput.getHour());
            calendar.set(Calendar.MINUTE, timeInput.getMinute());
            long alarmTime = calendar.getTimeInMillis();
            Log.d(tag, "Alarm time at button press: " + alarmTime);

            if(currentTime > alarmTime) {
                Log.d(tag, "Alarm set before current time, moving forward 24 hrs");
                alarmTime += MILL_PER_DAY;
                calendar.set(Calendar.MILLISECOND, (int) alarmTime);
                Log.d(tag, "Time difference: " + (alarmTime - currentTime));
                Log.d(tag, "Transformed Alarm Time: " + alarmTime);
            }

            alarmManager.set(AlarmManager.RTC, alarmTime, pendingIntent);

            NotificationManager n = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            if(n.isNotificationPolicyAccessGranted()) {
                audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

                Context context = getApplicationContext();
                String text = "Alarm set for: " + calendar.getTime();
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            }else{
                // Ask the user to grant access
                Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                startActivity(intent);
            }


        }
    }

    public void cancelAlarm(View view) {
        alarmManager.cancel(pendingIntent);

        int volume = audioManager.getStreamVolume(AudioManager.STREAM_RING);
        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        audioManager.setStreamVolume(AudioManager.STREAM_RING, volume, AudioManager.FLAG_SHOW_UI + AudioManager.FLAG_PLAY_SOUND);

        Context context = getApplicationContext();
        String text = "Alarm canceled";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }

}

