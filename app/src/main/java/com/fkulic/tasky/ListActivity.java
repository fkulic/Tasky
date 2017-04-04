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

public class ListActivity extends Activity implements View.OnClickListener {

    public static final int REQ_CODE_TASK = 9;
    public static final int REQ_CODE_CATEGORY = 10;
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
        this.mTaskAdapter = new TaskAdapter(new ArrayList<Task>());
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
        int reqCode = 0;
        switch (v.getId()) {
            case R.id.bEditCategories:
                intent = new Intent(getApplicationContext(), EditCategoriesActivity.class);
                reqCode = REQ_CODE_CATEGORY;
                break;
            case R.id.bNewTask:
                intent = new Intent(getApplicationContext(), NewTaskActivity.class);
                reqCode = REQ_CODE_TASK;
                break;
            default:
                break;
        }
        if (intent != null) {
            this.startActivityForResult(intent, reqCode);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQ_CODE_CATEGORY:
                    // TODO: 4.4.2017. processNewCategory(data.getExtras());
                    break;
                case REQ_CODE_TASK:
                    processNewTask(data.getExtras());
                    break;
            }
        }
    }

    private void processNewTask(Bundle extras) {
        String title = extras.getString(KEY_TITLE);
        String category = extras.getString(KEY_CATEGORY);
        String priority = extras.getString(KEY_PRIORITY);
        String description = extras.getString(KEY_DESCRIPTION);
        Task task = new Task(title, description, category, priority);
        TaskDBHelper.getInstance(getApplicationContext()).insertTask(task);
        mTaskAdapter.addNewTask(task);
    }
}
