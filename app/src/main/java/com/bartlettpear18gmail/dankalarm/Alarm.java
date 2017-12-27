package com.bartlettpear18gmail.dankalarm;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

public class Alarm extends AppCompatActivity {

    private String tag = "debug";
    private AudioManager audioManager;
    private NotificationManager notificationManager;
    private Uri notification;
    private Ringtone ring;

    private TextView question;
    private EditText answerInput;
    private Integer attempt;
    private String problem;
    private int answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        Log.d(tag, "Alarm activity started");


        question = (TextView)findViewById(R.id.problem);
        answerInput = (EditText) findViewById(R.id.answerInput);
        makeQuestion();

        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

        int volume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING)/2; //audioManager.getStreamVolume(AudioManager.STREAM_ALARM);
        Log.d(tag, "Volume : " + volume);

        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        audioManager.setStreamVolume(AudioManager.STREAM_RING, volume, AudioManager.FLAG_SHOW_UI + AudioManager.FLAG_PLAY_SOUND);

        notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ring = RingtoneManager.getRingtone(getApplicationContext(), notification);
        ring.play();
    }

    public void dismiss(View view) {
        Log.d(tag, "Checkpoint");
        String input = answerInput.getText().toString();
        Log.d(tag, "Input: " + input);

        attempt = Integer.parseInt(input);
        Log.d(tag, "Attempt: " + attempt);

        if(attempt.equals(answer)) {
            ring.stop();
            Log.d(tag, "Alarm dismissed");
        } else {
            TextView title = (TextView) findViewById(R.id.title);
            title.setText("Try again");
        }

    }

    /**
     * Create a math problem and display on activity
     */
    private void makeQuestion() {
        Random rand = new Random();
        int b = rand.nextInt(10);
        int a = rand.nextInt(10);
        int operator = rand.nextInt(10);

        char op;

        if(operator <= 2) {
            op = '+';
            answer = a + b;
        } else if (operator > 2 && operator <= 5) {
            op = '-';
            answer = a - b;
        } else if (operator > 5 && operator <= 7) {
            op = 'x';
            answer = a * b;
        } else {
            op = '/';
            answer = a/b;
        }

        problem = "What is " + a + op + b + "?";
        question.setText(problem);
    }


}
