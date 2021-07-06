package com.taylan.speedyasistantes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import java.text.DateFormat;
import java.util.Calendar;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import java.util.Calendar;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.os.SystemClock;
import android.provider.Settings;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Notification  extends AppCompatActivity{
    private TextView mTextView;
    DatabaseHelper databaseHelper;
    private Button buttonCancelAlarm;
    private Button buttonTimePicker;
    private String reminderName;
    private String categoryName;
    Intent getIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mTextView = findViewById(R.id.textView);
        buttonTimePicker = findViewById(R.id.button_timepicker);
        buttonCancelAlarm  = findViewById(R.id.button_cancel);
        databaseHelper = new DatabaseHelper(this);
        getIntent = getIntent();
        buttonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int date = calendar.get(Calendar.DATE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Notification.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.YEAR, year);
                        calendar1.set(Calendar.MONTH, month);
                        calendar1.set(Calendar.DATE, date);
                        clickTime(calendar1);
                    }
                }, year, month, date);
                datePickerDialog.show();

            }
        });
        buttonCancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });
    }
    private void clickTime(Calendar calendar){
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        boolean is24HourFormat = true;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);
                updateTimeText(calendar);
                startAlarm(calendar);
                onClick(view);
            }
        }, hour, minute, true);
        timePickerDialog.show();
    }//delete task

    private void updateTimeText(Calendar c) {
        categoryName = getIntent.getStringExtra("Category Name");
        reminderName = getIntent.getStringExtra("Reminder Name");
        String timeText = "Alarm set for: ";
        String reminderDate;
        String reminderTime;
        timeText += reminderName + " " +  DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime()) +
        "\n" + DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());
        reminderDate = DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());
        reminderTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        try {
            databaseHelper.notificationInsert(reminderName, reminderTime, reminderDate, categoryName);
        }catch (Exception e){
            e.printStackTrace();
        }
        mTextView.setText(timeText);
    }
    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmRecieve.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }
    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmRecieve.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.cancel(pendingIntent);
        mTextView.setText("Alarm canceled");
    }

    private void onClick(View view){
      //  Intent intent = new Intent(Notification.this,DisplayData.class);
        Intent intent = new Intent(Notification.this,Category.class);
        reminderName = getIntent.getStringExtra("Reminder Name");
        categoryName = getIntent.getStringExtra("Category Name");
        intent.putExtra("Reminder Name", reminderName);
        intent.putExtra("Category Name", categoryName);
        startActivity(intent);
    }


}