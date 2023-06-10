package com.example.admin_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class UsersActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    UsersRecyclerAdapter usersRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        recyclerView = (RecyclerView)findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<UsersItem> options =
                new FirebaseRecyclerOptions.Builder<UsersItem>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("USRS"), UsersItem.class)
                .build();

        usersRecyclerAdapter = new UsersRecyclerAdapter(options);
        recyclerView.setAdapter(usersRecyclerAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        usersRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        usersRecyclerAdapter.stopListening();
    }


    //Search
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                txtSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                txtSearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void txtSearch(String str){
        FirebaseRecyclerOptions<UsersItem> options =
                new FirebaseRecyclerOptions.Builder<UsersItem>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("USRS").orderByChild("nameU").startAt(str).endAt(str+"~"), UsersItem.class)
                        .build();
        new FirebaseRecyclerOptions.Builder<UsersItem>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("USRS").orderByChild("emailU").startAt(str).endAt(str+"~"), UsersItem.class)
                .build();
        new FirebaseRecyclerOptions.Builder<UsersItem>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("USRS").orderByChild("plate").startAt(str).endAt(str+"~"), UsersItem.class)
                .build();

        usersRecyclerAdapter = new UsersRecyclerAdapter(options);
        usersRecyclerAdapter.startListening();
        recyclerView.setAdapter(usersRecyclerAdapter);
    }




}