package com.lizoknovakova.todolist.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lizoknovakova.todolist.AddNewTask;
import com.lizoknovakova.todolist.Data.Task;
import com.lizoknovakova.todolist.MainActivity;
import com.lizoknovakova.todolist.R;
import com.lizoknovakova.todolist.Utils.DatabaseHandler;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<Task> tasks;
    private DatabaseHandler db;
    private MainActivity activity;

    public TaskAdapter(MainActivity activity, DatabaseHandler db){
        this.db = db;
        this.activity = activity;
    }

    public void setTasks(List<Task> tasksList) {
        this.tasks = tasksList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {
        db.openDatabase();
        final Task task = tasks.get(position);
        holder.checkBoxTask.setText(task.getTask());
        holder.checkBoxTask.setChecked(toBoolean(task.getStatus()));
        holder.checkBoxTask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    db.updateStatus(task.getId(), 1);
                } else {
                    db.updateStatus(task.getId(), 0);
                }
            }
        });
    }

    private boolean toBoolean(int n){
        return n!=0;
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public Context getContext(){
        return activity;
    }

    public void deleteTask(int position){
        Task task = tasks.get(position);
        db.deleteTask(task.getId());
        tasks.remove(position);
        notifyItemRemoved(position);
    }

    public void editTask(int position){
        Task task = tasks.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", task.getId());
        bundle.putString("task", task.getTask());

        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), AddNewTask.TAG);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBoxTask;
        ViewHolder(View view){
            super(view);
            checkBoxTask = view.findViewById(R.id.checkBox);
        }
    }
}
