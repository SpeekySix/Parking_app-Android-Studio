package com.example.parking_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.SignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class HomeActivity extends AppCompatActivity {

    TextView name,email;
    Button logout;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    //parking button send to ParkingActivity
    ImageButton parkBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //parking button configurare
        parkBtn = (ImageButton) findViewById(R.id.parkBtn);
        parkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openParkingActivity();
            }
        });



        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        logout = findViewById(R.id.logoutBtn);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account!=null){
            String Name = account.getDisplayName();
            String Email = account.getEmail();

            name.setText(Name);
            email.setText(Email);
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignOut();
            }
        });

    }


//functia pt deschiderea ParkingActivity dupa apasare parking btn

    private void ParkingActivity(){

        finish();
        Intent intent = new Intent(getApplicationContext(),ParkingActivity.class);
        startActivity(intent);
    }

    public void openParkingActivity() {
        Intent intent = new Intent(this, ParkingActivity.class);
        startActivity(intent);
    }

    private void SignOut() {

        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}