package com.fkulic.tasky;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import java.util.ArrayList;

public class ListActivity extends Activity {

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
        tasks.add(new Task("title", "dada da das as fsfr kewof skodf sfonskbfLF SDKFJ KLSJF KLJSFKLDJSLDJASLFKGSKLDHALKFHAKL", "faks", "high"));
        tasks.add(new Task("title", "dada da das as fsfr kewof skodf sfonskbfLF SDKFJ KLSJF KLJSFKLDJSLDJASLFKGSKLDHALKFHAKL", "faks", "high"));
        tasks.add(new Task("title", "dada da das as fsfr kewof skodf sfonskbfLF SDKFJ KLSJF KLJSFKLDJSLDJASLFKGSKLDHALKFHAKL", "faks", "high"));
        tasks.add(new Task("title", "dada da das as fsfr kewof skodf sfonskbfLF SDKFJ KLSJF KLJSFKLDJSLDJASLFKGSKLDHALKFHAKL", "faks", "high"));
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
    }
}
