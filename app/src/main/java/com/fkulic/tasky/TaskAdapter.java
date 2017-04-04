package com.fkulic.tasky;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Filip on 3.4.2017..
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    ArrayList<Task> mTasks;

    public TaskAdapter(ArrayList<Task> tasks) {
        mTasks = tasks;
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

    static class ViewHolder extends RecyclerView.ViewHolder {
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
    }
}
