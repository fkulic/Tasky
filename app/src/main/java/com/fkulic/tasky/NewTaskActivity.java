package com.fkulic.tasky;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import static com.fkulic.tasky.ListActivity.KEY_CATEGORY;
import static com.fkulic.tasky.ListActivity.KEY_DESCRIPTION;
import static com.fkulic.tasky.ListActivity.KEY_PRIORITY;
import static com.fkulic.tasky.ListActivity.KEY_TITLE;

public class NewTaskActivity extends Activity implements View.OnClickListener {

    EditText etNewTaskTitle;
    Spinner sNewTaskCategory;
    Spinner sNewTaskPriority;
    EditText etNewTaskDescription;
    Button bAddNewTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        setUpUI();
    }

    private void setUpUI() {
        getActionBar().setTitle(getString(R.string.NewTask));
        etNewTaskTitle = (EditText) findViewById(R.id.etNewTaskTitle);
        sNewTaskCategory = (Spinner) findViewById(R.id.sNewTaskCategory);
        sNewTaskPriority = (Spinner) findViewById(R.id.sNewTaskPriority);
        etNewTaskDescription = (EditText) findViewById(R.id.etNewTaskDescription);
        bAddNewTask = (Button) findViewById(R.id.bAddNewTask);


        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                TaskDBHelper.getInstance(getApplicationContext()).getCategories());
        sNewTaskCategory.setAdapter(categoryAdapter);

        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                Task.TaskPriority.getPriorities());
        sNewTaskPriority.setAdapter(priorityAdapter);

        sNewTaskPriority.setSelection(1);
        bAddNewTask.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        String title = etNewTaskTitle.getText().toString();
        String category = sNewTaskCategory.getSelectedItem().toString();
        String priority = sNewTaskPriority.getSelectedItem().toString();
        String description = etNewTaskDescription.getText().toString();

        if (title.trim().length() > 0 && category.length() > 0 && priority.length() > 0 ) {

            Intent intent = new Intent();
            intent.putExtra(KEY_TITLE, title);
            intent.putExtra(KEY_DESCRIPTION, description);
            intent.putExtra(KEY_CATEGORY, category);
            intent.putExtra(KEY_PRIORITY, priority);
            this.setResult(RESULT_OK, intent);
            this.finish();
        }
    }
}
