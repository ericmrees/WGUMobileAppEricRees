package com.example.wgumobileappericrees.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.wgumobileappericrees.Database.WGUMobileDB;
import com.example.wgumobileappericrees.Entity.Assessment;
import com.example.wgumobileappericrees.R;
import com.example.wgumobileappericrees.Utility.DatePickerFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddAssessmentActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    WGUMobileDB db;
    boolean addedAssessment;
    int termId;
    int courseId;
    Intent intent;
    SimpleDateFormat dateFormat;
    Date aDate;
    Date aStartDate;
    Date aEndDate;
    String aName;
    Button btnAddAssessment;
    private TextView datePickerV;
    EditText assessmentName;
    TextView assessmentStart;
    TextView assessmentEnd;
    Spinner spinType;
    String stringType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);
        db = WGUMobileDB.getInstance(getApplicationContext());
        intent = getIntent();
        termId = intent.getIntExtra("termId", -1);
        courseId = intent.getIntExtra("courseId", -1);
        btnAddAssessment = findViewById(R.id.btn_addAssessment);
        assessmentName = findViewById(R.id.eTxt_addAssessmentName);
        assessmentStart = findViewById(R.id.eDate_addAssessmentStart);
        assessmentEnd = findViewById(R.id.eDate_addAssessmentEnd);
        spinType = findViewById(R.id.spn_addType);

        setDatePicker();

        // Set add assessment button
        btnAddAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addAssessment();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (addedAssessment) {
                    Intent intent = new Intent(AddAssessmentActivity.this, CourseDetailsActivity.class);
                    intent.putExtra("termId", termId);
                    intent.putExtra("courseId", courseId);
                    startActivity(intent);
                }
            }
        });

        // Set assessment type spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinType.setAdapter(adapter);

        spinType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stringType = spinType.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    // Method to set date chosen for assessment start and end dates
    private void setDatePicker() {
        assessmentStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerV = findViewById(R.id.eDate_addAssessmentStart);
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        assessmentEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerV = findViewById(R.id.eDate_addAssessmentEnd);
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

    // Method to add new assessment
    private void addAssessment() throws ParseException {
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        aName = assessmentName.getText().toString();
        String aStart = assessmentStart.getText().toString();
        String aEnd = assessmentEnd.getText().toString();
        String aDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        aStartDate = dateFormat.parse(aStart);
        aEndDate = dateFormat.parse(aEnd);
        this.aDate = dateFormat.parse(aDate);

        if (aName.trim().isEmpty() || (aStart.trim().isEmpty() || (aEnd.trim().isEmpty()))) {
            Toast.makeText(getApplicationContext(), "All fields are required, can't be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (aStartDate.after(aEndDate)) {
            Toast.makeText(getApplicationContext(), "Start date needs to be before end date!", Toast.LENGTH_SHORT).show();
            return;
        }

        Assessment a = new Assessment();
        a.setACourseId(courseId);
        a.setAssessmentName(aName);
        a.setAssessmentStart(aStartDate);
        a.setAssessmentEnd(aEndDate);
        a.setType(stringType);
        db.assessmentDAO().insert(a);
        Toast.makeText(getApplicationContext(), aName + " was added!", Toast.LENGTH_LONG).show();
        addedAssessment = true;
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
            Intent intent = new Intent(AddAssessmentActivity.this, HomeActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
