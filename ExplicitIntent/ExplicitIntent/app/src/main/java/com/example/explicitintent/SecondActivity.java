package com.example.explicitintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.explicitintent.databinding.ActivityMainBinding;
import com.example.explicitintent.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    private ActivitySecondBinding binding2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding2 = ActivitySecondBinding.inflate(getLayoutInflater());
        View view = binding2.getRoot();
        setContentView(view);

        Bundle extra = getIntent().getExtras();
        if(extra == null)
        {
            return;
        }

        String qString = extra.getString("qString");
        binding2.textView2.setText(qString);
    }

    public void returnText(View view) {
        finish();
    }

    @Override
    public void finish() {

        Intent data = new Intent();
        String returnString = binding2.editText2.getText().toString();
        data.putExtra("returnData",returnString);
        setResult(RESULT_OK,data);

        super.finish();
    }
}