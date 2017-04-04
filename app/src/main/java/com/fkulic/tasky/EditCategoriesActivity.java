package com.fkulic.tasky;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class EditCategoriesActivity extends Activity implements View.OnClickListener {

    EditText etNewCategory;
    Button bAddNewCategory;
    ListView lvCategories;

    private CategoryAdapter mCategoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_categories);
        setUpUI();
    }

    private void setUpUI() {
        this.getActionBar().setTitle(getString(R.string.EditCategories));
        this.etNewCategory = (EditText) findViewById(R.id.etNewCategory);
        this.bAddNewCategory = (Button) findViewById(R.id.bAddNewCategory);
        this.lvCategories = (ListView) findViewById(R.id.lvCategories);

        this.mCategoryAdapter = new CategoryAdapter(TaskDBHelper.getInstance(getApplicationContext()).getCategories());
        this.lvCategories.setAdapter(mCategoryAdapter);
        this.bAddNewCategory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String category = etNewCategory.getText().toString();
        if (category.trim().length() > 2) {
            if (TaskDBHelper.getInstance(getApplicationContext()).getCategories().contains(category)) {
                Toast.makeText(getApplicationContext(), R.string.newCategoryWarningExists, Toast.LENGTH_SHORT).show();
            } else {
                TaskDBHelper.getInstance(getApplicationContext()).insertCategory(category);
                mCategoryAdapter.addNewCategory(category);
                etNewCategory.setText("");
            }

        } else {
            Toast.makeText(getApplicationContext(), R.string.newCategoryWarningLenth, Toast.LENGTH_SHORT).show();
        }

    }
}
