package com.example.wgumobileappericrees.Utility;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wgumobileappericrees.Database.WGUMobileDB;
import com.example.wgumobileappericrees.Entity.Assessment;
import com.example.wgumobileappericrees.Entity.Course;
import com.example.wgumobileappericrees.Entity.Mentor;
import com.example.wgumobileappericrees.Entity.Term;

import java.util.Calendar;
import java.util.List;

public class TestData extends AppCompatActivity {
    Term testTerm1 = new Term();
    Term testTerm2 = new Term();
    Course testCourse1 = new Course();
    Course testCourse2 = new Course();
    Course testCourse3 = new Course();
    Course testCourse4 = new Course();
    Assessment testAssessment1 = new Assessment();
    Assessment testAssessment2 = new Assessment();
    Mentor testMentor1 = new Mentor();
    Mentor testMentor2 = new Mentor();

    WGUMobileDB db;

    // Method to add all test data (terms, courses, assessments, and mentors)
    public void addTestData(Context context) {
        db = WGUMobileDB.getInstance(context);
        try {
            insertTerms();
            insertCourses();
            insertAssessments();
            insertMentors();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to add all test terms
    private void insertTerms() {
        Calendar start;
        Calendar end;

        // Set up test term 1
        start = Calendar.getInstance();
        end = Calendar.getInstance();
        start.add(Calendar.MONTH, 0);
        end.add(Calendar.MONTH, 5);
        testTerm1.setTermName("Term 1");
        testTerm1.setTermStart(start.getTime());
        testTerm1.setTermEnd(end.getTime());

        // Set up test term 2
        start = Calendar.getInstance();
        end = Calendar.getInstance();
        start.add(Calendar.MONTH, 5);
        end.add(Calendar.MONTH, 10);
        testTerm2.setTermName("Term 2");
        testTerm2.setTermStart(start.getTime());
        testTerm2.setTermEnd(end.getTime());

        db.termDAO().insertAll(testTerm1, testTerm2);
    }

    // Method to add all test courses
    private void insertCourses() {
        Calendar start;
        Calendar end;
        List<Term> tList = db.termDAO().getTermList();
        if (tList == null) return;

        // Set up test course 1
        start = Calendar.getInstance();
        end = Calendar.getInstance();
        start.add(Calendar.MONTH, 0);
        end.add(Calendar.MONTH, 1);
        testCourse1.setCourseName("Software I");
        testCourse1.setCourseStart(start.getTime());
        testCourse1.setCourseEnd(end.getTime());
        testCourse1.setStatus("Completed");
        testCourse1.setCourseNote("Excited to start my new course!");
        testCourse1.setCTermId(tList.get(0).getTermId());

        // Set up test course 2
        start = Calendar.getInstance();
        end = Calendar.getInstance();
        start.add(Calendar.MONTH, 1);
        end.add(Calendar.MONTH, 2);
        testCourse2.setCourseName("OSs for Programmers");
        testCourse2.setCourseStart(start.getTime());
        testCourse2.setCourseEnd(end.getTime());
        testCourse2.setStatus("Completed");
        testCourse2.setCourseNote("Excited to start my new course!");
        testCourse2.setCTermId(tList.get(0).getTermId());

        // Set up test course 3
        start = Calendar.getInstance();
        end = Calendar.getInstance();
        start.add(Calendar.MONTH, 2);
        end.add(Calendar.MONTH, 4);
        testCourse3.setCourseName("Software II");
        testCourse3.setCourseStart(start.getTime());
        testCourse3.setCourseEnd(end.getTime());
        testCourse3.setStatus("Dropped");
        testCourse3.setCourseNote("Excited to start my new course!");
        testCourse3.setCTermId(tList.get(0).getTermId());

        // Set up test course 4
        start = Calendar.getInstance();
        end = Calendar.getInstance();
        start.add(Calendar.MONTH, 4);
        end.add(Calendar.MONTH, 5);
        testCourse4.setCourseName("Mobile App");
        testCourse4.setCourseStart(start.getTime());
        testCourse4.setCourseEnd(end.getTime());
        testCourse4.setStatus("Completed");
        testCourse4.setCourseNote("Excited to start my new course!");
        testCourse4.setCTermId(tList.get(0).getTermId());

        db.courseDAO().insertAll(testCourse1, testCourse2, testCourse3, testCourse4);
    }

    // Method to add all test assessments
    private void insertAssessments() {
        Calendar start;
        Calendar end;
        List<Term> tList = db.termDAO().getTermList();
        List<Course> cList = db.courseDAO().getCourseList(tList.get(0).getTermId());
        if (cList == null) return;

        // Set up test assessment 1
        start = Calendar.getInstance();
        end = Calendar.getInstance();
        start.add(Calendar.MONTH, 0);
        end.add(Calendar.MONTH, 1);
        testAssessment1.setAssessmentName("Inventory App");
        testAssessment1.setAssessmentStart(start.getTime());
        testAssessment1.setAssessmentEnd(end.getTime());
        testAssessment1.setType("Performance");
        testAssessment1.setACourseId(cList.get(0).getCourseId());

        // Set up test assessment 2
        start = Calendar.getInstance();
        end = Calendar.getInstance();
        start.add(Calendar.MONTH, 1);
        end.add(Calendar.MONTH, 1);
        testAssessment2.setAssessmentName("Software I Exam");
        testAssessment2.setAssessmentStart(start.getTime());
        testAssessment2.setAssessmentEnd(end.getTime());
        testAssessment2.setType("Objective");
        testAssessment2.setACourseId(cList.get(0).getCourseId());

        db.assessmentDAO().insertAll(testAssessment1, testAssessment2);
    }

    // Method to add all test mentors
    private void insertMentors() {
        List<Term> tList = db.termDAO().getTermList();
        List<Course> cList = db.courseDAO().getCourseList(tList.get(0).getTermId());
        if (cList == null) return;

        // Set up test mentor 1
        testMentor1.setMentorName("Eric Rees");
        testMentor1.setMentorPhone("8015461148");
        testMentor1.setMentorEmail("eric@wgu.edu");
        testMentor1.setMCourseId(cList.get(0).getCourseId());

        // Set up test mentor 2
        testMentor2.setMentorName("Adam Fischer");
        testMentor2.setMentorPhone("8017674455");
        testMentor2.setMentorEmail("adamf@wgu.edu");
        testMentor2.setMCourseId(cList.get(0).getCourseId());

        db.mentorDAO().insertAll(testMentor1, testMentor2);
    }
}
