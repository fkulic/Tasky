package com.fkulic.tasky;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Filip on 4.4.2017..
 */

public class CategoryAdapter extends BaseAdapter {

    private ArrayList<String> mCategories;

    public CategoryAdapter(ArrayList<String> categories) {
        mCategories = categories;
    }

    public void addNewCategory(String category) {
        mCategories.add(category);
        notifyDataSetChanged();
    }

    public void editCategory(String newCategory, int position) {
        mCategories.set(position, newCategory);
        notifyDataSetChanged();
    }

    public void removeCategory(int position) {
        mCategories.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mCategories.size();
    }

    @Override
    public Object getItem(int position) {
        return mCategories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.category_item, parent, false);
            holder = new CategoryViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CategoryViewHolder) convertView.getTag();
        }

        String category = this.mCategories.get(position);
        holder.tvCategoryName.setText(category);
        return convertView;
    }

    static class CategoryViewHolder {
        TextView tvCategoryName;

        public CategoryViewHolder(View view) {
            this.tvCategoryName = (TextView) view.findViewById(R.id.tvCategoryName);
        }
    }
}
