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
import com.example.wgumobileappericrees.Entity.Assessment;
import com.example.wgumobileappericrees.R;
import com.example.wgumobileappericrees.Utility.DatePickerFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditAssessmentActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    WGUMobileDB db;
    boolean updatedAssessment;
    int termId;
    int courseId;
    int assessmentId;
    Intent intent;
    private TextView datePickerV;
    SimpleDateFormat dateFormat;
    Date aDate;
    EditText editAssessmentName;
    Spinner editSpinType;
    String editType;
    TextView editAssessmentStart;
    TextView editAssessmentEnd;
    Button btnUpdateAssessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assessment);
        intent = getIntent();
        db = WGUMobileDB.getInstance(getApplicationContext());
        termId = intent.getIntExtra("termId", -1);
        courseId = intent.getIntExtra("courseId", -1);
        assessmentId = intent.getIntExtra("assessmentId", -1);
        editAssessmentName = findViewById(R.id.eTxt_updateAssessmentName);
        editSpinType = findViewById(R.id.spn_updateType);
        editAssessmentStart = findViewById(R.id.eDate_updateAssessmentStart);
        editAssessmentEnd = findViewById(R.id.eDate_updateAssessmentEnd);
        btnUpdateAssessment = findViewById(R.id.btn_updateAssessment);

        setDatePicker();
        setAssessmentValues();

        // Set assessment type spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editSpinType.setAdapter(adapter);

        editSpinType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editType = editSpinType.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Set update assessment button
        btnUpdateAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    updateAssessment();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (updatedAssessment) {
                    Intent intent = new Intent(EditAssessmentActivity.this, CourseDetailsActivity.class);
                    intent.putExtra("termId", termId);
                    intent.putExtra("courseId", courseId);
                    intent.putExtra("assessmentId", assessmentId);
                    startActivity(intent);
                }
            }
        });
    }

    // Method to update existing assessment
    private void updateAssessment() throws ParseException {
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String aName = editAssessmentName.getText().toString();
        String aStart = editAssessmentStart.getText().toString();
        String aEnd = editAssessmentEnd.getText().toString();
        String aDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        Date aStartDate = dateFormat.parse(aStart);
        Date aEndDate = dateFormat.parse(aEnd);
        this.aDate = dateFormat.parse(aDate);

        if (aName.trim().isEmpty() || aStart.trim().isEmpty() || aEnd.trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "All fields are required, can't be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (aStartDate.after(aEndDate)) {
            Toast.makeText(getApplicationContext(), "Start date needs to be before end date!", Toast.LENGTH_SHORT).show();
            return;
        }

        Assessment a = new Assessment();
        a.setAssessmentId(assessmentId);
        a.setACourseId(courseId);
        a.setAssessmentName(aName);
        a.setType(editType);
        a.setAssessmentStart(aStartDate);
        a.setAssessmentEnd(aEndDate);
        db.assessmentDAO().update(a);
        Toast.makeText(getApplicationContext(), aName + " was updated!", Toast.LENGTH_SHORT).show();
        updatedAssessment = true;
    }

    // Method to set assessment values
    private void setAssessmentValues() {
        Assessment a = new Assessment();
        a = db.assessmentDAO().getAssessment(courseId, assessmentId);
        String aName = a.getAssessmentName();
        String aType = a.getType();
        String aStart = DateFormat.format("MM/dd/yyyy", a.getAssessmentStart()).toString();
        String aEnd = DateFormat.format("MM/dd/yyyy", a.getAssessmentEnd()).toString();

        editAssessmentName.setText(aName);
        editSpinType.setSelection(getIndex(editSpinType, aType));
        editAssessmentStart.setText(aStart);
        editAssessmentEnd.setText(aEnd);
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

    // Method to set date chosen for assessment start and end dates
    private void setDatePicker() {
        editAssessmentStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerV = findViewById(R.id.eDate_updateAssessmentStart);
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        editAssessmentEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerV = findViewById(R.id.eDate_updateAssessmentEnd);
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
            Intent intent = new Intent(EditAssessmentActivity.this, HomeActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}