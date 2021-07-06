package com.taylan.speedyasistantes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends MenuActivity {
    private Button button;
    private Button button2;
    private ListView taskListMain;
    DatabaseHelper databaseHelper;
    private Handler mHandler = new Handler();
    Intent intent;
    Intent intRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskListMain = findViewById(R.id.taskListMain);
        button = findViewById(R.id.deleteT);
        button2 = findViewById(R.id.button);
        databaseHelper = new DatabaseHelper(this);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, CreateTask.class);
                startActivity(intent);
            }
        });
        showTasks();
    }

    private void showTasks(){
        Cursor data = databaseHelper.fetchItem();
        ArrayList<String> listData = new ArrayList<>();
        ArrayList<Integer> itemID = new ArrayList<>();
        while(data.moveToNext()){
                listData.add(data.getString(1));
                itemID.add(data.getInt(0));
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, listData);
        taskListMain.setAdapter(adapter);
        taskListMain.setItemsCanFocus(false);
        taskListMain.setSelected(true);
        taskListMain.setClickable(true);
        taskListMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String listTaskName = adapterView.getItemAtPosition(i).toString();
                int ID = itemID.get(i);
              //  Cursor data2 = databaseHelper.fetchItem2((int)ID);
                try{
                    boolean isChecked = taskListMain.isItemChecked(i);
                    if (isChecked) {
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    databaseHelper.deleteTask(ID, listTaskName);
                                    toastOutput("Task is deleted");
                                    intRefresh = new Intent(MainActivity.this, MainActivity.class);
                                    startActivity(intRefresh);
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else {

                        Cursor data = databaseHelper.fetchListItems(listTaskName);
                        int listItemID = -1;
                        while (data.moveToNext()) {
                            listItemID = data.getInt(0);
                        }
                        if (listItemID > -1) {
                            toastOutputShort("Item unchecked");
                        } else {
                            toastOutput("No ID associated with that Reminder Name");
                        }
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
    private void toastOutputShort(String output){
        Toast.makeText(this, output, Toast.LENGTH_SHORT).show();
    }
}