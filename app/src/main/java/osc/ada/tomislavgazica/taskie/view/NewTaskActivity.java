package osc.ada.tomislavgazica.taskie.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import osc.ada.tomislavgazica.taskie.R;
import osc.ada.tomislavgazica.taskie.model.Task;
import osc.ada.tomislavgazica.taskie.model.TaskPriority;

public class NewTaskActivity extends AppCompatActivity {

    @BindView(R.id.edittext_newtask_enterTitle)
    EditText titleEntry;
    @BindView(R.id.edittext_newtask_enterDescription)
    EditText descriptionEntry;
    @BindView(R.id.spinner_newtask_priority)
    Spinner priorityEntry;
    @BindView(R.id.button_newtask_setDate)
    Button saveDateEntry;
    @BindView(R.id.textview_newtask_dueDate)
    TextView dueDate;
    private Calendar calendar;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        ButterKnife.bind(this);
        setUpSpinnerSource();
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);
    }

    private void showDate(int year, int month, int day) {
        dueDate.setText(new StringBuilder().append(day).append(".").append(month).append(".").append(year));
    }

    private void setUpSpinnerSource() {
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, TaskPriority.values());
        priorityEntry.setAdapter(adapter);
        priorityEntry.setSelection(0);
    }

    @OnClick(R.id.button_newtask_setDate)
    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                    if(arg1 > year || (arg1>=year && arg2>=month+1) || (arg1>=year && arg2>=month && arg3 >= day)) {
                        year = arg1;
                        month = arg2;
                        day = arg3;
                        showDate(arg1, arg2 + 1, arg3);
                    }else {
                        Toast.makeText(getApplicationContext(), "Cant enter past date.", Toast.LENGTH_SHORT).show();
                    }
                }
            };


    @OnClick(R.id.imagebutton_newtask_saveTask)
    public void saveTask() {
        String title = titleEntry.getText().toString();
        String description = descriptionEntry.getText().toString();
        TaskPriority priority = (TaskPriority) priorityEntry.getSelectedItem();

        Task newTask = new Task(priority, title, description, day, month + 1, year);
        Intent saveTaskIntent = new Intent(this, TasksActivity.class);
        saveTaskIntent.putExtra(TasksActivity.EXTRA_TASK, newTask);
        setResult(RESULT_OK, saveTaskIntent);
        finish();
    }

}
