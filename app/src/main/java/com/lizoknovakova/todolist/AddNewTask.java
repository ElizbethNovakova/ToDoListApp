package com.lizoknovakova.todolist;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.lizoknovakova.todolist.Data.Task;
import com.lizoknovakova.todolist.Utils.DatabaseHandler;

import java.util.Objects;

public class AddNewTask extends BottomSheetDialogFragment {

    public static final String TAG = "BottomSheetDialog";

    private EditText newTaskEditText;
    private Button newTaskBtn;
    private DatabaseHandler db;

    public static AddNewTask addNewTask(){
        return new AddNewTask();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_new_task, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        newTaskEditText = Objects.requireNonNull(getView()).findViewById(R.id.addNewTask);
        newTaskBtn = Objects.requireNonNull(getView()).findViewById(R.id.addNewTaskBtn);

        final Bundle bundle = getArguments();
        boolean isUpdated = false;
        if(bundle != null){
            isUpdated = true;
            String task = bundle.getString("task");
            newTaskEditText.setText(task);
            if(task.length() > 0){
                newTaskBtn.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
            }
        }

        db = new DatabaseHandler(getActivity());
        db.openDatabase();

        newTaskEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals("")){
                    newTaskBtn.setEnabled(false);
                    newTaskBtn.setTextColor(Color.GRAY);
                } else {
                    newTaskBtn.setEnabled(true);
                    newTaskBtn.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        final boolean finalIsUpdated = isUpdated;
        newTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = newTaskEditText.getText().toString();
                if(finalIsUpdated){
                    db.updateTask(bundle.getInt("id"), text);
                } else {
                    Task task = new Task();
                    task.setTask(text);
                    task.setStatus(0);
                    db.insertTask(task);
                }
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if(getActivity() instanceof DialogCloseInterface){
            ((DialogCloseInterface)getActivity()).dialogClose(dialog);
        }
    }
}


