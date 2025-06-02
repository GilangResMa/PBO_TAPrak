package schreminder.model;

import java.util.ArrayList;
import java.util.List;

public class TodoModel {
    private final List<TodoItem> todoList = new ArrayList<>();

    public void addTodo(TodoItem item) { todoList.add(item); }
    public void removeTodo(int index) { if (index >= 0 && index < todoList.size()) todoList.remove(index); }
    public List<TodoItem> getTodos() { return todoList; }
}
