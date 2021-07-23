package com.example.wgumobileappericrees.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.wgumobileappericrees.Entity.Term;

import java.util.List;

@Dao
public interface TermDAO {
    // Insert term query
    @Insert
    void insert(Term term);

    // Insert all terms query
    @Insert
    void insertAll(Term... term);

    @Update
    void update(Term term);

    // Delete term query
    @Delete
    void delete(Term term);

    // Delete all terms query
    @Delete
    void reset(List<Term> term);

    // Get term list
    @Query("SELECT * FROM term_table ORDER BY termId")
    List<Term> getTermList();

    // Get all terms query
    @Query("SELECT * FROM term_table")
    List<Term> getAllTerms();

    // Get a term by termId
    @Query("SELECT * FROM term_table WHERE termId = :termId ORDER BY termId")
    Term getTerm(int termId);
}