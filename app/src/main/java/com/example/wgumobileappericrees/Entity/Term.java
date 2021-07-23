package com.example.wgumobileappericrees.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

// Define table name
@Entity(tableName = "term_table")
public class Term {
    // Create term id column
    @PrimaryKey(autoGenerate = true)
    private int termId;

    // Create term name column
    @ColumnInfo(name = "termName")
    private String termName;

    // Create term start column
    @ColumnInfo(name = "termStart")
    private Date termStart;

    // Create term end column
    @ColumnInfo(name = "termEnd")
    private Date termEnd;

    // Generate constructor
    /*public Term(int termId, String termName, Date termStart, Date termEnd) {
        this.termId = termId;
        this.termName = termName;
        this.termStart = termStart;
        this.termEnd = termEnd;
    }*/

    // Generate getter and setter
    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public Date getTermStart() {
        return termStart;
    }

    public void setTermStart(Date termStart) {
        this.termStart = termStart;
    }

    public Date getTermEnd() {
        return termEnd;
    }

    public void setTermEnd(Date termEnd) {
        this.termEnd = termEnd;
    }

    // Generate toString
    @Override
    public String toString() {
        return "Term{" +
                "termId=" + termId +
                ", termName='" + termName + '\'' +
                ", termStart=" + termStart +
                ", termEnd=" + termEnd +
                '}';
    }
}
