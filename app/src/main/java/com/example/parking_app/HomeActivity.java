package com.example.parking_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.util.jar.Attributes;


public class HomeActivity extends AppCompatActivity {

    TextView name;
    //TextView email;

    EditText nrPlateField, nameUsr, emailUsr;

    Button logout;
    Button save;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    //parking button send to ParkingActivity
    ImageButton parkBtn;

    Button about;
    Button profile;
    ImageButton maps;

    DatabaseReference dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        nameUsr = findViewById(R.id.fieldName);
        emailUsr = findViewById(R.id.fieldEmail);
        nrPlateField = findViewById(R.id.fieldNrPlate);

        save = findViewById(R.id.saveBtn);

        dbRef = FirebaseDatabase.getInstance().getReference().child("USRS");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertUser();
            }
        });



        //parking button configurare
        parkBtn = (ImageButton) findViewById(R.id.parkBtn);
        parkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openParkingActivity();
            }
        });

        name = findViewById(R.id.name);
        //email = findViewById(R.id.email);


        about = (Button) findViewById(R.id.aboutBtn);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAboutActivity();
            }
        });

        profile = (Button) findViewById(R.id.profileBtn);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProfileActivity();
            }
        });

        maps = (ImageButton) findViewById(R.id.mapsBtn);
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMapsActivity();
            }
        });


        logout = findViewById(R.id.logoutBtn);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account!=null){
            String Name = account.getDisplayName();
            //String Email = account.getEmail();

            name.setText(Name);
            //email.setText(Email);

        }




        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignOut();
            }
        });

    }

    private void openMapsActivity() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    private void openProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void openAboutActivity() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    private void insertUser() {

        String nameU = nameUsr.getText().toString();
        String emailU = emailUsr.getText().toString();
        String plate = nrPlateField.getText().toString();

        Users users = new Users(nameU, emailU, plate);

        dbRef.push().setValue(users);
        Toast.makeText(HomeActivity.this, "Modificarile au fost salvate!", Toast.LENGTH_SHORT).show();
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