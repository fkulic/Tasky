package com.fkulic.tasky;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Filip on 3.4.2017..
 */

public class TaskDBHelper extends SQLiteOpenHelper {

    private static final String CREATE_TABLE_TASKS = "CREATE TABLE " + Schema.TABLE_TASKS + " (" +
            Schema.TASK_TITLE + " TEXT," +
            Schema.TASK_DESCRIPTION + " TEXT," +
            Schema.TASK_CATEGORY + " TEXT," +
            Schema.TASK_PRIORITY + " TEXT);";

    private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE " + Schema.TASK_CATEGORY + " (" +
            Schema.CATEGORY_NAME + "TEXT);";

    private static final String DROP_TABLE_TASKS = "DROP TABLE IF EXISTS " + Schema.TABLE_TASKS + ";";
    private static final String DROP_TABKE_CATEGORY = "DROP TABLE IF EXISTS " + Schema.TABLE_CATEGORIES + ";";

    private static final String SELECT_TASKS = "SELECT * FROM " + Schema.TABLE_TASKS + ";";
    private static final String SELECT_CATEGORIES = "SELECT * FROM " + Schema.TABLE_CATEGORIES + ";";

    private static TaskDBHelper taskDBHelper = null;

    public TaskDBHelper(Context context) {
        super(context, Schema.DB_NAME, null, Schema.DB_VERSION);
    }

    public static synchronized TaskDBHelper getInstance() {
        return taskDBHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TASKS);
        db.execSQL(CREATE_TABLE_CATEGORIES);
        insertDefaultCategories();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_TASKS);
        db.execSQL(DROP_TABKE_CATEGORY);
        this.onCreate(db);
    }

    public void insertTask(Task task) {
        ContentValues cv = new ContentValues();
        cv.put(Schema.TASK_TITLE, task.getTitle());
        cv.put(Schema.TASK_DESCRIPTION , task.getDescription());
        cv.put(Schema.TASK_CATEGORY , task.getCategory());
        cv.put(Schema.TASK_PRIORITY , task.getPriority());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert(Schema.TABLE_TASKS, Schema.TASK_TITLE, cv);
        sqLiteDatabase.close();

    }

    public void insertCategory(String category) {
        ContentValues cv = new ContentValues();
        cv.put(Schema.CATEGORY_NAME, category);
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert(Schema.TABLE_CATEGORIES, Schema.CATEGORY_NAME, cv);
        sqLiteDatabase.close();
    }

    public ArrayList<Task> getTasks() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_TASKS, null);
        ArrayList<Task> tasks = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(0);
                String description = cursor.getString(1);
                String category = cursor.getString(2);
                String priority = cursor.getString(3);
                tasks.add(new Task(title, description, category, priority));
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return tasks;
    }

    public ArrayList<String> getCategories() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_CATEGORIES, null);
        ArrayList<String> categories = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String category = cursor.getString(0);
                categories.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return categories;
    }

    private void insertDefaultCategories() {
        this.insertCategory("Job");
        this.insertCategory("School");
        this.insertCategory("Health");
    }

    static class Schema {
        private static final int DB_VERSION = 1;
        private static final String DB_NAME = "tasks.db";

        public static final String TABLE_TASKS = "tasks";
        public static final String TASK_TITLE = "title";
        public static final String TASK_DESCRIPTION = "description";
        public static final String TASK_CATEGORY = "category";
        public static final String TASK_PRIORITY = "priority";

        public static final String TABLE_CATEGORIES = "table_categories";
        public static final String CATEGORY_NAME = "name";


    }
}
