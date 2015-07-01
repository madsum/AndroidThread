package com.masum.indentService;

import android.content.Intent;
import android.util.Log;

import com.masum.anroidthread.MainActivity;

/**
 * Created by masum on 26/05/15.
 */
public class MyIntentService extends android.app.IntentService {

    public MyIntentService(){
        super("com.masum.indentService.MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        int count=0;
        while(true) {
            Log.i(MainActivity.TAG, "Intent Service is started!");
            count++;
            if(count == 5){
                Log.i(MainActivity.TAG, "Intent Service is ended!");
                sendDynamicBroadCast();

                break;
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendStaticBroadCast(){
        Log.i(MainActivity.TAG, "sendStaticBroadCast");
        Intent intent = new Intent();
        intent.setAction("abc");
        intent.addCategory("category");
        sendBroadcast(intent);
    }

    public void sendDynamicBroadCast(){
        Log.i(MainActivity.TAG, "sendDynamicBroadCast");
        Intent intentResponse = new Intent();
        intentResponse.setAction("xx");
        intentResponse.addCategory(Intent.CATEGORY_DEFAULT);
        intentResponse.putExtra(MainActivity.EXTRA_KEY_OUT, "important msg from intent service");
        sendBroadcast(intentResponse);
    }

}
