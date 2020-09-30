package com.lizoknovakova.todolist.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lizoknovakova.todolist.Data.Task;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String NAME = "toDoDatabase";
    private static final String TABLE = "toDoTable";
    private static final String ID = "id";
    private static final String TASK = "task";
    private static final String STATUS = "status";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE + "("  + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TASK + "TEXT, " + STATUS + " INTEGER";

    private SQLiteDatabase db;

    private DatabaseHandler(Context context){
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public void openDatabase(){
        db = this.getWritableDatabase();
    }

    public void insertTask(Task task){
        ContentValues cv = new ContentValues();
        cv.put(TASK, task.getTask());
        cv.put(STATUS, 0);
        db.insert(TABLE, null,cv);
    }

    public List<Task> getAllTasks(){
        List<Task> tasksList = new ArrayList<>();
        Cursor cursor = null;
        db.beginTransaction();
        try {
            cursor = db.query(TABLE, null, null, null, null, null, null);
            if(cursor != null){
                if(cursor.moveToFirst()){
                    do {
                        Task task = new Task();
                        task.setTask(cursor.getString(cursor.getColumnIndex(TASK)));
                        task.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                        task.setStatus(cursor.getInt(cursor.getColumnIndex(STATUS)));
                        tasksList.add(task);
                    }while (cursor.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            cursor.close();
        }
        return tasksList;
    }

    public void updateStatus(int id, int status){
        ContentValues cv = new ContentValues();
        cv. put(STATUS, status);
        db.update(TABLE, cv, ID + "=?", new String[] {String.valueOf(id)});
    }

    public void updateTask(int id, String task){
        ContentValues cv = new ContentValues();
        cv. put(TASK, task);
        db.update(TABLE, cv, ID + "=?", new String[] {String.valueOf(id)});
    }

    public void deleteTask(int id){
        db.delete(TABLE, ID + "=?", new String[] {String.valueOf(id)});
    }
}
