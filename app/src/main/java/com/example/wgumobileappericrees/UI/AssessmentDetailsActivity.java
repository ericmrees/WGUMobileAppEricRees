package com.example.wgumobileappericrees.UI;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wgumobileappericrees.Database.WGUMobileDB;
import com.example.wgumobileappericrees.Entity.Assessment;
import com.example.wgumobileappericrees.R;
import com.example.wgumobileappericrees.Utility.NotificationReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AssessmentDetailsActivity extends AppCompatActivity {
    WGUMobileDB db;
    Button btnAssessmentStartAlert;
    Button btnAssessmentEndAlert;
    boolean deletedAssessment;
    int termId;
    int courseId;
    int assessmentId;
    Long alertTime;
    Assessment currentAssessment;
    Date aStartDate;
    Date aEndDate;
    Date aDate;
    Intent intent;
    TextView txtAssessmentName;
    TextView txtType;
    TextView txtAssessmentStart;
    TextView txtAssessmentEnd;
    String aName;
    String aType;
    String aStart;
    String aEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);
        intent = getIntent();
        db = WGUMobileDB.getInstance(getApplicationContext());
        termId = intent.getIntExtra("termId", -1);
        courseId = intent.getIntExtra("courseId", -1);
        assessmentId = intent.getIntExtra("assessmentId", -1);
        aName = getIntent().getStringExtra("assessmentName");
        aType = getIntent().getStringExtra("type");
        aStart = getIntent().getStringExtra("assessmentStart");
        aEnd = getIntent().getStringExtra("assessmentEnd");
        btnAssessmentStartAlert = findViewById(R.id.btn_assessmentStartAlertDetails);
        btnAssessmentEndAlert = findViewById(R.id.btn_assessmentEndAlertDetails);
        txtAssessmentName = findViewById(R.id.eTxt_assessmentNameDetails);
        txtType = findViewById(R.id.eTxt_typeDetails);
        txtAssessmentStart = findViewById(R.id.eTxt_assessmentStartDetails);
        txtAssessmentEnd = findViewById(R.id.eTxt_assessmentEndDetails);

        // Set assessment values
        List<Assessment> allAssessments = db.assessmentDAO().getAllAssessments();
        for (Assessment a:allAssessments) {
            if (a.getAssessmentId() == assessmentId) currentAssessment = a;
        }
        if (currentAssessment != null) {
            aName = currentAssessment.getAssessmentName();
            aType = currentAssessment.getType();
            aStart = DateFormat.format("MM/dd/yyyy", currentAssessment.getAssessmentStart()).toString();
            aEnd = DateFormat.format("MM/dd/yyyy", currentAssessment.getAssessmentEnd()).toString();
        }
        if (assessmentId != -1) {
            txtAssessmentName.setText(aName);
            txtType.setText(aType);
            txtAssessmentStart.setText(aStart);
            txtAssessmentEnd.setText(aEnd);
        }

        //createNotificationChannel();

        // Set assessment start alert button
        btnAssessmentStartAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addAssessmentStartAlert();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        // Set assessment end alert button
        btnAssessmentEndAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addAssessmentEndAlert();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Method to delete assessment
    private void deleteAssessment() {
        Assessment a = new Assessment();
        a = db.assessmentDAO().getAssessment(courseId, assessmentId);
        db.assessmentDAO().delete(a);
        Intent intent = new Intent(AssessmentDetailsActivity.this, CourseDetailsActivity.class);
        intent.putExtra("termId", termId);
        intent.putExtra("courseId", courseId);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), aName + " has been deleted!", Toast.LENGTH_LONG).show();
        deletedAssessment = true;
    }

    // Method to add alert for assessment start date
    public void addAssessmentStartAlert() throws ParseException {
        Assessment a = new Assessment();
        a = db.assessmentDAO().getCurrentAssessment(courseId);
        assessmentId = a.getAssessmentId();
        String startText = aName + " starts today!";
        setAssessmentStartAlert(assessmentId, aStartDate, aName, startText);
    }

    // Method to set up alert for assessment start date
    private void setAssessmentStartAlert(int id, Date date, String title, String text) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String stringDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        aStartDate = dateFormat.parse(aStart);
        this.aDate = dateFormat.parse(stringDate);
        date = aStartDate;
        alertTime = date.getTime();
        if (aStartDate.compareTo(aDate) < 0) {
            Toast.makeText(getApplicationContext(), "Can't set an alert, assessment start date has passed!", Toast.LENGTH_SHORT).show();
            return;
        }
        NotificationReceiver.setAssessmentAlert(getApplicationContext(), id, alertTime, title, text);
        Toast.makeText(getApplicationContext(), "Alert for " + title + " start date added!", Toast.LENGTH_LONG).show();
    }

    // Method to add alert for assessment end date
    public void addAssessmentEndAlert() throws ParseException {
        Assessment a = new Assessment();
        a = db.assessmentDAO().getCurrentAssessment(courseId);
        assessmentId = a.getAssessmentId();
        String endText = aName + " ends today!";
        setAssessmentEndAlert(assessmentId, aEndDate, aName, endText);
    }

    // Method to set up alert for assessment end date
    private void setAssessmentEndAlert(int id, Date date, String title, String text) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String stringDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        aEndDate = dateFormat.parse(aEnd);
        this.aDate = dateFormat.parse(stringDate);
        date = aEndDate;
        alertTime = date.getTime();
        if (aEndDate.compareTo(aDate) < 0) {
            Toast.makeText(getApplicationContext(), "Can't set an alert, assessment end date has passed!", Toast.LENGTH_SHORT).show();
            return;
        }
        NotificationReceiver.setAssessmentAlert(getApplicationContext(), id, alertTime, title, text);
        Toast.makeText(getApplicationContext(), "Alert for " + title + " end date added!", Toast.LENGTH_LONG).show();
    }

    // Menu setup
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessment_details, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // WGU icon to send to home screen
        if (id == R.id.home) {
            Intent intent = new Intent(AssessmentDetailsActivity.this, HomeActivity.class);
            startActivity(intent);
        }

        // Delete icon to delete assessment
        if (id == R.id.delete_assessment) {
            deleteAssessment();
        }

        // Edit icon to send to edit assessment screen
        if (id == R.id.edit_assessment) {
            Intent intent = new Intent(AssessmentDetailsActivity.this, EditAssessmentActivity.class);
            intent.putExtra("termId", termId);
            intent.putExtra("courseId", courseId);
            intent.putExtra("assessmentId", assessmentId);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
