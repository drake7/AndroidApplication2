package com.example.threaddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ThemedSpinnerAdapter;

import com.example.threaddemo.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    Future<String> future;
    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg)
        {
            Bundle bundle = msg.getData();
            String message = bundle.getString("myKey");
            binding.myTextView.setText(message);
        }
    } ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    public void buttonClick(View view) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                long endTime = System.currentTimeMillis() + 5 * 1000;

                while (System.currentTimeMillis() < endTime) {
                    synchronized (this) {
                        try {
                            wait(endTime - System.currentTimeMillis());
                        } catch (Exception e) {
                        }
                    }
                }
                return ("Task Completed");
            }
        });
    executorService.shutdown();
    }

    public void statusClick(View view) {

        if(future.isDone())
        {
            String result = null;
            try{
                result = future.get(3, TimeUnit.SECONDS);
            }
            catch(ExecutionException |  InterruptedException | TimeoutException e)
            {
                e.printStackTrace();
            }
                binding.myTextView.setText(result);
        }
        else
        {
            binding.myTextView.setText("waiting");
        }
    }
}