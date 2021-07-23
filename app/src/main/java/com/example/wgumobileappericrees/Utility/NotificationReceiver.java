package com.example.wgumobileappericrees.Utility;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.wgumobileappericrees.R;

public class NotificationReceiver extends BroadcastReceiver {
    public static final String alert = "alertFile";
    public static final String assessmentAlert = "assessmentAlertFile";
    public static final String courseAlert = "courseAlertFile";
    public static final String nextAlert = "nextAlertId";
    public static final String Channel_Id = "WGU App";

    @Override
    public void onReceive(Context context, Intent intent) {
        String alertTitle = intent.getStringExtra("title");
        String alertText = intent.getStringExtra("text");
        int nextAlertId = intent.getIntExtra("nextAlertId", getAndIncrementNextAlertId(context));

        // Set notification channel
        createNotificationChannel(context);

        Notification n = new NotificationCompat.Builder(context, Channel_Id)
                .setSmallIcon(R.drawable.wgu_logo)
                .setContentTitle(alertTitle)
                .setContentText(alertText).build();

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(nextAlertId, n);
    }

    // Method to set alert to use within activities
    public static void setAlert(Context context, int Id, Long time, String title, String text) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        int nextAlertId = getNextAlertId(context);
        Intent intentAlert = new Intent(context, NotificationReceiver.class);
        intentAlert.putExtra("Id", Id);
        intentAlert.putExtra("title", title);
        intentAlert.putExtra("text", text);
        intentAlert.putExtra("nextAlertId", nextAlertId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, nextAlertId, intentAlert, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);

        SharedPreferences sp = context.getSharedPreferences(alert, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(Integer.toString(Id), nextAlertId);
        editor.apply();

        incrementNextAlertId(context);
    }

    // Method to set course alert to use within activities
    public static void setCourseAlert(Context context, int Id, Long time, String title, String text) {
        setAlert(context, Id, time, title, text);
    }

    // Method to set assessment alert to use within activities
    public static void setAssessmentAlert(Context context, int Id, Long time, String title, String text) {
        setAlert(context, Id, time, title, text);
    }

    // Method to get next alert id
    private static int getNextAlertId(Context context) {
        SharedPreferences alertPrefs;
        alertPrefs = context.getSharedPreferences(alert, Context.MODE_PRIVATE);
        int nextAlertId = alertPrefs.getInt(nextAlert, 1);
        return nextAlertId;
    }

    // Method to increment next alert id
    private static void incrementNextAlertId(Context context) {
        SharedPreferences alertPrefs;
        alertPrefs = context.getSharedPreferences(alert, Context.MODE_PRIVATE);
        int nextAlertId = alertPrefs.getInt(nextAlert, 1);
        SharedPreferences.Editor alertEditor = alertPrefs.edit();
        alertEditor.putInt(nextAlert, nextAlertId + 1);
        alertEditor.apply();
    }

    // Method to get and increment next alert id
    private static int getAndIncrementNextAlertId(Context context) {
        int nextAlarmId = getNextAlertId(context);
        incrementNextAlertId(context);
        return nextAlarmId;
    }

    // Method to create notification channel
    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel";
            String description = "channel description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NotificationReceiver.Channel_Id, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}