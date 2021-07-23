package com.example.wgumobileappericrees.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "mentor_table",
        foreignKeys = @ForeignKey(
                entity = Course.class,
                parentColumns = "courseId",
                childColumns = "mCourseId",
                onDelete = ForeignKey.CASCADE
        )
)
public class Mentor {
    // Create mentor id column
    @PrimaryKey(autoGenerate = true)
    private int mentorId;

    // Create course id column
    @ColumnInfo(name = "mCourseId", index = true)
    private int mCourseId;

    // Create mentor name column
    @ColumnInfo(name = "mentorName")
    private String mentorName;

    // Create mentor phone column
    @ColumnInfo(name = "mentorPhone")
    private String mentorPhone;

    // Create mentor email column
    @ColumnInfo(name = "mentorEmail")
    private String mentorEmail;

    // Create constructor
    /*public Mentor(int mentorId, int mCourseId, String mentorName, String mentorPhone, String mentorEmail) {
        this.mentorId = mentorId;
        this.mCourseId = mCourseId;
        this.mentorName = mentorName;
        this.mentorPhone = mentorPhone;
        this.mentorEmail = mentorEmail;
    }*/

    // Create getter and setter
    public int getMentorId() { return mentorId; }

    public void setMentorId(int mentorId) { this.mentorId = mentorId; }

    public String getMentorName() { return mentorName; }

    public void setMentorName(String mentorName) { this.mentorName = mentorName; }

    public String getMentorPhone() { return mentorPhone; }

    public void setMentorPhone(String mentorPhone) { this.mentorPhone = mentorPhone; }

    public String getMentorEmail() { return mentorEmail; }

    public void setMentorEmail(String mentorEmail) { this.mentorEmail = mentorEmail; }

    public int getMCourseId() { return mCourseId; }

    public void setMCourseId(int mCourseId) { this.mCourseId = mCourseId; }

    // Create toString
    @Override
    public String toString() {
        return "Mentor{" +
                "mentorId=" + mentorId +
                ", mentorName='" + mentorName + '\'' +
                ", mentorPhone='" + mentorPhone + '\'' +
                ", mentorEmail='" + mentorEmail + '\'' +
                ", mCourseId='" + mCourseId + '\'' +
                '}';
    }
}