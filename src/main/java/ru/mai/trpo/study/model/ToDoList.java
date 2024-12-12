/*
 * Copyright (c) 2024, TopS BI LLC. All rights reserved.
 * http://www.topsbi.ru
 */

package ru.mai.trpo.study.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class ToDoList {

    private final List<Task> tasks = new ArrayList<>();

    public void addTask(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Task title cannot be null or empty");
        }
        tasks.add(new Task(title));
    }

    public void removeTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("Invalid task index");
        }
        tasks.remove(index);
    }

    public void markTaskAsCompleted(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("Invalid task index");
        }
        tasks.get(index).setState(TaskState.COMPLETED);
    }

    public List<Task> getTasks(TaskFilterStrategy filterStrategy) {
        return filterStrategy.filter(tasks);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Task {
        private String title;
        private TaskState state;

        // Конструктор по умолчанию, чтобы state автоматически устанавливался как NEW
        public Task(String title) {
            this.title = title;
            this.state = TaskState.NEW;
        }

        @Override
        public String toString() {
            return String.format("Task[title='%s', state='%s']", title, state);
        }
    }

    public enum TaskState {
        NEW,
        COMPLETED
    }

    public interface TaskFilterStrategy {
        List<Task> filter(List<Task> tasks);
    }

    public static class AllTasksFilter implements TaskFilterStrategy {
        @Override
        public List<Task> filter(List<Task> tasks) {
            return new ArrayList<>(tasks);
        }
    }

    public static class CompletedTasksFilter implements TaskFilterStrategy {
        @Override
        public List<Task> filter(List<Task> tasks) {
            return tasks.stream()
                    .filter(task -> task.getState() == TaskState.COMPLETED)
                    .collect(Collectors.toList());
        }
    }

    public static class NewTasksFilter implements TaskFilterStrategy {
        @Override
        public List<Task> filter(List<Task> tasks) {
            return tasks.stream()
                    .filter(task -> task.getState() == TaskState.NEW)
                    .collect(Collectors.toList());
        }
    }
}
