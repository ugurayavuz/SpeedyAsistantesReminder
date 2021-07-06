package com.taylan.speedyasistantes;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.task_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.categories) {
            Intent intent = new Intent(MenuActivity.this, Category.class);
            startActivity(intent);
        }
        else  if (item.getItemId() == R.id.create_task) {
            Intent intent = new Intent(MenuActivity.this, CreateTask.class);
            startActivity(intent);
        }
        else  if (item.getItemId() == R.id.to_main) {
            Intent intent = new Intent(MenuActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
