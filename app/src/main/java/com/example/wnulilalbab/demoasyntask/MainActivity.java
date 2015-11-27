package com.example.wnulilalbab.demoasyntask;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    volatile boolean runningStatus = false;
    volatile int number = 0;

    volatile Integer milisecond = 0;
    volatile Integer second = 0;
    volatile Integer minute = 0;
    volatile Integer hour = 0;

    TextView viewNumber;
    Button viewButton;

    ListView listTime;
    ArrayAdapter listAdapter = null;
    Integer collectNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewNumber = (TextView) findViewById(R.id.number);
        viewButton = (Button) findViewById(R.id.button);

        listTime = (ListView) findViewById(R.id.listView);
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        listTime.setAdapter(listAdapter);


    }

    public void collectTimeButtonClick(View view){
        listAdapter.add("["+ Integer.toString(collectNumber)+"] "+String.format("%02d", hour)+":"+String.format("%02d", minute)+":"
                +String.format("%02d", second)+":"+String.format("%02d", milisecond));

        collectNumber++;
        listTime.setSelection(listAdapter.getCount() - 1);
    }

    public void startStopButtonClick(View view){

        if (!runningStatus){
            runningStatus = true;
            new WatchTask().execute();
            viewButton.setText("Stop");
        }else{
            runningStatus = false;
            viewButton.setText("Start");
        }

    }

    private class WatchTask extends AsyncTask<Void, String, Void>{

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            while (runningStatus){
                //number++;

                //stopwach counter
                milisecond++;

                if (milisecond == 61){
                    second++;
                    milisecond = 0;
                }

                if (second == 61){
                    minute++;
                    second = 0;
                }

                if (minute == 61){
                    hour++;
                    minute = 0;
                }

                final String displayTime = String.format("%02d", hour)+":"+String.format("%02d", minute)+":"
                        +String.format("%02d", second)+":"+String.format("%02d", milisecond);

                publishProgress(displayTime);

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            viewNumber.setText(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //super.onPostExecute(aVoid);
        }
    }
}
