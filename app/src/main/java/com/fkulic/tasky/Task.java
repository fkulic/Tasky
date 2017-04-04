package com.fkulic.tasky;

import java.util.ArrayList;

/**
 * Created by Filip on 3.4.2017..
 */

public class Task {
    private String mTitle;
    private String mDescription;
    private String mCategory;
    private String mPriority;

    public Task(String title, String description, String category, String priority) {
        mTitle = title;
        mDescription = description;
        mCategory = category;
        mPriority = priority;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getCategory() {
        return mCategory;
    }

    public String getPriority() {
        return mPriority;
    }

    static class TaskPriority {
        public static final String PRIO_URGENT = "Urgent";
        public static final String PRIO_HIGH = "High";
        public static final String PRIO_NORMAL = "Normal";
        public static final String PRIO_LOW = "Low";

        public static ArrayList<String> getPriorities() {
            ArrayList<String> priorities = new ArrayList<>();
            priorities.add(PRIO_LOW);
            priorities.add(PRIO_NORMAL);
            priorities.add(PRIO_HIGH);
            priorities.add(PRIO_URGENT);
            return priorities;
        }
    }
}
