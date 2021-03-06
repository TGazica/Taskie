package osc.ada.tomislavgazica.taskie.view;

import osc.ada.tomislavgazica.taskie.model.Task;

public interface TaskClickListener {
    void onStatusClick(Task task);
    void onPriorityClick(Task task);
    void onLongClick(Task task);
}