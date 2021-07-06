package com.taylan.speedyasistantes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import java.text.DateFormat;
import java.util.Calendar;
import android.database.Cursor;
import android.widget.Toast;

public class EditItemName extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    private Button editTask;
    private Button deleteTask;
    private TextView currentDetails;
    private int taskID;
    private String taskName;
    private String remName;
    private String listItemName;
    Notification notification;
    Intent getIntent;
    Intent goTo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item_name);
        databaseHelper = new DatabaseHelper(this);
        editTask = findViewById(R.id.editBut);
        currentDetails = findViewById(R.id.currentDetails);
        notification = new Notification();
        goTo = new Intent(EditItemName.this, DisplayData.class);
        getIntent = getIntent();
        taskID = getIntent.getIntExtra("ID", -1);
        taskName = getIntent.getStringExtra("Task Name");
        remName = getIntent.getStringExtra("Reminder Name");
        listItemName = getIntent.getStringExtra("List Item Name");
        editAndDelete();
        //startActivity(goTo);
    }
    private void editAndDelete() {
        Cursor cursor = databaseHelper.fetchItem2(taskID);
        StringBuilder stringBuilder = new StringBuilder();
        try{
         while (cursor.moveToNext()) {
                stringBuilder.append("\nCurrent Reminder Name: " + cursor.getString(1) + "\n Current Reminder Time: " + cursor.getString(2)
                        +"\n Current Reminder Date: " + cursor.getString(3));
         }
         currentDetails.setText(stringBuilder);
         cursor.close();
        }catch(Exception e){
            e.printStackTrace();
        }
            editTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int date = calendar.get(Calendar.DATE);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(EditItemName.this, new DatePickerDialog.OnDateSetListener() {
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
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
    }

    private void clickTime(Calendar calendar) {
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
                updateDatabase(calendar);
                startAlarm(calendar);
                goB(view);
            }
        }, hour, minute, true);
        timePickerDialog.show();
    }

    private void updateDatabase(Calendar c){
        String reminderDate;
        String reminderTime;
        reminderDate = DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());
        reminderTime =  DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        try {
            databaseHelper.editTaskDetails(taskID, taskName, reminderTime, reminderDate);
        }
        catch(Exception e){
            e.printStackTrace();
        }
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

    private void goB(View view){
        try {
            Intent goIntent = new Intent(EditItemName.this, DisplayData.class);
            goIntent.putExtra("Reminder Name", taskName);
            goIntent.putExtra("List Item Name", listItemName);
            startActivity(goIntent);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void toastOutput(String output){
        Toast.makeText(this, output, Toast.LENGTH_LONG).show();
    }
}
