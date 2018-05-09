package osc.ada.tomislavgazica.taskie;

import java.util.List;

import osc.ada.tomislavgazica.taskie.model.Task;
import osc.ada.tomislavgazica.taskie.model.TaskGenerator;
import osc.ada.tomislavgazica.taskie.persistance.FakeDatabase;

public class TasksRepository {

    private static TasksRepository sRepository = null;
    private static final int INITIAL_TASK_COUNT = 10;

    private FakeDatabase database;

    private TasksRepository() {
        database = new FakeDatabase();
        database.save(TaskGenerator.generate(INITIAL_TASK_COUNT));
    }

    public static synchronized TasksRepository getInstance() {
        if (sRepository == null) {
            sRepository = new TasksRepository();
        }
        return sRepository;
    }

    public List<Task> getTasks() {
        return database.getTasks();
    }

    public void save(Task task) {
        database.save(task);
    }

    public void removeTask(Task task) {
        database.delete(task);
    }

    public void editTask(Task task) {
        database.editTask(task);
    }

    public void setTaskPriority(Task task) {
        database.setTaskPriority(task);
    }

    public void setTaskStatus(Task task) {
        database.setTaskStatus(task);
    }

}
