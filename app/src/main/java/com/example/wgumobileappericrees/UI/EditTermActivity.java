package com.example.wgumobileappericrees.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
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

public class EditTermActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    WGUMobileDB db;
    boolean updatedTerm;
    int termId;
    int courseList;
    Intent intent;
    private TextView datePickerV;
    SimpleDateFormat dateFormat;
    EditText editTermName;
    TextView editTermStart;
    TextView editTermEnd;
    Button btnUpdateTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_term);
        intent = getIntent();
        db = WGUMobileDB.getInstance(getApplicationContext());
        termId = intent.getIntExtra("termId", -1);
        courseList = intent.getIntExtra("courseList", -1);
        editTermName = findViewById(R.id.eTxt_updateTermName);
        editTermStart = findViewById(R.id.eDate_updateTermStart);
        editTermEnd = findViewById(R.id.eDate_updateTermEnd);
        btnUpdateTerm = findViewById(R.id.btn_updateTerm);

        setDatePicker();
        setTermValues();

        // Set update term button
        btnUpdateTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    updateTerm();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (updatedTerm) {
                    Intent intent = new Intent(EditTermActivity.this, TermActivity.class);
                    intent.putExtra("termId", termId);
                    startActivity(intent);
                }
            }
        });
    }

    // Method to update existing term
    private void updateTerm() throws ParseException {
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String tName = editTermName.getText().toString();
        String tStart = editTermStart.getText().toString();
        String tEnd = editTermEnd.getText().toString();
        Date tStartDate = dateFormat.parse(tStart);
        Date tEndDate = dateFormat.parse(tEnd);

        if (tName.trim().isEmpty() || tStart.trim().isEmpty() || tEnd.trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "All fields are required, can't be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (tStartDate.after(tEndDate)) {
            Toast.makeText(getApplicationContext(), "Start date needs to be before end date!", Toast.LENGTH_SHORT).show();
            return;
        }

        Term t = new Term();
        t.setTermId(termId);
        t.setTermName(tName);
        t.setTermStart(tStartDate);
        t.setTermEnd(tEndDate);
        db.termDAO().update(t);
        Toast.makeText(getApplicationContext(), tName + " was updated!", Toast.LENGTH_SHORT).show();
        updatedTerm = true;
    }

    // Method to set term values
    private void setTermValues() {
        Term t = new Term();
        t = db.termDAO().getTerm(termId);
        String tName = t.getTermName();
        String tStart = DateFormat.format("MM/dd/yyyy", t.getTermStart()).toString();
        String tEnd = DateFormat.format("MM/dd/yyyy", t.getTermEnd()).toString();

        editTermName.setText(tName);
        editTermStart.setText(tStart);
        editTermEnd.setText(tEnd);
    }

    // Method to set date chosen for term start and end dates
    private void setDatePicker() {
        editTermStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerV = findViewById(R.id.eDate_updateTermStart);
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        editTermEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerV = findViewById(R.id.eDate_updateTermEnd);
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
            Intent intent = new Intent(EditTermActivity.this, HomeActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}