package osc.ada.tomislavgazica.taskie.view;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import osc.ada.tomislavgazica.taskie.R;
import osc.ada.tomislavgazica.taskie.model.Task;
import osc.ada.tomislavgazica.taskie.model.TaskPriority;

class TaskViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.textview_item_title)
    TextView title;

    @BindView(R.id.textview_item_description)
    TextView description;

    @BindView(R.id.textview_item_date)
    TextView date;

    @BindView(R.id.togglebutton_item_status)
    ToggleButton status;

    @BindView(R.id.imagebutton_item_priority)
    ImageButton priority;

    private Task item;
    private TaskClickListener listener;

    public TaskViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setListener(TaskClickListener listener) {
        this.listener = listener;
    }

    public void setItem(Task current) {
        this.item = current;

        title.setText(item.getTitle());
        description.setText(item.getDescription());
        date.setText(new StringBuilder().append(item.getEndDateDay()).append(".").append(item.getEndDateMonth()).append(".").append(item.getEndDateYear()));

        int color = R.color.taskPriority_unknown;
        switch (current.getPriority()) {
            case LOW:
                color = R.color.taskpriority_low;
                break;
            case MEDIUM:
                color = R.color.taskpriority_medium;
                break;
            case HIGH:
                color = R.color.taskpriority_high;
                break;
        }
        priority.setImageResource(color);
    }

    @OnClick(R.id.imagebutton_item_priority)
    public void onPriorityClick() {
        TaskPriority priority = item.getPriority();
        if (priority == TaskPriority.LOW) {
            item.setPriority(TaskPriority.MEDIUM);
            this.priority.setImageResource(R.color.taskpriority_medium);
        } else if (priority == TaskPriority.MEDIUM) {
            item.setPriority(TaskPriority.HIGH);
            this.priority.setImageResource(R.color.taskpriority_high);
        } else if (priority == TaskPriority.HIGH) {
            item.setPriority(TaskPriority.LOW);
            this.priority.setImageResource(R.color.taskpriority_low);
        }
        if (listener != null) {
            listener.onPriorityClick(item);
        }
    }

    @OnClick(R.id.togglebutton_item_status)
    public void onStatusClick() {
        item.setFinished(!item.isFinished());
        if (listener != null) {
            listener.onStatusClick(item);
        }
    }

    @OnLongClick
    public boolean onTaskLongClick() {
        if (listener != null) {
            listener.onLongClick(item);
        }
        return true;
    }
}