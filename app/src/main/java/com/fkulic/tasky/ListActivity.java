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


    TaskAdapter mTaskAdapter;

    RecyclerView rvTasks;
    RecyclerView.LayoutManager mManager;
    RecyclerView.ItemDecoration mItemDecoration;
    Button bNewCategory;
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
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task("title", "dada da das as fsfr kewof skodf sfonskbfLF SDKFJ KLSJF KLJSFKLDJSLDJASLFKGSKLDHALKFHAKL", "faks", Task.TaskPriority.PRIO_HIGH));
        tasks.add(new Task("title", "dada da das as fsfr kewof skodf sfonskbfLF SDKFJ KLSJF KLJSFKLDJSLDJASLFKGSKLDHALKFHAKL", "faks", Task.TaskPriority.PRIO_LOW));
        tasks.add(new Task("title", "dada da das as fsfr kewof skodf sfonskbfLF SDKFJ KLSJF KLJSFKLDJSLDJASLFKGSKLDHALKFHAKL", "faks", Task.TaskPriority.PRIO_NORMAL));
        tasks.add(new Task("title", "dada da das as fsfr kewof skodf sfonskbfLF SDKFJ KLSJF KLJSFKLDJSLDJASLFKGSKLDHALKFHAKL", "faks", Task.TaskPriority.PRIO_URGENT));
        mTaskAdapter.loadTasks(tasks);
    }

    private void setUpUI() {
        this.rvTasks = (RecyclerView) findViewById(R.id.rvTasks);
        this.mTaskAdapter = new TaskAdapter(new ArrayList<Task>());
        this.mManager = new LinearLayoutManager(this);
        this.mItemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        this.rvTasks.addItemDecoration(this.mItemDecoration);
        this.rvTasks.setLayoutManager(this.mManager);
        this.rvTasks.setAdapter(this.mTaskAdapter);
        this.bNewCategory = (Button) findViewById(R.id.bNewCategory);
        this.bNewTask = (Button) findViewById(R.id.bNewTask);

        this.bNewCategory.setOnClickListener(this);
        this.bNewTask.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        int reqCode = 0;
        switch (v.getId()) {
            case R.id.bNewCategory:
                intent = new Intent(getApplicationContext(), NewCategoryActivity.class);
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
}
