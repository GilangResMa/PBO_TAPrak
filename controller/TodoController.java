package schreminder.controller;

import schreminder.model.*;
import schreminder.view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class TodoController {
    private final TodoModel model;
    private final TodoView view;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public TodoController(TodoModel model, TodoView view) {
        this.model = model;
        this.view = view;

        view.addAddButtonListener(new AddListener());
        view.addDeleteButtonListener(new DeleteListener());

        view.setVisible(true);
    }

    class AddListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String task = view.taskField.getText();
            String dateStr = view.dateField.getText();
            String timeStr = view.timeField.getText();

            try {
                LocalDate date = LocalDate.parse(dateStr, dateFormatter);
                LocalTime time = LocalTime.parse(timeStr, timeFormatter);
                LocalDateTime dateTime = LocalDateTime.of(date, time);

                TodoItem item = new TodoItem(0, task, "", dateTime);
                model.addTodo(item);
                view.listModel.addElement(item.toString());
                view.clearInput();
                scheduleReminder(item);
            } catch (Exception ex) {
                view.showError("Format tanggal harus yyyy-MM-dd dan waktu HH:mm");
            }
        }
    }

    class DeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = view.todoList.getSelectedIndex();
            if (index != -1) {
                model.removeTodo(index);
                view.listModel.remove(index);
            }
        }
    }

    private void scheduleReminder(TodoItem item) {
        long delay = Duration.between(LocalDateTime.now(), item.getDateTime()).toMillis();
        if (delay < 0) {
            view.showError("Waktu tugas sudah lewat!");
            return;
        }

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                view.showReminder(item.getTask(), item.getDateTime().toString());
            }
        }, delay);
    }
}
