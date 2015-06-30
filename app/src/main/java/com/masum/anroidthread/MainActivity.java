package com.masum.anroidthread;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.masum.Thread.HandlerMessagesActivity;
import com.masum.Thread.HandlerRunnableActivity;
import com.masum.asyncTask.MyAsyncTask;
import com.masum.indentService.MyIntentService;
import com.masum.pendingIntent.PendingIntendActivity;


public class MainActivity extends ActionBarActivity {

    public static final String TAG = "masum";
    BroadcastReceiver dynamicBR;
    static final String brAction = "mybr";
    LinearLayout myLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        makeDynamicBroadcastReceiver();
        myLayout = (LinearLayout) findViewById(R.id.main_layout);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    // for test send broadcast from adb command: am broadcast -a mybr -e color "#ff0000"
    public void makeDynamicBroadcastReceiver() {
        dynamicBR = new BroadcastReceiver() {
            @Override
            public void onReceive(Context ctx, Intent data) {
                //Read the color out of the extras
                String hexColor = data.getStringExtra("color");
                Toast.makeText(ctx, "Changing Color", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Color Received = " + hexColor);
                if (null != hexColor) {
                    //Set the background color
                    myLayout.setBackgroundColor(Color.parseColor(hexColor));
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter(brAction);
        registerReceiver(dynamicBR, intentFilter);
    }

    public void btnStartPendingActivity(View view) {
        Intent intent = new Intent(this, PendingIntendActivity.class);
        startActivity(intent);
    }

    public void onAsyncTaskClick(View view) {
        Log.i(TAG, "Async btn clicked");
        MyAsyncTask asyncTask = new MyAsyncTask(this);
        asyncTask.execute("my input");
    }

    public void onIntentServiceClick(View view) {
        Intent intent = new Intent(this, MyIntentService.class);
        startService(intent);

    }


    public void onThreadByRunableClick(View view) {
        Intent intent = new Intent(this, HandlerRunnableActivity.class);
        startActivity(intent);
    }

    public void onThreadByMsgClick(View view) {
        Intent inent2 = new Intent(getApplicationContext(), HandlerMessagesActivity.class);
        startActivity(inent2);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onThreadClick(View view) {
        Thread thread = new Thread(new LongRunningTask());
        thread.start();
       }

    public  Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 0: {
                    long threadId = Thread.currentThread().getId();
                    Log.i(MainActivity.TAG, "case 0: Mian threadid: " + threadId + " " + msg.obj.toString());
                    break;
                }
                case 1: {
                    long threadId = Thread.currentThread().getId();
                    Log.i(MainActivity.TAG, "case 1: Mian threadid: " + threadId + " " + msg.obj.toString());
                    break;
                }
                case 2: {
                    long threadId = Thread.currentThread().getId();
                    Log.i(MainActivity.TAG, "case 2: Mian threadid: " + threadId + " " + msg.obj.toString());
                    break;
                }
            }
        }
    };


    public class LongRunningTask implements Runnable {
        @Override
        public void run() {
            LogTask();
        }
    }

    public void LogTask(){
        int count = 0;
        while (count < 3) {
            Message msg = handler.obtainMessage(count, "this is msg + arg: " + count);
            handler.sendMessage(msg);
            long threadId = Thread.currentThread().getId();
            Log.i(MainActivity.TAG, "LongTask threadid: " + threadId);
            count++;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.i(MainActivity.TAG, "Worker thread is ended!");
    }

}

