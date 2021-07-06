package com.taylan.speedyasistantes;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class Category extends MenuActivity {
    private ListView listView;
    DatabaseHelper databaseHelper;
    private ArrayList<String> categoryNames;
    Intent getIntent;
    private String categoryNameOfTask;
    private String reminderName;
    private static final String[] categories = {"Select an alarm type option", "Anniversary", "Birthday", "Animal", "School Alarm", "Other"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        listView = findViewById(R.id.listViewCategory);
        databaseHelper = new DatabaseHelper(this);
        showCategories();
    }
    private void showCategories(){
        getIntent = getIntent();
        categoryNameOfTask = getIntent.getStringExtra("Category Name");
        reminderName = getIntent.getStringExtra("Reminder Name");
        categoryNames = new ArrayList<>();
        categoryNames.add("Anniversary");
        categoryNames.add("Birthday");
        categoryNames.add("Animal");
        categoryNames.add("School Alarm");
        categoryNames.add("Other");
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categoryNames);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String listCategoryName = adapterView.getItemAtPosition(i).toString();
                    try {
                        Intent goDisplay = new Intent(Category.this, DisplayData.class);
                        goDisplay.putExtra("Reminder Name", reminderName);
                        goDisplay.putExtra("Category Name", categoryNameOfTask);
                        goDisplay.putExtra("List Item Name", listCategoryName);
                        startActivity(goDisplay);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
            }
        });

    }

    private void toastOutput(String output){
        Toast.makeText(this, output, Toast.LENGTH_LONG).show();
    }

}