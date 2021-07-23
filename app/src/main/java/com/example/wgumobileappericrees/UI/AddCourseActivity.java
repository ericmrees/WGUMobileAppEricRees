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
import com.example.wgumobileappericrees.Entity.Course;
import com.example.wgumobileappericrees.R;
import com.example.wgumobileappericrees.Utility.DatePickerFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddCourseActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    WGUMobileDB db;
    boolean addedCourse;
    int termId;
    Intent intent;
    SimpleDateFormat dateFormat;
    Date cDate;
    Date cStartDate;
    Date cEndDate;
    String cName;
    Button btnAddCourse;
    private TextView datePickerV;
    EditText courseName;
    TextView courseStart;
    TextView courseEnd;
    Spinner spinStatus;
    String stringStatus;
    EditText note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        db = WGUMobileDB.getInstance(getApplicationContext());
        intent = getIntent();
        termId = intent.getIntExtra("termId", -1);
        btnAddCourse = findViewById(R.id.btn_addCourse);
        courseName = findViewById(R.id.eTxt_addCourseName);
        courseStart = findViewById(R.id.eDate_addCourseStart);
        courseEnd = findViewById(R.id.eDate_addCourseEnd);
        spinStatus = findViewById(R.id.spn_addStatus);
        note = findViewById(R.id.eTxt_addNotes);

        setDatePicker();

        // Set add course button
        btnAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addCourse();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (addedCourse == true) {
                    Intent intent = new Intent(AddCourseActivity.this, TermDetailsActivity.class);
                    intent.putExtra("termId", termId);
                    startActivity(intent);
                }
            }
        });

        // Set course status spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinStatus.setAdapter(adapter);

        spinStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stringStatus = spinStatus.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    // Method to set date chosen for course start and end dates
    private void setDatePicker() {
        courseStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerV = findViewById(R.id.eDate_addCourseStart);
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        courseEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerV = findViewById(R.id.eDate_addCourseEnd);
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

    // Method to add new course
    private void addCourse() throws ParseException {
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        cName = courseName.getText().toString();
        String cStart = courseStart.getText().toString();
        String cEnd = courseEnd.getText().toString();
        String cDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        cStartDate = dateFormat.parse(cStart);
        cEndDate = dateFormat.parse(cEnd);
        this.cDate = dateFormat.parse(cDate);
        String cNote = note.getText().toString();

        if (cName.trim().isEmpty() || (cStart.trim().isEmpty() || (cEnd.trim().isEmpty()))) {
            Toast.makeText(getApplicationContext(), "All fields are required, can't be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (cStartDate.after(cEndDate)) {
            Toast.makeText(getApplicationContext(), "Start date needs to be before end date!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (cNote.trim().isEmpty()) {
            cNote = "Need to take notes!";
        }

        Course c = new Course();
        c.setCTermId(termId);
        c.setCourseName(cName);
        c.setCourseStart(cStartDate);
        c.setCourseEnd(cEndDate);
        c.setStatus(stringStatus);
        c.setCourseNote(cNote);
        db.courseDAO().insert(c);
        Toast.makeText(getApplicationContext(), cName + " was added!", Toast.LENGTH_LONG).show();
        addedCourse = true;
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
            Intent intent = new Intent(AddCourseActivity.this, HomeActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
