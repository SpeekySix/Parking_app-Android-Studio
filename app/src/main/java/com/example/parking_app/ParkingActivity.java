package com.example.parking_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ParkingActivity extends AppCompatActivity {
    private Spinner spinnerLots;
    private EditText editTextLicensePlate;
    private List<LotsItem> parkingLots;

    RecyclerView recyclerView;
    LotsRecyclerAdapter lotsRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking);

        recyclerView = (RecyclerView) findViewById(R.id.rvlots);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<LotsItem> options =
                new FirebaseRecyclerOptions.Builder<LotsItem>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("LOTS"), LotsItem.class)
                        .build();

        lotsRecyclerAdapter = new LotsRecyclerAdapter(options);
        recyclerView.setAdapter(lotsRecyclerAdapter);



        /*spinnerLots = findViewById(R.id.spinnerLots);
        editTextLicensePlate = findViewById(R.id.editTextLicensePlate);*/

        // Retrieve parking lots from Firebase and populate the spinner
        /*DatabaseReference lotsRef = FirebaseDatabase.getInstance().getReference("LOTS");
        lotsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                parkingLots = new ArrayList<>();

                for (DataSnapshot lotSnapshot : dataSnapshot.getChildren()) {
                    Lot lot = lotSnapshot.getValue(Lot.class);
                    parkingLots.add(lot);
                }

                // Create an ArrayAdapter to populate the spinner with lot names
                ArrayAdapter<Lot> adapter = new ArrayAdapter<>(ParkingActivity.this, android.R.layout.simple_spinner_item, parkingLots);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerLots.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                // Handle any errors
            }
        });
    }

    public void saveParking(View view) {
        // Get the selected lot and license plate
        int selectedLotIndex = spinnerLots.getSelectedItemPosition();
        String licensePlate = editTextLicensePlate.getText().toString();

        // Create a new parking entry with the selected lot details and license plate
        Lot selectedLot = parkingLots.get(selectedLotIndex);
        Parking newParking = new Parking(selectedLot.getName(), selectedLot.getPrice(), licensePlate);

        // Save the new parking entry to the PARKINGS table in Firebase
        DatabaseReference parkingsRef = FirebaseDatabase.getInstance().getReference("PARKINGS");
        String parkingId = parkingsRef.push().getKey();
        parkingsRef.child(parkingId).setValue(newParking);*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        lotsRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        lotsRecyclerAdapter.stopListening();
    }
}