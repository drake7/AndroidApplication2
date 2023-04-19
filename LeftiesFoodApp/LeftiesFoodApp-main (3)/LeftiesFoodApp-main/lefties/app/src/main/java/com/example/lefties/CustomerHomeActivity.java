package com.example.lefties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
// Macci - Renamed CustomerActivity to CustomerHomeActivity
public class CustomerHomeActivity extends AppCompatActivity {

    DBHelper dbh;
    RecyclerView inventoryList;
    ArrayList<HashMap<String, String>> inventoryMapper = new ArrayList<>();
    Button addItem;
    FoodItemAdapterClass adapter;
    ArrayList<HashMap> foods;

    Cursor c;

    long acctId;
    long restaurantIdChosen;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        Bundle extras = getIntent().getExtras();
        acctId = extras.getLong("acctId");

       TextView name = findViewById(R.id.FromD);
       TextView address = findViewById(R.id.AtD);
       TextView city = findViewById(R.id.ToD);

        dbh = new DBHelper(this);

        inventoryList = findViewById(R.id.customerRestaurantRecycler);

        int columnCount = 2;
        inventoryList.setLayoutManager(
                new GridLayoutManager(this, columnCount));

        foods = new ArrayList<HashMap>();
        adapter = new FoodItemAdapterClass(this, foods, acctId);
        c = dbh.viewDataFoodWithRestaurantName();
        updateRecycler(c);
        inventoryList.setAdapter(adapter);

        setupSearchByCity();
        setupSearchByType();


    }

    public void updateRecycler(Cursor c){
        if (c.getCount() > 0) {
            foods.clear(); // clear the previous list
            while (c.moveToNext()) { // while there is still line left
                HashMap<String, String> foodTableColumns = new HashMap<>();
                foodTableColumns.put("food_id", c.getString(0));
                foodTableColumns.put("account_id", c.getString(1));
                foodTableColumns.put("food_name", c.getString(2));
                foodTableColumns.put("food_discounted_price", c.getString(3));
                foodTableColumns.put("food_regular_price", c.getString(4));
                foodTableColumns.put("food_qty", c.getString(5));
                if(c.getColumnCount() > 6){
                    foodTableColumns.put("restaurant_name", c.getString(7));
                }else{
                    foodTableColumns.put("restaurant_name", " ");
                }
                foods.add(foodTableColumns);
            }
        }
        adapter.notifyDataSetChanged();
    }


    // Macci - Isolate search functionality

    public void setupSearchByCity(){
        // Rajat - Enable city search
        Spinner spinner = findViewById(R.id.spinner3);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinner != null && spinner.getSelectedItem() != null && spinner.getSelectedItem().toString().equals("Surrey")) {
                    // Macci - Create reusable function updateRecycler that accepts the cursor
                    Cursor c = dbh.viewDataFoodM();
                    updateRecycler(c);
                }

                if (spinner != null && spinner.getSelectedItem() != null && spinner.getSelectedItem().toString().equals("Vancouver")) {
                    Cursor  c = dbh.viewDataFood();
                    updateRecycler(c);
                }

                if (spinner != null && spinner.getSelectedItem() != null && spinner.getSelectedItem().toString().equals("Burnaby")) {
                    Cursor c = dbh.viewDataFoodP();
                    updateRecycler(c);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void setupSearchByType(){
        Button featured = findViewById(R.id.Featured);
        Button mexican = findViewById(R.id.typeMexican);
        Button Pastries = findViewById(R.id.Pastries);
        Button Chinese = findViewById(R.id.typeChinese);
        Button Chicken = findViewById(R.id.Chicken);
        Button Veg = findViewById(R.id.Vegetarian);

        featured.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = dbh.viewDataFoodF();
                updateRecycler(c);
            }
        });

        mexican.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = dbh.viewDataFoodM();
                updateRecycler(c);
            }
        });

        Pastries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = dbh.viewDataFoodP();
                updateRecycler(c);
            }
        });

        Chinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = dbh.viewDataFoodC();
                updateRecycler(c);
            }
        });

        Chicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = dbh.viewDataFoodCh();
                updateRecycler(c);
            }
        });

        Veg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = dbh.viewDataFoodVeg();
                updateRecycler(c);
            }
        });

    }

}

