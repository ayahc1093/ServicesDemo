package com.example.mcberliner.servicesdemo;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mcberliner on 7/9/2015.
 */
public class MyService extends Service {

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {
            new DownloadTask().execute(new URL("http://www.example.com/downloads/file.pdf"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

    private class DownloadTask extends AsyncTask<URL, Integer, String> {

        @Override
        protected String doInBackground(URL... params) {
            for (int i = 1; i <= 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i * 10);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.d("DownloadTask", values[0] + "% downloaded");
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(getBaseContext(), "Download Complete", Toast.LENGTH_LONG).show();
            stopSelf();
        }
    }
}