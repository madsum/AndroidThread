package com.masum.pendingIntent;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.masum.anroidthread.MainActivity;
import com.masum.anroidthread.R;

public class PendingIntendActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_intend);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void startAlert(View view) {
        EditText text = (EditText) findViewById(R.id.editText1);
        int time = Integer.parseInt(text.getText().toString());
        Intent intent = new Intent(this, MyReceiver.class);
        PendingIntent pend_intent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 0, intent, 0);
        //calls the alarm
        AlarmManager alarmManager = (AlarmManager) getSystemService(
                ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + (time * 1000), pend_intent);
        Log.i(MainActivity.TAG, "Pending intent will start in "+time+" secounds");
    }

    public  void startMainActivity(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        try {
            pi.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }

    }

    public void onStatrtPendingIntend(View view) {
        startAlert(view);
        startMainActivity();
    }
}
