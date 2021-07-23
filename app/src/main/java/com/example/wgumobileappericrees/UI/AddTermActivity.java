package com.example.wgumobileappericrees.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.wgumobileappericrees.Database.WGUMobileDB;
import com.example.wgumobileappericrees.Entity.Term;
import com.example.wgumobileappericrees.R;
import com.example.wgumobileappericrees.Utility.DatePickerFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddTermActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    WGUMobileDB db;
    boolean addedTerm;
    SimpleDateFormat dateFormat;
    Date tDate;
    Button btnAddTerm;
    private TextView datePickerV;
    EditText termName;
    TextView termStart;
    TextView termEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);
        db = WGUMobileDB.getInstance(getApplicationContext());
        btnAddTerm = findViewById(R.id.btn_addTerm);
        termName = findViewById(R.id.eTxt_addTermName);
        termStart = findViewById(R.id.eDate_addTermStart);
        termEnd = findViewById(R.id.eDate_addTermEnd);

        setDatePicker();

        // Set add term button
        btnAddTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addTerm();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (addedTerm) {
                    Intent intent = new Intent(AddTermActivity.this, TermActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    // Method to set date chosen for term start and end dates
    private void setDatePicker() {
        termStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerV = findViewById(R.id.eDate_addTermStart);
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        termEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerV = findViewById(R.id.eDate_addTermEnd);
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month = month + 1);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = month + "/" + dayOfMonth + "/" + year;
        datePickerV.setText(currentDateString);
    }

    // Method to add new term
    private void addTerm() throws ParseException {
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String tName = termName.getText().toString();
        String tStart = termStart.getText().toString();
        String tEnd = termEnd.getText().toString();
        String tDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        Date tStartDate = dateFormat.parse(tStart);
        Date tEndDate = dateFormat.parse(tEnd);
        this.tDate = dateFormat.parse(tDate);

        if (tName.trim().isEmpty() || (tStart.trim().isEmpty() || (tEnd.trim().isEmpty()))) {
            Toast.makeText(getApplicationContext(), "All fields are required, can't be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (tStartDate.after(tEndDate)) {
            Toast.makeText(getApplicationContext(), "Start date needs to be before end date!", Toast.LENGTH_SHORT).show();
            return;
        }

        Term t = new Term();
        t.setTermName(tName);
        t.setTermStart(tStartDate);
        t.setTermEnd(tEndDate);
        db.termDAO().insert(t);
        Toast.makeText(getApplicationContext(), tName + " was added!", Toast.LENGTH_LONG).show();
        addedTerm = true;
    }

    // Menu setup
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // WGU icon to send to home screen
        if (id == R.id.home) {
            Intent intent = new Intent(AddTermActivity.this, HomeActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}