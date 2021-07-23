package com.example.wgumobileappericrees.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
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

public class EditCourseActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    WGUMobileDB db;
    boolean updatedCourse;
    int termId;
    int courseId;
    Intent intent;
    private TextView datePickerV;
    SimpleDateFormat dateFormat;
    Date cDate;
    EditText editCourseName;
    Spinner editSpinStatus;
    String editStatus;
    TextView editCourseStart;
    TextView editCourseEnd;
    EditText editNote;
    Button btnUpdateCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);
        intent = getIntent();
        db = WGUMobileDB.getInstance(getApplicationContext());
        termId = intent.getIntExtra("termId", -1);
        courseId = intent.getIntExtra("courseId", -1);
        editCourseName = findViewById(R.id.eTxt_updateCourseName);
        editSpinStatus = findViewById(R.id.spn_updateStatus);
        editCourseStart = findViewById(R.id.eDate_updateCourseStart);
        editCourseEnd = findViewById(R.id.eDate_updateCourseEnd);
        editNote = findViewById(R.id.eTxt_updateNotes);
        btnUpdateCourse = findViewById(R.id.btn_updateCourse);

        setDatePicker();
        setCourseValues();

        // Set course status spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editSpinStatus.setAdapter(adapter);

        editSpinStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editStatus = editSpinStatus.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Set update course button
        btnUpdateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    updateCourse();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (updatedCourse) {
                    Intent intent = new Intent(EditCourseActivity.this, TermDetailsActivity.class);
                    intent.putExtra("termId", termId);
                    intent.putExtra("courseId", courseId);
                    startActivity(intent);
                }
            }
        });
    }

    // Method to update existing course
    private void updateCourse() throws ParseException {
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String cName = editCourseName.getText().toString();
        String cStart = editCourseStart.getText().toString();
        String cEnd = editCourseEnd.getText().toString();
        String cDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        Date cStartDate = dateFormat.parse(cStart);
        Date cEndDate = dateFormat.parse(cEnd);
        this.cDate = dateFormat.parse(cDate);
        String cNote = editNote.getText().toString();

        if (cName.trim().isEmpty() || cStart.trim().isEmpty() || cEnd.trim().isEmpty()) {
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
        c.setCourseId(courseId);
        c.setCTermId(termId);
        c.setCourseName(cName);
        c.setStatus(editStatus);
        c.setCourseStart(cStartDate);
        c.setCourseEnd(cEndDate);
        c.setCourseNote(cNote);
        db.courseDAO().update(c);
        Toast.makeText(getApplicationContext(), cName + " was updated!", Toast.LENGTH_SHORT).show();
        updatedCourse = true;
    }

    // Method to set course values
    private void setCourseValues() {
        Course c = new Course();
        c = db.courseDAO().getCourse(termId, courseId);
        String cName = c.getCourseName();
        String cStatus = c.getStatus();
        String cStart = DateFormat.format("MM/dd/yyyy", c.getCourseStart()).toString();
        String cEnd = DateFormat.format("MM/dd/yyyy", c.getCourseEnd()).toString();
        String cNote = c.getCourseNote();

        editCourseName.setText(cName);
        editSpinStatus.setSelection(getIndex(editSpinStatus, cStatus));
        editCourseStart.setText(cStart);
        editCourseEnd.setText(cEnd);
        editNote.setText(cNote);
    }

    // Method to get spinner index
    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }
        return 0;
    }

    // Method to set date chosen for course start and end dates
    private void setDatePicker() {
        editCourseStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerV = findViewById(R.id.eDate_updateCourseStart);
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        editCourseEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerV = findViewById(R.id.eDate_updateCourseEnd);
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
            Intent intent = new Intent(EditCourseActivity.this, HomeActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
