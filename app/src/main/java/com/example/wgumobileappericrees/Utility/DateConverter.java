package com.example.wgumobileappericrees.Utility;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    // Method to convert timestamp to date
    public static Date fromTimeStamp(Long value) { return value == null ? null : new Date(value); }

    @TypeConverter
    // Method to convert date to timestamp
    public static Long dateToTimestamp(Date date) { return date == null ? null : date.getTime(); }
}
