package com.lizoknovakova.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.lizoknovakova.todolist.Adapter.TaskAdapter;
import com.lizoknovakova.todolist.Data.Task;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Task> tasksList;
    private TaskAdapter taskAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasksList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerViewTasks);
        taskAdapter = new TaskAdapter(this, tasksList);
        recyclerView.setAdapter(taskAdapter);

        Task task = new Task();
        task.setId(1);
        task.setStatus(0);
        task.setTask("This is my first task");

        tasksList.add(task);
        tasksList.add(task);
        tasksList.add(task);
        tasksList.add(task);
        tasksList.add(task);

        taskAdapter.setTasks(tasksList);
    }
}