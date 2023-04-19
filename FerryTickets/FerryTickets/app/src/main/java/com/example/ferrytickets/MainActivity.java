package com.example.ferrytickets;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    double costPerTicketToCarolina = 34;
    double costPerTicketToLongBeach = 40;
    int numberOfTicket;
    double totalCost;
    String tripChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText tickets = findViewById(R.id.numberOfTickets);
        Spinner spinner = findViewById(R.id.spinnerDes);
        TextView result = findViewById(R.id.result);
        Button cost = findViewById(R.id.computeCostButton);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.trip, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        cost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    numberOfTicket = Integer.parseInt(String.valueOf(tickets.getText()));
                }
                catch (NumberFormatException rc)
                {
                    result.setText("Please enter the number of tickets you need");
                    return;
                }

                NumberFormat currency = NumberFormat.getCurrencyInstance();
                tripChoice = spinner.getSelectedItem().toString();
                if(spinner.getSelectedItemPosition()==0)
                {
                    totalCost = costPerTicketToCarolina * numberOfTicket;
                }
                else{
                    totalCost = costPerTicketToLongBeach * numberOfTicket;
                }
                result.setText("One Way trip "+ tripChoice +" for "+ numberOfTicket +" passengers "
                + currency.format(totalCost));
                }
        });

    }
}