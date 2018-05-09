package osc.ada.tomislavgazica.taskie.persistance;

import java.util.ArrayList;
import java.util.List;

import osc.ada.tomislavgazica.taskie.model.Task;

public class FakeDatabase {

    List<Task> tasks;

    public FakeDatabase() {
        tasks = new ArrayList<>();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void save(Task task) {
        tasks.add(task);
    }

    public void save(List<Task> tasks) {
        this.tasks.addAll(tasks);
    }

    public void delete(Task task) {
        tasks.remove(task);
    }

    public void editTask(Task task) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getID() == task.getID()) {
                tasks.get(i).setPriority(task.getPriority());
                tasks.get(i).setTitle(task.getTitle());
                tasks.get(i).setDescription(task.getDescription());
                tasks.get(i).setEndDateDay(task.getEndDateDay());
                tasks.get(i).setEndDateMonth(task.getEndDateMonth());
                tasks.get(i).setEndDateYear(task.getEndDateYear());
            }
        }
    }

    public void setTaskPriority(Task task) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getID() == task.getID()) {
                tasks.get(i).setPriority(task.getPriority());
            }
        }
    }

    public void setTaskStatus(Task task) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getID() == task.getID()) {
                tasks.get(i).setFinished(task.isFinished());
            }
        }
    }

}
