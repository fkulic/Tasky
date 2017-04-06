package com.fkulic.tasky;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import static com.fkulic.tasky.TaskAdapter.RESULT_DELETE;
import static com.fkulic.tasky.TaskAdapter.RESULT_EDIT;

public class ListActivity extends Activity implements View.OnClickListener, TaskAdapter.RVClickListener {
    private static final String TAG = "ListActivity";

    public static final int REQ_CODE_NEW_TASK = 9;
    public static final int REQ_CODE_EDIT_TASK = 10;
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_PRIORITY = "priority";


    TaskAdapter mTaskAdapter;

    RecyclerView rvTasks;
    RecyclerView.LayoutManager mManager;
    RecyclerView.ItemDecoration mItemDecoration;
    Button bEditCategories;
    Button bNewTask;

    private Task oldTask;
    public static final String KEY_POSITION = "position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setUpUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTaskAdapter.loadTasks(TaskDBHelper.getInstance(getApplicationContext()).getTasks());
    }

    private void setUpUI() {
        this.rvTasks = (RecyclerView) findViewById(R.id.rvTasks);
        this.mTaskAdapter = new TaskAdapter(new ArrayList<Task>(), this);
        this.mManager = new LinearLayoutManager(this);
        this.mItemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        this.rvTasks.addItemDecoration(this.mItemDecoration);
        this.rvTasks.setLayoutManager(this.mManager);
        this.rvTasks.setAdapter(this.mTaskAdapter);
        this.bEditCategories = (Button) findViewById(R.id.bEditCategories);
        this.bNewTask = (Button) findViewById(R.id.bNewTask);

        this.bEditCategories.setOnClickListener(this);
        this.bNewTask.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bEditCategories:
                intent = new Intent(getApplicationContext(), EditCategoriesActivity.class);
                this.startActivity(intent);
                break;
            case R.id.bNewTask:
                intent = new Intent(getApplicationContext(), NewTaskActivity.class);
                this.startActivityForResult(intent, REQ_CODE_NEW_TASK);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQ_CODE_NEW_TASK:
                    processNewTask(data.getExtras());
                    break;
                case REQ_CODE_EDIT_TASK:
                    editTask(data.getExtras());
                    break;

            }
        }
    }

    private void editTask(Bundle extras) {
        Task newTask = taskFromExtras(extras);
        TaskDBHelper.getInstance(this).updateTask(this.oldTask, newTask);
        mTaskAdapter.editTask(this.oldTask, newTask);
    }

    private void processNewTask(Bundle extras) {
        Task task = taskFromExtras(extras);
        TaskDBHelper.getInstance(getApplicationContext()).insertTask(task);
        mTaskAdapter.addNewTask(task);
    }

    @Override
    public void rvClickListener(View view, int position, String popupResult) {
        if (popupResult.equals(RESULT_EDIT)) {
            this.oldTask = mTaskAdapter.mTasks.get(position);
            Intent intent = new Intent(this, NewTaskActivity.class);
            intent.putExtra(KEY_TITLE, oldTask.getTitle());
            intent.putExtra(KEY_DESCRIPTION, oldTask.getDescription());
            intent.putExtra(KEY_CATEGORY, oldTask.getCategory());
            intent.putExtra(KEY_PRIORITY, oldTask.getPriority());
            startActivityForResult(intent, REQ_CODE_EDIT_TASK);
        }
        if (popupResult.equals(RESULT_DELETE)) {
            TaskDBHelper.getInstance(this).deleteTask(mTaskAdapter.mTasks.get(position));
            mTaskAdapter.removeTask(position);
        }
    }

    public static Task taskFromExtras(Bundle extras) {
        String title = extras.getString(KEY_TITLE);
        String category = extras.getString(KEY_CATEGORY);
        String priority = extras.getString(KEY_PRIORITY);
        String description = extras.getString(KEY_DESCRIPTION);
        Task task = new Task(title, description, category, priority);
        return task;
    }
}
