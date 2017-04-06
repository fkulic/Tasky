package com.fkulic.tasky;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import static com.fkulic.tasky.ListActivity.KEY_CATEGORY;
import static com.fkulic.tasky.ListActivity.KEY_DESCRIPTION;
import static com.fkulic.tasky.ListActivity.KEY_PRIORITY;
import static com.fkulic.tasky.ListActivity.KEY_TITLE;
import static com.fkulic.tasky.ListActivity.taskFromExtras;

public class NewTaskActivity extends Activity implements View.OnClickListener {

    EditText etNewTaskTitle;
    Spinner sNewTaskCategory;
    Spinner sNewTaskPriority;
    EditText etNewTaskDescription;
    Button bAddNewTask;

    private boolean editTask = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        setUpUI();
        Intent intent = getIntent();
        if (intent.hasExtra(KEY_TITLE)) {
            setUpLabels(intent.getExtras());
            editTask = true;
        }
    }

    private void setUpLabels(Bundle extras) {
        getActionBar().setTitle(getString(R.string.EditTask));
        Task task = taskFromExtras(extras);
        etNewTaskTitle.setText(task.getTitle());
        sNewTaskCategory.setSelection(TaskDBHelper.getInstance(this).getCategories().indexOf(task.getCategory()));
        sNewTaskPriority.setSelection(Task.TaskPriority.getPriorities().indexOf(task.getPriority()));
        etNewTaskDescription.setText(task.getDescription());
        bAddNewTask.setText(R.string.save);
    }


    private void setUpUI() {
        getActionBar().setTitle(getString(R.string.NewTask));
        etNewTaskTitle = (EditText) findViewById(R.id.etNewTaskTitle);
        sNewTaskCategory = (Spinner) findViewById(R.id.sNewTaskCategory);
        sNewTaskPriority = (Spinner) findViewById(R.id.sNewTaskPriority);
        etNewTaskDescription = (EditText) findViewById(R.id.etNewTaskDescription);
        bAddNewTask = (Button) findViewById(R.id.bAddNewTask);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.simple_spiner_item,
                TaskDBHelper.getInstance(getApplicationContext()).getCategories());
        categoryAdapter.setDropDownViewResource(R.layout.simple_spiner_item_dropdown);
        sNewTaskCategory.setAdapter(categoryAdapter);

        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.simple_spiner_item,
                Task.TaskPriority.getPriorities());
        priorityAdapter.setDropDownViewResource(R.layout.simple_spiner_item_dropdown);
        sNewTaskPriority.setAdapter(priorityAdapter);
        sNewTaskPriority.setSelection(1);
        bAddNewTask.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        String title = etNewTaskTitle.getText().toString().trim();
        String category = sNewTaskCategory.getSelectedItem().toString();
        String priority = sNewTaskPriority.getSelectedItem().toString();
        String description = etNewTaskDescription.getText().toString();

        if (title.length() > 2) {
            if (!checkIfTitleExists(title) || editTask) {

                Intent intent = new Intent();
                intent.putExtra(KEY_TITLE, title);
                intent.putExtra(KEY_DESCRIPTION, description);
                intent.putExtra(KEY_CATEGORY, category);
                intent.putExtra(KEY_PRIORITY, priority);
                this.setResult(RESULT_OK, intent);
                this.finish();
            }
        } else {
            Toast.makeText(this, R.string.newTaskTitleShortWarning, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkIfTitleExists(String title) {
        if (editTask) {
            return false;
        }
        ArrayList<Task> tasks = TaskDBHelper.getInstance(this).getTasks();
        for (Task task : tasks) {
            if (task.getTitle().equalsIgnoreCase(title)) {
                Toast.makeText(this, R.string.titleWarningExists, Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }
}
