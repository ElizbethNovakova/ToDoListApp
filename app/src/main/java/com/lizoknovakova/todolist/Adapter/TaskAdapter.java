package com.lizoknovakova.todolist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lizoknovakova.todolist.Data.Task;
import com.lizoknovakova.todolist.R;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<Task> tasks;
    private LayoutInflater inflater;

    public TaskAdapter(Context context, List<Task> tasks){
        this.tasks = tasks;
        this.inflater = LayoutInflater.from(context);
    }

    public void setTasks(List<Task> tasksList) {
        this.tasks = tasksList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.task_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.checkBoxTask.setText(task.getTask());
        holder.checkBoxTask.setChecked(toBoolean(task.getStatus()));
    }

    private boolean toBoolean(int n){
        return n!=0;
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBoxTask;
        ViewHolder(View view){
            super(view);
            checkBoxTask = view.findViewById(R.id.checkBox);
        }
    }
}
