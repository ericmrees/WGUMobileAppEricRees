package com.example.wgumobileappericrees.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.wgumobileappericrees.Entity.Course;

import java.util.List;

@Dao
public interface CourseDAO {
    // Insert course query
    @Insert
    void insert(Course course);

    // Insert all courses query
    @Insert
    void insertAll(Course... course);

    // Update course query
    @Update
    void update(Course course);

    // Delete course query
    @Delete
    void delete(Course course);

    // Delete all courses query
    @Delete
    void reset(List<Course> course);

    // Get course list
    @Query("SELECT * FROM course_table WHERE cTermId = :termId ORDER BY courseId")
    List<Course> getCourseList(int termId);

    // Get all courses query
    @Query("SELECT * FROM course_table")
    List<Course> getAllCourses();

    // Get a course by termId & courseId
    @Query("SELECT * FROM course_table WHERE cTermId = :termId AND courseId = :courseId ORDER BY courseId")
    Course getCourse(int termId, int courseId);

    // Get current course
    @Query("SELECT * FROM course_table WHERE cTermId = :termId ORDER BY courseId DESC LIMIT 1")
    Course getCurrentCourse(int termId);
}
