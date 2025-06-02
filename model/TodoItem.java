package schreminder.model;

import java.time.LocalDateTime;

public class TodoItem {
    private int id;
    private String task;
    private String description;
    private LocalDateTime dateTime;

    public TodoItem(int id, String task, String description, LocalDateTime dateTime) {
        this.id = id;
        this.task = task;
        this.description = description;
        this.dateTime = dateTime;
    }

    public int getId() { return id; }
    public String getTask() { return task; }
    public String getDescription() { return description; }
    public LocalDateTime getDateTime() { return dateTime; }

    @Override
    public String toString() {
        return task + " (" + dateTime + ")";
    }
}
