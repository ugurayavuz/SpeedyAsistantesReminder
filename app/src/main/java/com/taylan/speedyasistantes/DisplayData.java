package com.taylan.speedyasistantes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayData extends MenuActivity {
    private ListView listView;
    DatabaseHelper databaseHelper;
    private String remName;
    private String catName;
    private String listItemName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);
        listView = findViewById(R.id.listView);
        databaseHelper = new DatabaseHelper(this);
        showTasks();
    }
    private void showTasks(){
        Cursor data = databaseHelper.fetchItem();
        Intent getInt = getIntent();
        remName = getInt.getStringExtra("Reminder Name");
        catName = getInt.getStringExtra("Category Name");
        listItemName = getInt.getStringExtra("List Item Name");
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            if(listItemName.matches(data.getString(4))) {
                listData.add(data.getString(1));
            }
        }
            ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                   String listTaskName = adapterView.getItemAtPosition(i).toString();
                   try{
                   Cursor data2 = databaseHelper.fetchListItems(listTaskName);
                   int listItemID = -1;
                   while (data2.moveToNext()) {
                       listItemID = data2.getInt(0);
                   }
                   if (listItemID > -1) {
                       Intent editScreenIntent = new Intent(DisplayData.this, EditItemName.class);
                       editScreenIntent.putExtra("ID", listItemID);
                       editScreenIntent.putExtra("Task Name", listTaskName);
                       editScreenIntent.putExtra("Reminder Name", remName);
                       editScreenIntent.putExtra("List Item Name", listItemName);
                       startActivity(editScreenIntent);
                   } else {
                       toastOutput("No ID associated with that Reminder Name");
                   }
                   }catch (Exception e){
                       e.printStackTrace();
                   }
                }
            });

    }

    private void toastOutput(String output){
        Toast.makeText(this, output, Toast.LENGTH_LONG).show();
    }

}