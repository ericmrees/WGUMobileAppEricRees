package com.example.wgumobileappericrees.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

// Define table name
@Entity(tableName = "course_table",
        foreignKeys = @ForeignKey(
                entity = Term.class,
                parentColumns = "termId",
                childColumns = "cTermId",
                onDelete = ForeignKey.CASCADE
        )
)
public class Course {
    // Create course id column
    @PrimaryKey(autoGenerate = true)
    private int courseId;

    // Create course term id column
    @ColumnInfo(name = "cTermId", index = true)
    private int cTermId;

    // Create course name column
    @ColumnInfo(name = "courseName")
    private String courseName;

    // Create course start column
    @ColumnInfo(name = "courseStart")
    private Date courseStart;

    // Create course end column
    @ColumnInfo(name = "courseEnd")
    private Date courseEnd;

    // Create course status column
    @ColumnInfo(name = "status")
    private String status;

    // Create course notes column
    @ColumnInfo(name = "courseNote")
    private String courseNote;

    // Create constructor
    /*public Course(int courseId, int cTermId, String courseName, Date courseStart, Date courseEnd, String status, String courseNote) {
        this.courseId = courseId;
        this.cTermId = cTermId;
        this.courseName = courseName;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.status = status;
        this.courseNote = courseNote;
    }*/

    // Create getter and setter
    public int getCourseId() { return courseId; }

    public void setCourseId(int courseId) { this.courseId = courseId; }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getCourseStart() {
        return courseStart;
    }

    public void setCourseStart(Date courseStart) {
        this.courseStart = courseStart;
    }

    public Date getCourseEnd() {
        return courseEnd;
    }

    public void setCourseEnd(Date courseEnd) {
        this.courseEnd = courseEnd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCTermId() {
        return cTermId;
    }

    public void setCTermId(int cTermId) { this.cTermId = cTermId; }

    public String getCourseNote() {
        return courseNote;
    }

    public void setCourseNote(String courseNote) { this.courseNote = courseNote; }

    // Create toString
    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", courseStart='" + courseStart + '\'' +
                ", courseEnd='" + courseEnd + '\'' +
                ", status='" + status + '\'' +
                ", cTermId='" + cTermId + '\'' +
                ", courseNote='" + courseNote + '\'' +
                '}';
    }
}