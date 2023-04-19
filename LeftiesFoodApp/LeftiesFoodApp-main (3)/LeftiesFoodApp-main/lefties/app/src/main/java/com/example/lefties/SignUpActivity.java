package com.example.lefties;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    //Database Initialize
    DBHelper dbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Spinner Functionality
        Spinner spinner = (Spinner) findViewById(R.id.accontType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.accountType,
                android.R.layout.simple_spinner_dropdown_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //Call New Database
        dbh = new DBHelper(this);
        EditText acnName = findViewById(R.id.accountName);
        Spinner acnType = findViewById(R.id.accontType);
        EditText acnEmail = findViewById(R.id.accountEmail);
        EditText acnPass = findViewById(R.id.accountPassword);
        EditText acnPhoneNumber = findViewById(R.id.accountPhoneNumber);
        EditText acnAddress = findViewById(R.id.accountAddress);
        Spinner acnCity = findViewById(R.id.accountCity);
        Button btnSignUpConfirm = findViewById(R.id.btnSignUp);

        btnSignUpConfirm.setOnClickListener(new View.OnClickListener() {
            long acctId;
//            String acnTypeText = acnType.getSelectedItem().toString();
            @Override
            public void onClick(View v) {

                String acctType = acnType.getSelectedItem().toString();

                acctId = dbh.addAccount(
                        acnName.getText().toString(),
                        acctType,
                        acnEmail.getText().toString(),
                        acnPass.getText().toString(),
                        acnPhoneNumber.getText().toString(),
                        acnAddress.getText().toString(),
                        acnCity.getSelectedItem().toString()
                );

                if (acctId > 0) {
                    Toast.makeText(getApplicationContext(), "Account added successfully", Toast.LENGTH_LONG).show();
                    Intent intent;
                    if(acctType == "Restaurant"){
                         intent = new Intent(SignUpActivity.this, RestaurantHomeActivity.class);
                    } else {
                        intent = new Intent(SignUpActivity.this, CustomerHomeActivity.class);
                    }
                    intent.putExtra("acctId", acctId);
                    intent.putExtra("acctName", acnName.getText().toString());
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to add account", Toast.LENGTH_LONG).show();
                }
            }
        });
    }



}