package com.fkulic.tasky;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

public class EditCategoriesActivity extends Activity implements View.OnClickListener, AdapterView.OnItemLongClickListener {

    EditText etNewCategory;
    Button bAddNewCategory;
    ListView lvCategories;

    private CategoryAdapter mCategoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_categories);
        setUpUI();
        registerForContextMenu(lvCategories);
    }

    private void setUpUI() {
        this.getActionBar().setTitle(getString(R.string.EditCategories));
        this.etNewCategory = (EditText) findViewById(R.id.etNewCategory);
        this.bAddNewCategory = (Button) findViewById(R.id.bAddNewCategory);
        this.lvCategories = (ListView) findViewById(R.id.lvCategories);

        this.mCategoryAdapter = new CategoryAdapter(TaskDBHelper.getInstance(getApplicationContext()).getCategories());
        this.lvCategories.setAdapter(mCategoryAdapter);
        this.bAddNewCategory.setOnClickListener(this);
        this.lvCategories.setOnItemLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String category = etNewCategory.getText().toString();
        if (category.trim().length() > 2) {
            if (!checkIfCategoryExists(category)) {
                TaskDBHelper.getInstance(getApplicationContext()).insertCategory(category);
                mCategoryAdapter.addNewCategory(category);
                etNewCategory.setText("");
            }
        } else {
            Toast.makeText(getApplicationContext(), R.string.newCategoryWarningLenth, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
        if (mCategoryAdapter.getItem(position).toString().equals("General")) {
            Toast.makeText(this, R.string.warningEditDeleteGeneralCategory, Toast.LENGTH_SHORT).show();
            return false;
        }
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        popup.getMenuInflater().inflate(R.menu.edit_delete_popup, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuItemEditCategory:
                        popEditDialog(position);
                        return true;
                    case R.id.menuItemDeleteCategory:
                        deleteCategory(position);
                        return true;
                }
                return false;
            }
        });
        return false;
    }

    private void deleteCategory(final int position) {
        String category = mCategoryAdapter.getItem(position).toString();
        TaskDBHelper.getInstance(getApplicationContext()).deleteCategory(category);
        mCategoryAdapter.removeCategory(position);
    }

    private void popEditDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setTitle("Edit category");
        builder.setView(input);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editCategory(position, input.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void editCategory(final int position,final String newCategory) {
        if (newCategory.trim().length() > 2) {
            if (!checkIfCategoryExists(newCategory)) {
                String oldCategory = mCategoryAdapter.getItem(position).toString();
                TaskDBHelper.getInstance(getApplicationContext()).updateCategory(oldCategory, newCategory);
                mCategoryAdapter.editCategory(newCategory, position);
            }
        }

    }

    private boolean checkIfCategoryExists(String category) {
        if (TaskDBHelper.getInstance(getApplicationContext()).getCategories().contains(category)) {
            Toast.makeText(getApplicationContext(), R.string.newCategoryWarningExists, Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
