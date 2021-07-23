package com.example.wgumobileappericrees.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

// Define table name
@Entity(tableName = "assessment_table",
        foreignKeys = @ForeignKey(
                entity = Course.class,
                parentColumns = "courseId",
                childColumns = "aCourseId",
                onDelete = ForeignKey.CASCADE
        )
)
public class Assessment {
    // Create assessment id column
    @PrimaryKey(autoGenerate = true)
    private int assessmentId;

    // Create assessment course id column
    @ColumnInfo(name = "aCourseId", index = true)
    private int aCourseId;

    // Create assessment name column
    @ColumnInfo(name = "assessmentName")
    private String assessmentName;

    // Create type column
    @ColumnInfo(name = "type")
    private String type;

    // Create assessment start column
    @ColumnInfo(name = "assessmentStart")
    private Date assessmentStart;

    // Create assessment end column
    @ColumnInfo(name = "assessmentEnd")
    private Date assessmentEnd;

    // Create constructor
    /*public Assessment(int assessmentId, int aCourseId, String assessmentName, String type, Date assessmentStart, Date assessmentEnd) {
        this.assessmentId = assessmentId;
        this.aCourseId = aCourseId;
        this.assessmentName = assessmentName;
        this.type = type;
        this.assessmentStart = assessmentStart;
        this.assessmentEnd = assessmentEnd;
    }*/

    // Create getter and setter
    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getAssessmentStart() {
        return assessmentStart;
    }

    public void setAssessmentStart(Date assessmentStart) { this.assessmentStart = assessmentStart; }

    public Date getAssessmentEnd() {
        return assessmentEnd;
    }

    public void setAssessmentEnd(Date assessmentEnd) {
        this.assessmentEnd = assessmentEnd;
    }

    public int getACourseId() { return aCourseId; }

    public void setACourseId(int aCourseId) { this.aCourseId = aCourseId; }

    // Create toString
    @Override
    public String toString() {
        return "Assessment{" +
                "assessmentId=" + assessmentId +
                ", assessmentName='" + assessmentName + '\'' +
                ", type='" + type + '\'' +
                ", assessmentStart='" + assessmentStart + '\'' +
                ", assessmentEnd='" + assessmentEnd + '\'' +
                ", aCourseId=" + aCourseId +
                '}';
    }
}
