package com.masum.anroidthread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;



public class ThreadActivity extends ActionBarActivity {

    public ProgressBar progressBar;

    private static MyThread sThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState==null){
            setContentView(R.layout.thread_activity);
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            showProgressDialog();
            sThread=new MyThread(mHandler);
            sThread.start();
        }
    }

    public void showProgressDialog(){
        progressBar.setVisibility(ProgressBar.VISIBLE);

    }

    public  void hideProgressDialog(){
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        if (sThread.isAlive()){
            showProgressDialog();
            sThread.setHandler(mHandler);
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (sThread.isAlive()){
            sThread.setHandler(null);
            hideProgressDialog();
        }
    }

    private Handler mHandler=new  Handler(){
        @Override
        public void handleMessage(Message message){
            //update UI
            Log.i(MainActivity.TAG, "MAIN therd id: "+Thread.currentThread().getId());
            hideProgressDialog();
        }
    };

/*
    public android.os.Handler handler = new android.os.Handler() {

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
*/
    private class MyThread extends Thread{
        private Handler mHandler;

        public MyThread(Handler handler){
            super();
            mHandler=handler;
        }

        @Override
        public void run(){
            //some long operation
            if (mHandler!=null){
                Log.i(MainActivity.TAG, "XX therd id: "+Thread.currentThread().getId());
                mHandler.sendEmptyMessage(0);
            }
              //  mHandler.sendEmptyMessage(0);
        }

        public void setHandler(Handler handler){
            mHandler=handler;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_thread, menu);
        return true;
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
}
