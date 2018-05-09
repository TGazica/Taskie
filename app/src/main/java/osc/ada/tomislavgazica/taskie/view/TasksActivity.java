package osc.ada.tomislavgazica.taskie.view;

import android.content.ClipData;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import osc.ada.tomislavgazica.taskie.R;
import osc.ada.tomislavgazica.taskie.TasksRepository;
import osc.ada.tomislavgazica.taskie.model.Task;

public class TasksActivity extends AppCompatActivity implements TaskClickListener, EditTaskClickListener {

    private static final int REQUEST_NEW_TASK = 10;
    public static final int REQUEST_EDIT_TASK = 5;
    public static final String EXTRA_TASK = "task";
    public static final String EDIT_TASK = "edit_task";
    private static final String SHOW_TASKS = "show_tasks";
    private static final String SHOW_TASKS_BY_PRIORITY = "show_tasks_by_priority";
    private static final String SHOW_TASKS_BY_STATUS = "show_tasks_by_status";

    private String show;
    private boolean showAll;

    TasksRepository repository = TasksRepository.getInstance();
    TaskAdapter taskAdapter;

    @BindView(R.id.fab_tasks_addNew)
    FloatingActionButton newTask;
    @BindView(R.id.recycler_tasks)
    RecyclerView taskRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        ButterKnife.bind(this);
        setupRecyclerView();
        showTasks();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.taskie_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setupRecyclerView() {
        int orientation = LinearLayoutManager.VERTICAL;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, orientation, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this, orientation);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        taskAdapter = new TaskAdapter(this);

        taskRecycler.setLayoutManager(layoutManager);
        taskRecycler.setItemAnimator(itemAnimator);
        taskRecycler.addItemDecoration(decoration);
        taskRecycler.setAdapter(taskAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sortByPriority:
                showTasksByPriority();
                return true;
            case R.id.menu_showCompleted:
                if (showAll) {
                    showTasksByStatus();
                    showAll = false;
                } else {
                    showTasks();
                    showAll = true;
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void callLastShowTask() {
        switch (show) {
            case SHOW_TASKS:
                showTasks();
                break;
            case SHOW_TASKS_BY_PRIORITY:
                showTasksByPriority();
                break;
            case SHOW_TASKS_BY_STATUS:
                showTasksByStatus();
                break;
            default:
                showTasks();
        }
    }

    private void showTasks() {
        showAll = true;
        show = SHOW_TASKS;
        List<Task> tasks = repository.getTasks();
        taskAdapter.updateTasks(tasks);
    }

    private void showTasksByPriority() {
        show = SHOW_TASKS_BY_PRIORITY;
        List<Task> tasks = repository.getTasks();
        Collections.sort(tasks, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return o1.getPriority().compareTo(o2.getPriority());
            }
        });
        taskAdapter.updateTasks(tasks);
    }

    private void showTasksByStatus() {
        show = SHOW_TASKS_BY_STATUS;
        List<Task> tasks = repository.getTasks();
        List<Task> ongoingTasks = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            if (!tasks.get(i).isFinished()) {
                ongoingTasks.add(tasks.get(i));
            }
        }
        taskAdapter.updateTasks(ongoingTasks);
    }


    @OnClick(R.id.fab_tasks_addNew)
    public void startNewTaskActivity() {
        Intent intent = new Intent(this, NewTaskActivity.class);
        startActivityForResult(intent, REQUEST_NEW_TASK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_NEW_TASK && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra(EXTRA_TASK)) {
                Task task = (Task) data.getSerializableExtra(EXTRA_TASK);
                repository.save(task);
                callLastShowTask();
            }
        }

        if (requestCode == REQUEST_EDIT_TASK && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra(EDIT_TASK)) {
                Task task = (Task) data.getSerializableExtra(EDIT_TASK);
                repository.editTask(task);
                callLastShowTask();
            }
        }
    }

    @Override
    public void onStatusClick(Task task) {
        repository.setTaskStatus(task);
        callLastShowTask();
    }

    @Override
    public void onPriorityClick(Task task) {
        repository.setTaskPriority(task);
        callLastShowTask();
    }

    @Override
    public void onLongClick(Task task) {
        EditTaskDialogFragment editTaskDialogFragment = new EditTaskDialogFragment(this);
        editTaskDialogFragment.setTask(task);
        editTaskDialogFragment.setListener(this);
    }

    @Override
    public void onEditClick(Task task) {
        Intent intent = new Intent(this, EditItemActivity.class);
        intent.putExtra(EditItemActivity.EDIT_TASK, task);
        startActivityForResult(intent, REQUEST_EDIT_TASK);
    }

    @Override
    public void onDeleteClick(Task task) {
        repository.removeTask(task);
        callLastShowTask();
    }
}
