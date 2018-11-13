package com.example.mahfuz.asynctaskexample;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView countTv;
    private Button startBt, stopBt, asyncBt;

    Handler handler;

    boolean bool;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countTv = findViewById(R.id.countTv);
        startBt = findViewById(R.id.startBt);
        stopBt = findViewById(R.id.stopBt);
        asyncBt = findViewById(R.id.asyncBt);

        //handler = new Handler(getApplicationContext().getMainLooper());

        bool = true;

        startBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool = true;
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        while (bool) {
                            try {
                                Thread.sleep(1000);
                                i++;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Log.d("Thred id:", Thread.currentThread().getId()+"");

                            countTv.post(new Runnable() {
                                @Override
                                public void run() {
                                    countTv.setText(i+"");
                                }
                            });

                            /*handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    countTv.setText(i+"");
                                }
                            });*/

                        }
                    }
                };

                new Thread(runnable).start();
            }
        });

        stopBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool = false;
            }
        });


        asyncBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent asyncIntent = new Intent(MainActivity.this, AsyncTaskActivity.class);
                startActivity(asyncIntent);
            }
        });

    }



}
