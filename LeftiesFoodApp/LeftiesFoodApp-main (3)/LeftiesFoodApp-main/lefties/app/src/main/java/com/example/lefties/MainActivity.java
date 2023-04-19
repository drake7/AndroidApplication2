package com.example.lefties;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    DBHelper dbh;
    long acctId;
    String acctName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbh = new DBHelper(this);
        seedTable();

        EditText userEmail = findViewById(R.id.userEmail);
        EditText userPass = findViewById(R.id.userPass);
        Button btnLogin = findViewById(R.id.loginBtn);
        Button btnSignUp = findViewById(R.id.btnGoSignUp);

        //creating a notification channel in the start
        NotificationUtils.createNotificationChannel(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor c = dbh.viewDataAccount(userEmail.getText().toString(), userPass.getText().toString());
                StringBuilder str = new StringBuilder();
                if(c.getCount() > 0){
                    String accountType;
                    if (c.moveToFirst()){
                        accountType = c.getString(2);
                        acctName = c.getString(3);
                        acctId = Long.parseLong(c.getString(0));

                        if(accountType.equals("Customer")){
                            Toast.makeText(MainActivity.this, "Customer", Toast.LENGTH_SHORT).show();
                            goToCustomerHome()
;                        } else {
                            Toast.makeText(MainActivity.this, "Manager", Toast.LENGTH_SHORT).show();
                            goToRestoHome();
                        }
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Please enter valid a Email or Password.", Toast.LENGTH_SHORT).show();

                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSignUpPage();
            }
        });
    }

    public void goToRestoHome()
    {
        Intent i = new Intent(getApplicationContext(),RestaurantHomeActivity.class);
        i.putExtra("acctId", acctId);
        i.putExtra("accName", acctName);
        startActivity(i);
    }

    public void goToSignUpPage() {
        Intent i = new Intent(getApplicationContext(),SignUpActivity.class);
        startActivity(i);
    }

    public void goToCustomerHome()
    {
        Intent i = new Intent(getApplicationContext(), CustomerHomeActivity.class);
        i.putExtra("acctId", acctId);
        i.putExtra("accName", acctName);
        startActivity(i);
    }

    // Macci - Populate with data. If "Golden Star" restaurant does not exist, populate the tables
    public void seedTable(){
        Cursor c = dbh.viewAccountByName("Golden Star");
        StringBuilder str = new StringBuilder();
        if(c.getCount() == 0){
            DBSeeder dbs = new DBSeeder(dbh);
            dbs.seedTables();
        }
    }
}