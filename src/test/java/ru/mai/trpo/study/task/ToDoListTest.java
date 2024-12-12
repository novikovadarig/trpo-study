/*
 * Copyright (c) 2024, TopS BI LLC. All rights reserved.
 * http://www.topsbi.ru
 */

package ru.mai.trpo.study.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import ru.mai.trpo.study.model.ToDoList;

class ToDoListTest {

    private ToDoList toDoList;

    @BeforeEach
    void setUp() {
        toDoList = new ToDoList();
    }

    @Test
    void addTask_shouldAddNewTask() {
        toDoList.addTask("Task 1");
        List<ToDoList.Task> tasks = toDoList.getTasks();
        assertEquals(1, tasks.size());
        assertEquals("Task 1", tasks.get(0).getTitle());
    }

    @Test
    void addTask_shouldThrowExceptionForNullTitle() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> toDoList.addTask(null));
        assertEquals("Task title cannot be null or empty.", exception.getMessage());
    }

    @Test
    void addTask_shouldThrowExceptionForEmptyTitle() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> toDoList.addTask(" "));
        assertEquals("Task title cannot be null or empty.", exception.getMessage());
    }

    @Test
    void removeTask_shouldRemoveExistingTask() {
        toDoList.addTask("Task 1");
        toDoList.addTask("Task 2");
        toDoList.removeTask(0);
        List<ToDoList.Task> tasks = toDoList.getTasks();
        assertEquals(1, tasks.size());
        assertEquals("Task 2", tasks.get(0).getTitle());
    }

    @Test
    void removeTask_shouldThrowExceptionForInvalidIndex() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> toDoList.removeTask(0));
        assertEquals("Invalid task index: 0", exception.getMessage());
    }

    @Test
    void markTaskAsCompleted_shouldMarkTaskAsCompleted() {
        toDoList.addTask("Task 1");
        toDoList.markTaskAsCompleted(0);
        List<ToDoList.Task> tasks = toDoList.getTasks();
        assertTrue(tasks.get(0).isCompleted());
    }

    @Test
    void markTaskAsCompleted_shouldThrowExceptionForInvalidIndex() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> toDoList.markTaskAsCompleted(0));
        assertEquals("Invalid task index: 0", exception.getMessage());
    }

    @Test
    void getTasks_shouldReturnAllTasks() {
        toDoList.addTask("Task 1");
        toDoList.addTask("Task 2");
        List<ToDoList.Task> tasks = toDoList.getTasks();
        assertEquals(2, tasks.size());
        assertEquals("Task 1", tasks.get(0).getTitle());
        assertEquals("Task 2", tasks.get(1).getTitle());
    }

    @Test
    void getTasks_shouldReturnEmptyListIfNoTasksAdded() {
        List<ToDoList.Task> tasks = toDoList.getTasks();
        assertTrue(tasks.isEmpty());
    }

    @Test
    void taskState_shouldReturnNewForNewTasks() {
        toDoList.addTask("Task 1");
        ToDoList.Task task = toDoList.getTasks().get(0);
        assertEquals("New", task.toString().split("state=")[1].replace("}", ""));
    }

    @Test
    void taskState_shouldReturnCompletedForCompletedTasks() {
        toDoList.addTask("Task 1");
        toDoList.markTaskAsCompleted(0);
        ToDoList.Task task = toDoList.getTasks().get(0);
        assertEquals("Completed", task.toString().split("state=")[1].replace("}", ""));
    }

    @Test
    void toString_shouldProvideCorrectTaskRepresentation() {
        toDoList.addTask("Task 1");
        ToDoList.Task task = toDoList.getTasks().get(0);
        assertTrue(task.toString().contains("title='Task 1'") && task.toString().contains("state=New"));
    }

    @Test
    void taskCreation_shouldAssignCorrectTitle() {
        toDoList.addTask("Task 1");
        assertEquals("Task 1", toDoList.getTasks().get(0).getTitle());
    }

    @Test
    void tasksList_shouldBeEncapsulated() {
        toDoList.addTask("Task 1");
        List<ToDoList.Task> tasks = toDoList.getTasks();
        tasks.clear(); // This should not affect the internal list
        assertEquals(1, toDoList.getTasks().size());
    }
}
