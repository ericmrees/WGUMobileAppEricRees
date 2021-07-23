package com.example.wgumobileappericrees.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.wgumobileappericrees.DAO.AssessmentDAO;
import com.example.wgumobileappericrees.DAO.CourseDAO;
import com.example.wgumobileappericrees.DAO.MentorDAO;
import com.example.wgumobileappericrees.DAO.TermDAO;
import com.example.wgumobileappericrees.Entity.Assessment;
import com.example.wgumobileappericrees.Entity.Course;
import com.example.wgumobileappericrees.Entity.Mentor;
import com.example.wgumobileappericrees.Entity.Term;
import com.example.wgumobileappericrees.Utility.DateConverter;

// Add database entities
@Database(entities = {Term.class, Course.class, Assessment.class, Mentor.class}, version = 10, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class WGUMobileDB extends RoomDatabase {
    // Create database instance
    private static WGUMobileDB instance;
    // Define database name
    private static final String DATABASE_NAME = "WGUMobileDB.db";
    // Set instance for database
    public synchronized static WGUMobileDB getInstance(Context context) {
        if (instance == null) {
            // When database is null initialize database
            instance = Room.databaseBuilder(context.getApplicationContext(), WGUMobileDB.class, DATABASE_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    // Create DAOs
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();
    public abstract MentorDAO mentorDAO();
}
