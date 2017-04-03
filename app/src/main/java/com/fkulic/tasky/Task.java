package com.fkulic.tasky;

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
    }
}
