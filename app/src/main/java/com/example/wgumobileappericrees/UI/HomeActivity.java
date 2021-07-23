package com.example.wgumobileappericrees.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.wgumobileappericrees.Database.WGUMobileDB;
import com.example.wgumobileappericrees.R;
import com.example.wgumobileappericrees.Utility.TestData;

import static android.graphics.Color.*;

public class HomeActivity extends AppCompatActivity {
    private WGUMobileDB db;
    Button btnEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        db = WGUMobileDB.getInstance(getApplicationContext());
        btnEnter = findViewById(R.id.btn_enter);

        // Set enter button
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TermActivity.class);
                startActivity(intent);
            }
        });

        // Create addTestData button programmatically
        ConstraintLayout homeLayout = findViewById(R.id.homeConstraintLayout);
        ConstraintSet set = new ConstraintSet();
        Button addTestDataBtn = new Button(getApplicationContext());
        addTestDataBtn.setText("ADD TEST DATA");
        addTestDataBtn.setId(R.id.btn_addTestData);
        addTestDataBtn.setBackgroundColor(Color.parseColor("#003057"));
        addTestDataBtn.setTextColor(Color.parseColor("white"));
        addTestDataBtn.setPadding(16, 0, 16, 0);
        addTestDataBtn.setTypeface(null, Typeface.BOLD);
        set.constrainHeight(addTestDataBtn.getId(), ConstraintSet.WRAP_CONTENT);
        set.constrainWidth(addTestDataBtn.getId(), ConstraintSet.WRAP_CONTENT);
        set.connect(addTestDataBtn.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 32);
        set.connect(addTestDataBtn.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 32);
        homeLayout.addView(addTestDataBtn);
        setContentView(homeLayout);
        set.applyTo(homeLayout);

        // Set add test data button
        addTestDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestData testData = new TestData();
                testData.addTestData(getApplicationContext());
                Toast.makeText(getApplicationContext(), "Test data added to database!", Toast.LENGTH_SHORT).show();
            }
        });

        // Create deleteTestData button programmatically
        Button deleteTestDataBtn = new Button(getApplicationContext());
        deleteTestDataBtn.setText("DELETE TEST DATA");
        deleteTestDataBtn.setId(R.id.btn_deleteTestData);
        deleteTestDataBtn.setBackgroundColor(Color.parseColor("#003057"));
        deleteTestDataBtn.setTextColor(parseColor("white"));
        deleteTestDataBtn.setPadding(16, 0, 16, 0);
        deleteTestDataBtn.setTypeface(null, Typeface.BOLD);
        set.constrainHeight(deleteTestDataBtn.getId(), ConstraintSet.WRAP_CONTENT);
        set.constrainWidth(deleteTestDataBtn.getId(), ConstraintSet.WRAP_CONTENT);
        set.connect(deleteTestDataBtn.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 32);
        set.connect(deleteTestDataBtn.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 32);
        homeLayout.addView(deleteTestDataBtn);
        setContentView(homeLayout);
        set.applyTo(homeLayout);

        // Set delete test data button
        deleteTestDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.clearAllTables();
                Toast.makeText(getApplicationContext(), "Test data deleted from database!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}