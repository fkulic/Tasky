package com.fkulic.tasky;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Filip on 3.4.2017..
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    public static final String RESULT_EDIT = "edit";
    public static final String RESULT_DELETE = "delete";

    ArrayList<Task> mTasks;
    static RVClickListener mListener;

    public interface RVClickListener {
        public void rvClickListener(View view, int position, String popupResult);
    }

    public TaskAdapter(ArrayList<Task> tasks, RVClickListener listener) {
        mTasks = tasks;
        mListener = listener;
    }

    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.task_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TaskAdapter.ViewHolder holder, int position) {
        Task task = mTasks.get(position);
        holder.colorByPriority(task);
        holder.tvTaskTitle.setText(task.getTitle());
        holder.tvTaskDescription.setText(task.getDescription());
        holder.taskCategory.setText(task.getCategory());
    }

    public void loadTasks(ArrayList<Task> tasks) {
        mTasks = tasks;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public void addNewTask(Task task) {
        mTasks.add(task);
        notifyDataSetChanged();
    }

    public void editTask(Task oldTask, Task newTask) {
        mTasks.set(mTasks.indexOf(oldTask), newTask);
        notifyDataSetChanged();
    }

    public void removeTask(int position) {
        mTasks.remove(position);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        LinearLayout llTask;
        TextView tvTaskTitle;
        TextView tvTaskDescription;
        TextView taskCategory;

        public ViewHolder(View itemView) {
            super(itemView);
            this.llTask = (LinearLayout) itemView.findViewById(R.id.llTask);
            this.tvTaskTitle = (TextView) itemView.findViewById(R.id.tvTaskTitle);
            this.tvTaskDescription = (TextView) itemView.findViewById(R.id.tvTaskDescription);
            this.taskCategory = (TextView) itemView.findViewById(R.id.taskCategory);

            itemView.setOnLongClickListener(this);
        }

        void colorByPriority(Task task) {
            switch (task.getPriority()) {
                case Task.TaskPriority.PRIO_LOW:
                    this.llTask.setBackgroundResource(R.color.Low);
                    break;
                case Task.TaskPriority.PRIO_NORMAL:
                    this.llTask.setBackgroundResource(R.color.Normal);
                    break;
                case Task.TaskPriority.PRIO_HIGH:
                    this.llTask.setBackgroundResource(R.color.High);
                    break;
                case Task.TaskPriority.PRIO_URGENT:
                    this.llTask.setBackgroundResource(R.color.Urgent);
                    break;
                default:
                    break;
            }
        }

        @Override
        public boolean onLongClick(final View v) {
            final int position = this.getLayoutPosition();
            PopupMenu popup = new PopupMenu(v.getContext(), v);
            popup.getMenuInflater().inflate(R.menu.edit_delete_popup, popup.getMenu());
            popup.show();
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menuItemEditCategory:
                            mListener.rvClickListener(v, position, RESULT_EDIT);
                            return true;
                        case R.id.menuItemDeleteCategory:
                            mListener.rvClickListener(v, position, RESULT_DELETE);
                            return true;
                    }
                    return false;
                }

            });
            return true;
        }
    }
}
