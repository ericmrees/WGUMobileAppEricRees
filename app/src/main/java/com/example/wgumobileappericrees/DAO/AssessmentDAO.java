package com.example.wgumobileappericrees.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.wgumobileappericrees.Entity.Assessment;

import java.util.List;

@Dao
public interface AssessmentDAO {
    // Insert assessment query
    @Insert
    void insert(Assessment assessment);

    // Insert all assessments query
    @Insert
    void insertAll(Assessment... assessment);

    // Update assessment query
    @Update
    void update(Assessment assessment);

    // Delete assessment query
    @Delete
    void delete(Assessment assessment);

    // Delete all assessments query
    @Delete
    void reset(List<Assessment> assessment);

    // Get assessment list
    @Query("SELECT * FROM assessment_table WHERE aCourseId = :courseId ORDER BY assessmentId")
    List<Assessment> getAssessmentList(int courseId);

    // Get all assessments query
    @Query("SELECT * FROM assessment_table")
    List<Assessment> getAllAssessments();

    // Get an assessment by assessmentId
    @Query("SELECT * FROM assessment_table WHERE aCourseId = :courseId AND assessmentId = :assessmentId")
    Assessment getAssessment(int courseId, int assessmentId);

    // Get current assessment
    @Query("SELECT * FROM assessment_table WHERE aCourseId = :courseId ORDER BY assessmentId DESC LIMIT 1")
    Assessment getCurrentAssessment(int courseId);
}
