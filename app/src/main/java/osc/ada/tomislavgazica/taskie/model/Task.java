package osc.ada.tomislavgazica.taskie.model;

import java.io.Serializable;

public class Task implements Serializable {

    public static int sID = 0;

    private int ID;
    private TaskPriority priority;
    private String title;
    private String description;
    private int endDateDay;
    private int endDateMonth;
    private int endDateYear;
    private boolean isFinished = false;

    public Task(TaskPriority priority, String title, String description, int endDateDay, int endDateMonth, int endDateYear) {
        ID = sID++;
        this.priority = priority;
        this.title = title;
        this.description = description;
        this.endDateDay = endDateDay;
        this.endDateMonth = endDateMonth;
        this.endDateYear = endDateYear;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEndDateDay() {
        return endDateDay;
    }

    public void setEndDateDay(int endDateDay) {
        this.endDateDay = endDateDay;
    }

    public int getEndDateMonth() {
        return endDateMonth;
    }

    public void setEndDateMonth(int endDateMonth) {
        this.endDateMonth = endDateMonth;
    }

    public int getEndDateYear() {
        return endDateYear;
    }

    public void setEndDateYear(int endDateYear) {
        this.endDateYear = endDateYear;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }
}
