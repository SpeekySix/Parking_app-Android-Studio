package com.example.admin_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {


    private ImageButton lotsBtn;
    private ImageButton priceBtn;
    private ImageButton accBtn;
    private ImageButton usersBtn;
    private ImageButton historyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //configurare buton trimitere LotsActivity din MainActivity ADMIN
        lotsBtn = (ImageButton) findViewById(R.id.lotsBtn);
        lotsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openLotsActivity();
            }
        });

        //configurare buton trimitere PricesActivity din MainActivity ADMIN
        priceBtn = (ImageButton) findViewById(R.id.priceBtn);
        priceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openPricesActivity();
            }
        });

        //configurare buton trimitere UsersActivity din MainActivity ADMIN
        usersBtn = (ImageButton) findViewById(R.id.usersBtn);
        usersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openUsersActivity();
            }
        });

        //configurare buton trimitere HistoryActivity din MainActivity ADMIN
        historyBtn = (ImageButton) findViewById(R.id.historyBtn);
        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openHistoryActivity();
            }
        });

        //configurare buton trimitere AccountingActivity din MainActivity ADMIN
        accBtn = (ImageButton) findViewById(R.id.accBtn);
        accBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openAccountingActivity();
            }
        });


    }


    //functiile pentru Activitati

    private void openLotsActivity() {
        Intent intent = new Intent(this, LotsActivity.class);
        startActivity(intent);
    }

    private void openPricesActivity() {
        Intent intent = new Intent(this, PricesActivity.class);
        startActivity(intent);
    }

    private void openUsersActivity() {
        Intent intent = new Intent(this, PricesActivity.class);
        startActivity(intent);
    }

    private void openHistoryActivity() {
        Intent intent = new Intent(this, PricesActivity.class);
        startActivity(intent);
    }

    private void openAccountingActivity() {
        Intent intent = new Intent(this, PricesActivity.class);
        startActivity(intent);
    }
}