package com.example.project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button Start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        //deleteDatabase("PIZZA_CUSTOMER");
        Start = findViewById(R.id.Start_B);
        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask(MainActivity.this);
               connectionAsyncTask.execute("https://18fbea62d74a40eab49f72e12163fe6c.api.mockbin.io/");
            }
        });
    }
}