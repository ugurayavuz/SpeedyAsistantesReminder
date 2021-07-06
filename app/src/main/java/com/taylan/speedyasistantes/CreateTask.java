package com.taylan.speedyasistantes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CreateTask extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DatabaseHelper databaseHelper;
    private TextView reminderName;
    private Spinner spinner;
    private static final String[] categories = {"Select an alarm type option", "Anniversary", "Birthday", "Animal", "School Alarm"};
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        reminderName = findViewById(R.id.reminderName);
        spinner = findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        databaseHelper = new DatabaseHelper(this);
        intent = new Intent(CreateTask.this,Notification.class);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 1:
                String first = reminderName.getText().toString();
                if(reminderName.length() != 0 ){
                    intent.putExtra("Reminder Name", first);
                    intent.putExtra("Category Name", categories[1]);
                    startActivity(intent);
                }
                else{
                    toastOutput("Input is empty");
                }
                break;
            case 2:
                String second = reminderName.getText().toString();
                if(reminderName.length() != 0 ){
                    intent.putExtra("Reminder Name", second);
                    intent.putExtra("Category Name", categories[2]);
                    startActivity(intent);
                }
                else{
                    toastOutput("Input is empty");
                }
                break;
            case 3:
                String third = reminderName.getText().toString();
                if(reminderName.length() != 0 ){
                    intent.putExtra("Reminder Name", third);
                    intent.putExtra("Category Name", categories[3]);
                    startActivity(intent);
                }
                else{
                    toastOutput("Input is empty");
                }
                break;
            case 4:
                String fourth = reminderName.getText().toString();
                if(reminderName.length() != 0 ){
                    intent.putExtra("Reminder Name", fourth);
                    intent.putExtra("Category Name", categories[4]);
                    startActivity(intent);
                }
                else{
                    toastOutput("Input is empty");
                }
                break;

        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

    private void toastOutput(String output){
        Toast.makeText(this, output, Toast.LENGTH_LONG).show();
    }

}