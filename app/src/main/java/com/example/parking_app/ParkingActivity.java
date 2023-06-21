package com.example.parking_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingActivity extends AppCompatActivity {
    private Spinner spinnerLots;
    private EditText editTextLicensePlate;
    private List<LotsItem> parkingLots;

    Button payment;
    String PublishableKey = "pk_test_51NHPD6CeAsHZuBkI6FSUJMBvIAsxEfQIq0RcijR60ZvB5YilxdJbkVKn6G2BTNEEKTuIk0ZfKQjFBDPfdr9BTQSc00NdksfWor";
    String SecretKey = "sk_test_51NHPD6CeAsHZuBkIRBaDBYKbvjN9WEoxX9Mz8xY3x6imB6ZGjHpUjW0TXgEwmWhMLeLjFkYqMA5t1id3c1f0uJNh0013BMBdUL";
    String CustomerId;
    String EphemeralKey; //Epherical
    String ClientSecret;
    PaymentSheet paymentSheet;

    RecyclerView recyclerView;
    LotsRecyclerAdapter lotsRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_parking);

        payment = findViewById(R.id.payBtn);

        PaymentConfiguration.init(this, PublishableKey);


        paymentSheet = new PaymentSheet(this, paymentSheetResult -> {

            onPaymentResult(paymentSheetResult);

        });


        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                paymentFlow();

            }
        });


        StringRequest request = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/customers",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)  {

                        try {

                            JSONObject object = new JSONObject(response);
                            CustomerId = object.getString("id");
                            Toast.makeText(ParkingActivity.this, CustomerId, Toast.LENGTH_SHORT).show();
                            getEphemeralKey();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ParkingActivity.this,error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> header = new HashMap<>();
                header.put("Authorization", "Bearer "+SecretKey);
                return header;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

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

    private void paymentFlow() {

        paymentSheet.presentWithPaymentIntent(ClientSecret, new PaymentSheet.Configuration("Message!!!",new PaymentSheet.CustomerConfiguration(

                CustomerId,
                EphemeralKey

        )));



    }

    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {

        if (paymentSheetResult instanceof PaymentSheetResult.Completed){

            Toast.makeText(this, "Payment succes", Toast.LENGTH_SHORT).show();
        }

    }

    private void getEphemeralKey() {

        StringRequest request = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/ephemeral_keys",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject object = new JSONObject(response);
                            EphemeralKey = object.getString("id");
                            Toast.makeText(ParkingActivity.this, CustomerId, Toast.LENGTH_SHORT).show();
                            getClientSecret(CustomerId, EphemeralKey);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ParkingActivity.this,error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> header = new HashMap<>();
                header.put("Authorization", "Bearer "+SecretKey);
                header.put("Stripe-Version", "2022-11-15");
                return header;

            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("customer", CustomerId);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);


    }

    private void getClientSecret(String customerId, String ephemeralKey) {

        StringRequest request = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/payment_intents",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject object = new JSONObject(response);
                            ClientSecret = object.getString("client_secret");
                            Toast.makeText(ParkingActivity.this, ClientSecret, Toast.LENGTH_SHORT).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ParkingActivity.this,error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> header = new HashMap<>();
                header.put("Authorization", "Bearer "+SecretKey);

                return header;

            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("customer", CustomerId);
                params.put("amount", "100"+"00");
                params.put("currency","RON");
                params.put("automatic_payment_methods[enabled]", "true");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

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