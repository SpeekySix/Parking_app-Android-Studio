package com.example.parking_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.parking_app.databinding.ActivityProfileBinding;
import com.example.parking_app.databinding.ActivityTermsBinding;

public class TermsActivity extends DrawerActivity {

    ActivityTermsBinding activityTermsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityTermsBinding = ActivityTermsBinding.inflate(getLayoutInflater());
        setContentView(activityTermsBinding.getRoot());
        allocateActivityTitle("");
    }
}