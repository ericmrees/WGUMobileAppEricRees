package com.example.wgumobileappericrees.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wgumobileappericrees.Database.WGUMobileDB;
import com.example.wgumobileappericrees.Entity.Mentor;
import com.example.wgumobileappericrees.R;

import java.util.List;

public class MentorDetailsActivity extends AppCompatActivity {
    WGUMobileDB db;
    boolean deletedMentor;
    int termId;
    int courseId;
    int mentorId;
    Mentor currentMentor;
    Intent intent;
    TextView txtMentorName;
    TextView txtMentorPhone;
    TextView txtMentorEmail;
    String mName;
    String mPhone;
    String mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_details);
        intent = getIntent();
        db = WGUMobileDB.getInstance(getApplicationContext());
        termId = intent.getIntExtra("termId", -1);
        courseId = intent.getIntExtra("courseId", -1);
        mentorId = intent.getIntExtra("mentorId", -1);
        mName = getIntent().getStringExtra("mentorName");
        mPhone = getIntent().getStringExtra("mentorPhone");
        mEmail = getIntent().getStringExtra("mentorEmail");
        txtMentorName = findViewById(R.id.eTxt_mentorNameDetails);
        txtMentorPhone = findViewById(R.id.eTxt_mentorPhoneDetails);
        txtMentorEmail = findViewById(R.id.eTxt_mentorEmailDetails);

        // Set mentor values
        List<Mentor> allMentors = db.mentorDAO().getAllMentors();
        for (Mentor m:allMentors) {
            if (m.getMentorId() == mentorId) currentMentor = m;
        }
        if (currentMentor != null) {
            mName = currentMentor.getMentorName();
            mPhone = currentMentor.getMentorPhone();
            mEmail = currentMentor.getMentorEmail();
        }
        if (mentorId != -1) {
            txtMentorName.setText(mName);
            txtMentorPhone.setText(mPhone);
            txtMentorEmail.setText(mEmail);
        }
    }

    // Method to delete mentor
    private void deleteMentor() {
        Mentor m = new Mentor();
        m = db.mentorDAO().getMentor(courseId, mentorId);
        db.mentorDAO().delete(m);
        Intent intent = new Intent(this, CourseDetailsActivity.class);
        intent.putExtra("termId", termId);
        intent.putExtra("courseId", courseId);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), mName + " has been deleted!", Toast.LENGTH_LONG).show();
        deletedMentor = true;
    }

    // Menu setup
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mentor_details, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // WGU icon to send to home screen
        if (id == R.id.home) {
            Intent intent = new Intent(MentorDetailsActivity.this, HomeActivity.class);
            startActivity(intent);
        }

        // Delete icon to delete mentor
        if (id == R.id.delete_mentor) {
            deleteMentor();
        }

        // Edit icon to send to edit mentor screen
        if (id == R.id.edit_mentor) {
            Intent intent = new Intent(MentorDetailsActivity.this, EditMentorActivity.class);
            intent.putExtra("termId", termId);
            intent.putExtra("courseId", courseId);
            intent.putExtra("mentorId", mentorId);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}