package com.example.wgumobileappericrees.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wgumobileappericrees.Database.WGUMobileDB;
import com.example.wgumobileappericrees.Entity.Mentor;
import com.example.wgumobileappericrees.R;

public class EditMentorActivity extends AppCompatActivity {
    WGUMobileDB db;
    boolean updatedMentor;
    int termId;
    int courseId;
    int mentorId;
    Intent intent;
    EditText editMentorName;
    EditText editMentorPhone;
    EditText editMentorEmail;
    Button btnUpdateMentor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mentor);
        intent = getIntent();
        db = WGUMobileDB.getInstance(getApplicationContext());
        termId = intent.getIntExtra("termId", -1);
        courseId = intent.getIntExtra("courseId", -1);
        mentorId = intent.getIntExtra("mentorId", -1);
        editMentorName = findViewById(R.id.eTxt_updateMentorName);
        editMentorPhone = findViewById(R.id.ePhone_updateMentorPhone);
        editMentorEmail = findViewById(R.id.eEmail_updateMentorEmail);
        btnUpdateMentor = findViewById(R.id.btn_updateMentor);

        setMentorValues();

        // Set update mentor button
        btnUpdateMentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMentor();
                if (updatedMentor) {
                    Intent intent = new Intent(EditMentorActivity.this, CourseDetailsActivity.class);
                    intent.putExtra("termId", termId);
                    intent.putExtra("courseId", courseId);
                    intent.putExtra("mentorId", mentorId);
                    startActivity(intent);
                }
            }
        });
    }

    // Method to update existing mentor
    private void updateMentor() {
        String mName = editMentorName.getText().toString();
        String mPhone = editMentorPhone.getText().toString();
        String mEmail = editMentorEmail.getText().toString();

        if (mName.trim().isEmpty() || mPhone.trim().isEmpty() || mEmail.trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "All fields are required, can't be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        Mentor m = new Mentor();
        m.setMentorId(mentorId);
        m.setMCourseId(courseId);
        m.setMentorName(mName);
        m.setMentorPhone(mPhone);
        m.setMentorEmail(mEmail);
        db.mentorDAO().update(m);
        Toast.makeText(getApplicationContext(), mName + " was updated!", Toast.LENGTH_SHORT).show();
        updatedMentor = true;
    }

    // Method to set mentor values
    private void setMentorValues() {
        Mentor m = new Mentor();
        m = db.mentorDAO().getMentor(courseId, mentorId);
        String mName = m.getMentorName();
        String mPhone = m.getMentorPhone();
        String mEmail = m.getMentorEmail();

        editMentorName.setText(mName);
        editMentorPhone.setText(mPhone);
        editMentorEmail.setText(mEmail);
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
            Intent intent = new Intent(EditMentorActivity.this, HomeActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}