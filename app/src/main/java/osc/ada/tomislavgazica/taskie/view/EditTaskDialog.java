package osc.ada.tomislavgazica.taskie.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import osc.ada.tomislavgazica.taskie.R;
import osc.ada.tomislavgazica.taskie.model.Task;

public class EditTaskDialog extends Dialog implements View.OnClickListener {

    TextView edit;
    TextView delete;

    Task task;

    EditTaskClickListener listener;

    public EditTaskDialog(@NonNull Context context) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_edit_task);
        edit = findViewById(R.id.textview_dialog_edit);
        edit.setOnClickListener(this);
        delete = findViewById(R.id.textview_dialog_delete);
        delete.setOnClickListener(this);

        show();
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void setListener(EditTaskClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            if (v == edit) {
                listener.onEditClick(task);
                dismiss();
            }
            if (v == delete) {
                listener.onDeleteClick(task);
                dismiss();
            }
        }
    }
}
