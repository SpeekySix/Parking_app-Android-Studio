package com.example.admin_app;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import io.reactivex.rxjava3.annotations.NonNull;

public class LotsActivity extends AppCompatActivity {

    DatabaseReference databaseReference;

    RecyclerView recyclerView;
    ArrayList<LotsItem> lotsItemArrayList;
    LotsRecyclerAdapter adapter;

    Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lots);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true); // work offline
        //Objects.requireNonNull(getSupportActionBar()).hide();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        lotsItemArrayList = new ArrayList<>();


        //buttons for Lots CRUD
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDialogAdd viewDialogAdd = new ViewDialogAdd();
                viewDialogAdd.showDialog(LotsActivity.this);
            }
        });

        readData();
    }


    //adaugare new lot in baza de date si creare adapter nou pentru o noua zona de parcare
    private void readData() {

        databaseReference.child("LOTS").orderByChild("lotName").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lotsItemArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    LotsItem lots = dataSnapshot.getValue(LotsItem.class);
                    lotsItemArrayList.add(lots);
                }
                adapter = new LotsRecyclerAdapter(LotsActivity.this, lotsItemArrayList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    //deschidere fereastra dialog pentru Add new lot
    public class ViewDialogAdd {
        public void showDialog(Context context) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_dialog_add_new_lot);

            EditText textName = dialog.findViewById(R.id.textName);
            EditText textPrice = dialog.findViewById(R.id.textPrice);


            Button buttonAdd = dialog.findViewById(R.id.buttonAdd);
            Button buttonCancel = dialog.findViewById(R.id.buttonCancel);

            buttonAdd.setText("ADD");
            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String idLot = "lot" + new Date().getTime();
                    String name = textName.getText().toString();
                    String price = textPrice.getText().toString();

                    if (name.isEmpty() || price.isEmpty() ) {
                        Toast.makeText(context, "All fields must be completed!", Toast.LENGTH_SHORT).show();
                    } else {
                        databaseReference.child("LOTS").child(idLot).setValue(new LotsItem(idLot, name, price));
                        Toast.makeText(context, "Lot added!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        }
    }
}
