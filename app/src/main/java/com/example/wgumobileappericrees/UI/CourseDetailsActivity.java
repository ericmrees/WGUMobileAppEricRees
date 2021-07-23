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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wgumobileappericrees.Database.WGUMobileDB;
import com.example.wgumobileappericrees.Entity.Assessment;
import com.example.wgumobileappericrees.Entity.Course;
import com.example.wgumobileappericrees.Entity.Mentor;
import com.example.wgumobileappericrees.R;
import com.example.wgumobileappericrees.Utility.NotificationReceiver;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseDetailsActivity extends AppCompatActivity {
    WGUMobileDB db;
    LinearLayoutManager linearLayoutManager;
    AssessmentAdapter aAdapter;
    MentorAdapter mAdapter;
    FloatingActionButton FABAddAssessment;
    FloatingActionButton FABAddMentor;
    Button btnCourseStartAlert;
    Button btnCourseEndAlert;
    Button btnShareNotes;
    Intent intent;
    boolean deletedCourse;
    int courseId;
    int termId;
    public static int numAssessments;
    public static int numMentors;
    Course currentCourse;
    List<Assessment> assessmentList;
    List<Mentor> mentorList;
    Date cStartDate;
    Date cEndDate;
    Date cDate;
    Long alertTime;
    RecyclerView rvAssessment;
    RecyclerView rvMentor;
    String cName;
    String cStatus;
    String cStart;
    String cEnd;
    String cNote;
    TextView txtCourseName;
    TextView txtStatus;
    TextView txtCourseStart;
    TextView txtCourseEnd;
    TextView txtCourseNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        db = WGUMobileDB.getInstance(getApplicationContext());
        intent = getIntent();
        termId = intent.getIntExtra("termId", -1);
        courseId = intent.getIntExtra("courseId", -1);
        cName = getIntent().getStringExtra("courseName");
        cStatus = getIntent().getStringExtra("status");
        cStart = getIntent().getStringExtra("courseStart");
        cEnd = getIntent().getStringExtra("courseEnd");
        cNote = getIntent().getStringExtra("courseNote");
        rvAssessment = findViewById(R.id.rv_assessments);
        rvMentor = findViewById(R.id.rv_mentors);
        FABAddAssessment = findViewById(R.id.FAB_addAssessment);
        FABAddMentor = findViewById(R.id.FAB_addMentor);
        btnCourseStartAlert = findViewById(R.id.btn_courseStartAlertDetails);
        btnCourseEndAlert = findViewById(R.id.btn_courseEndAlertDetails);
        btnShareNotes = findViewById(R.id.btn_shareNotes);
        txtCourseName = findViewById(R.id.eTxt_courseNameDetails);
        txtStatus = findViewById(R.id.eTxt_statusDetails);
        txtCourseStart = findViewById(R.id.eTxt_courseStartDetails);
        txtCourseEnd = findViewById(R.id.eTxt_courseEndDetails);
        txtCourseNote = findViewById(R.id.eTxt_notesDetails);
        assessmentList = db.assessmentDAO().getAssessmentList(courseId);
        mentorList = db.mentorDAO().getMentorList(courseId);

        // Set course values
        List<Course> allCourses = db.courseDAO().getAllCourses();
        for (Course c:allCourses) {
            if (c.getCourseId() == courseId) currentCourse = c;
        }
        if (currentCourse != null) {
            cName = currentCourse.getCourseName();
            cStatus = currentCourse.getStatus();
            cStart = DateFormat.format("MM/dd/yyyy", currentCourse.getCourseStart()).toString();
            cEnd = DateFormat.format("MM/dd/yyyy", currentCourse.getCourseEnd()).toString();
            cNote = currentCourse.getCourseNote();
        }
        if (courseId != -1) {
            txtCourseName.setText(cName);
            txtStatus.setText(cStatus);
            txtCourseStart.setText(cStart);
            txtCourseEnd.setText(cEnd);
            txtCourseNote.setText(cNote);
        }

        setAssessmentAdapter();
        setMentorAdapter();
        //createNotificationChannel();

        // Set add assessment floating action button
        FABAddAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseDetailsActivity.this, AddAssessmentActivity.class);
                intent.putExtra("termId", termId);
                intent.putExtra("courseId", courseId);
                startActivity(intent);
            }
        });

        // Set add mentor floating action button
        FABAddMentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseDetailsActivity.this, AddMentorActivity.class);
                intent.putExtra("termId", termId);
                intent.putExtra("courseId", courseId);
                startActivity(intent);
            }
        });

        // Set course start alert button
        btnCourseStartAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addCourseStartAlert();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        // Set course end alert button
        btnCourseEndAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addCourseEndAlert();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        // Set share notes button
        btnShareNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send intent
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, cNote);
                sendIntent.putExtra(Intent.EXTRA_TITLE, cName + " Notes");
                sendIntent.setType("text/plain");
                // Share intent
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });
    }

    // Set assessment adapter for recycler view
    private void setAssessmentAdapter() {
        // Initialize linear layout manager
        linearLayoutManager = new LinearLayoutManager(this);
        // Set layout manager
        rvAssessment.setLayoutManager(linearLayoutManager);
        // Initialize adapter
        aAdapter = new AssessmentAdapter(this);
        // Set adapter
        rvAssessment.setAdapter(aAdapter);
        // Filter courses by term
        List<Assessment> filteredAssessments = new ArrayList<>();
        for (Assessment a: db.assessmentDAO().getAllAssessments()) {
            if (a.getACourseId() == courseId) filteredAssessments.add(a);
        }
        numAssessments = filteredAssessments.size();
        aAdapter.setWords(filteredAssessments);
    }

    // Set mentor adapter for recycler view
    private void setMentorAdapter() {
        // Initialize linear layout manager
        linearLayoutManager = new LinearLayoutManager(this);
        // Set layout manager
        rvMentor.setLayoutManager(linearLayoutManager);
        // Initialize adapter
        mAdapter = new MentorAdapter(this);
        // Set adapter
        rvMentor.setAdapter(mAdapter);
        // Filter courses by term
        List<Mentor> filteredMentors = new ArrayList<>();
        for (Mentor m: db.mentorDAO().getAllMentors()) {
            if (m.getMCourseId() == courseId) filteredMentors.add(m);
        }
        numMentors = filteredMentors.size();
        mAdapter.setWords(filteredMentors);
    }

    // Method to delete course
    private void deleteCourse() {
        Course c = new Course();
        c = db.courseDAO().getCourse(termId, courseId);
        db.courseDAO().delete(c);
        Intent intent = new Intent(CourseDetailsActivity.this, TermDetailsActivity.class);
        intent.putExtra("termId", termId);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), cName + " has been deleted!", Toast.LENGTH_LONG).show();
        if (numAssessments != 0) {
            Toast.makeText(this, "Associated assessments have been deleted!", Toast.LENGTH_SHORT).show();
        }
        if (numMentors != 0) {
            Toast.makeText(this, "Associated mentors have been deleted!", Toast.LENGTH_SHORT).show();
        }
        deletedCourse = true;
    }

    // Method to add alert for course start date
    public void addCourseStartAlert() throws ParseException {
        Course c = new Course();
        c = db.courseDAO().getCurrentCourse(termId);
        courseId = c.getCourseId();
        String startText = cName + " starts today!";
        setCourseStartAlert(courseId, cStartDate, cName, startText);
    }

    // Method to set up alert for course start date
    private void setCourseStartAlert(int id, Date date, String title, String text) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String stringDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        cStartDate = dateFormat.parse(cStart);
        this.cDate = dateFormat.parse(stringDate);
        date = cStartDate;
        alertTime = date.getTime();
        //alertTime = DateConverter.dateToTimestamp(date);
        if (cStartDate.compareTo(cDate) < 0) {
            Toast.makeText(getApplicationContext(), "Can't set an alert, course start date has passed!", Toast.LENGTH_SHORT).show();
            return;
        }
        NotificationReceiver.setCourseAlert(getApplicationContext(), id, alertTime, title, text);
        Toast.makeText(getApplicationContext(), "Alert for " + title + " start date added!", Toast.LENGTH_LONG).show();
    }

    // Method to add alert for course end date
    public void addCourseEndAlert() throws ParseException {
        Course c = new Course();
        c = db.courseDAO().getCurrentCourse(termId);
        courseId = c.getCourseId();
        String endText = cName + " ends today!";
        setCourseEndAlert(courseId, cEndDate, cName, endText);
    }

    // Method to set up alert for course end date
    private void setCourseEndAlert(int id, Date date, String title, String text) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String stringDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        cEndDate = dateFormat.parse(cEnd);
        this.cDate = dateFormat.parse(stringDate);
        date = cEndDate;
        alertTime = date.getTime();
        if (cEndDate.compareTo(cDate) < 0) {
            Toast.makeText(getApplicationContext(), "Can't set an alert, course end date has passed!", Toast.LENGTH_SHORT).show();
            return;
        }
        NotificationReceiver.setCourseAlert(getApplicationContext(), id, alertTime, title, text);
        Toast.makeText(getApplicationContext(), "Alert for " + title + " end date added!", Toast.LENGTH_LONG).show();
    }

    // Menu setup
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_details, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // WGU icon to send to home screen
        if (id == R.id.home) {
            Intent intent = new Intent(CourseDetailsActivity.this, HomeActivity.class);
            startActivity(intent);
        }

        // Delete icon to delete course
        if (id == R.id.delete_course) {
            deleteCourse();
        }

        // Edit icon to send to edit course screen
        if (id == R.id.edit_course) {
            Intent intent = new Intent(CourseDetailsActivity.this, EditCourseActivity.class);
            intent.putExtra("termId", termId);
            intent.putExtra("courseId", courseId);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
