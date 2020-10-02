package com.lizoknovakova.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lizoknovakova.todolist.Adapter.RecyclerTaskSwipe;
import com.lizoknovakova.todolist.Adapter.TaskAdapter;
import com.lizoknovakova.todolist.Data.Task;
import com.lizoknovakova.todolist.Utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseInterface{

    private RecyclerView recyclerView;
    private List<Task> tasksList;
    private TaskAdapter taskAdapter;
    private FloatingActionButton floatingActionButton;

    private DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        tasksList = new ArrayList<>();

        db = new DatabaseHandler(this);
        db.openDatabase();

        recyclerView = findViewById(R.id.recyclerViewTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        floatingActionButton = findViewById(R.id.floatBtnAddTask);

        taskAdapter = new TaskAdapter(MainActivity.this, db);
        recyclerView.setAdapter(taskAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerTaskSwipe(taskAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        tasksList = db.getAllTasks();
        Collections.reverse(tasksList);
        taskAdapter.setTasks(tasksList);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewTask.addNewTask().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });
    }

    @Override
    public void dialogClose(DialogInterface dialog) {
        tasksList = db.getAllTasks();
        Collections.reverse(tasksList);
        taskAdapter.setTasks(tasksList);
        taskAdapter.notifyDataSetChanged();
    }
}