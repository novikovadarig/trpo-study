/*
 * Copyright (c) 2024, TopS BI LLC. All rights reserved.
 * http://www.topsbi.ru
 */

package ru.mai.trpo.study.model;

import java.util.ArrayList;
import java.util.List;

public class ToDoList {

    private final List<Task> tasks = new ArrayList<>();

    // Добавление задачи
    public void addTask(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be null or empty.");
        }
        tasks.add(new Task(title));
    }

    // Удаление задачи
    public void removeTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("Invalid task index: " + index);
        }
        tasks.remove(index);
    }

    // Отметка задачи как выполненной
    public void markTaskAsCompleted(int index) {
        Task task = getTask(index);
        task.markCompleted();
    }

    // Получение всех задач
    public List<Task> getTasks() {
        return new ArrayList<>(tasks); // Возвращаем копию списка для сохранения инкапсуляции
    }

    // Получение конкретной задачи
    private Task getTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("Invalid task index: " + index);
        }
        return tasks.get(index);
    }

    // Класс задачи
    public static class Task {
        private final String title;
        private TaskState state;

        public Task(String title) {
            this.title = title;
            this.state = new NewState();
        }

        public String getTitle() {
            return title;
        }

        public boolean isCompleted() {
            return state instanceof CompletedState;
        }

        public void markCompleted() {
            this.state = new CompletedState();
        }

        @Override
        public String toString() {
            return "Task{" +
                    "title='" + title + '\'' +
                    ", state=" + state.getStateName() +
                    '}';
        }
    }

    // Шаблон "Состояние" для управления состояниями задачи
    interface TaskState {
        String getStateName();
    }

    static class NewState implements TaskState {
        @Override
        public String getStateName() {
            return "New";
        }
    }

    static class CompletedState implements TaskState {
        @Override
        public String getStateName() {
            return "Completed";
        }
    }
}
