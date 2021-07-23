package com.example.wgumobileappericrees.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wgumobileappericrees.Database.WGUMobileDB;
import com.example.wgumobileappericrees.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TermActivity extends AppCompatActivity {
    WGUMobileDB db;
    FloatingActionButton FABAddTerm;
    RecyclerView rvTerm;
    LinearLayoutManager linearLayoutManager;
    TermAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        db = WGUMobileDB.getInstance(getApplicationContext());
        FABAddTerm = findViewById(R.id.FAB_addTerm);
        rvTerm = findViewById(R.id.rv_terms);

        setTermAdapter();

        // Set add term floating action button
        FABAddTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TermActivity.this, AddTermActivity.class);
                startActivity(intent);
            }
        });
    }

    // Set term adapter for recycler view
    private void setTermAdapter() {
        // Initialize linear layout manager
        linearLayoutManager = new LinearLayoutManager(this);
        // Set layout manager
        rvTerm.setLayoutManager(linearLayoutManager);
        // Initialize adapter
        adapter = new TermAdapter(this);
        // Set adapter
        rvTerm.setAdapter(adapter);
        adapter.setWords(db.termDAO().getAllTerms());
    }

    // Menu setup
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // WGU icon sends to home screen
        if (id == R.id.home) {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}