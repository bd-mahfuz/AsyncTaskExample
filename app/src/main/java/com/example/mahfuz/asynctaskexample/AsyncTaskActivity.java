package com.example.mahfuz.asynctaskexample;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AsyncTaskActivity extends AppCompatActivity {

    private TextView countTv;
    private Button startBt, stopBt;

    Handler handler;

    boolean bool;
    int counter;
    MyAsyncTask asyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);

        countTv = findViewById(R.id.countTva);
        startBt = findViewById(R.id.startBta);
        stopBt = findViewById(R.id.stopBta);

        bool = true;

        startBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bool = true;
                asyncTask = new MyAsyncTask();
                asyncTask.execute(counter);

            }
        });

        stopBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // bool = false;

                //another way to stop async task
                asyncTask.cancel(true); // in this time onPostExecuteMethod wont work
            }
        });



    }

    class MyAsyncTask extends AsyncTask<Integer, Integer, Integer> {

        int customCounter;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customCounter = 0;
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            customCounter = integers[0];
            while(bool) {
                try {
                    Thread.sleep(1000);
                    customCounter ++;
                    publishProgress(customCounter);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (isCancelled()) {
                    break;
                }

            }
            return customCounter;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            countTv.setText(integer+"");
            counter = integer;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            countTv.setText(""+values[0]);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
