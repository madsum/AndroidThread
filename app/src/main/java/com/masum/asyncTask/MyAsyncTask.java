package com.masum.asyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.masum.anroidthread.MainActivity;

/**
 * Created by masum on 26/05/15.
 */
public class MyAsyncTask extends AsyncTask<String, Integer, String> {

    ProgressDialog progressDialog;
    String input_param = null;
    int count = 0;

    public MyAsyncTask(Context ctx) {
        progressDialog = new ProgressDialog(ctx);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgress(10);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.i(MainActivity.TAG, "Long task onPreExecute");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        input_param = params[0];
        return doFakeLongTask();
    }


    private String doFakeLongTask() {


        while (true) {
            Log.i(MainActivity.TAG, "Long work is started!");
            count++;
            publishProgress(count);
            if (count == 10) {
                Log.i(MainActivity.TAG, "Long work is ended!");
                break;
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return Integer.toString(count);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressDialog.incrementProgressBy(values[0]);
        Log.i(MainActivity.TAG, "Long task onProgressUpdate");
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        count = 0;
        Log.i(MainActivity.TAG, "Long task onPostExecute");
    }
}
