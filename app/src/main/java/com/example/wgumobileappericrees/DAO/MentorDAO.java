package com.example.wgumobileappericrees.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.wgumobileappericrees.Entity.Mentor;

import java.util.List;

@Dao
public interface MentorDAO {
    // Insert mentor query
    @Insert
    void insert(Mentor mentor);

    // Insert all mentors query
    @Insert
    void insertAll(Mentor... mentor);

    // Update mentor query
    @Update
    void update(Mentor mentor);

    // Delete mentor query
    @Delete
    void delete(Mentor mentor);

    // Delete all mentors query
    @Delete
    void reset(List<Mentor> mentor);

    // Get mentor list
    @Query("SELECT * FROM mentor_table WHERE mCourseId = :courseId ORDER BY mentorId")
    List<Mentor> getMentorList(int courseId);

    // Get all mentors query
    @Query("SELECT * FROM mentor_table")
    List<Mentor> getAllMentors();

    // Get a mentor by mentorId
    @Query("SELECT * FROM mentor_table WHERE mCourseId = :courseId AND mentorId = :mentorId")
    Mentor getMentor(int courseId, int mentorId);
}