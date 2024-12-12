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

public class ToDoListTest {

    private ToDoList toDoList;

    @BeforeEach
    void setUp() {
        toDoList = new ToDoList();
    }

    @Test
    void addTask_ValidTitle_TaskAdded() {
        toDoList.addTask("Task 1");
        assertEquals(1, toDoList.getTasks(new ToDoList.AllTasksFilter()).size());
    }

    @Test
    void addTask_NullTitle_ExceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> toDoList.addTask(null));
    }

    @Test
    void addTask_EmptyTitle_ExceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> toDoList.addTask(""));
    }

    @Test
    void removeTask_ValidIndex_TaskRemoved() {
        toDoList.addTask("Task 1");
        toDoList.removeTask(0);
        assertEquals(0, toDoList.getTasks(new ToDoList.AllTasksFilter()).size());
    }

    @Test
    void removeTask_InvalidIndex_ExceptionThrown() {
        assertThrows(IndexOutOfBoundsException.class, () -> toDoList.removeTask(0));
    }

    @Test
    void markTaskAsCompleted_ValidIndex_TaskMarkedAsCompleted() {
        toDoList.addTask("Task 1");
        toDoList.markTaskAsCompleted(0);
        assertEquals(1, toDoList.getTasks(new ToDoList.CompletedTasksFilter()).size());
    }

    @Test
    void markTaskAsCompleted_InvalidIndex_ExceptionThrown() {
        assertThrows(IndexOutOfBoundsException.class, () -> toDoList.markTaskAsCompleted(0));
    }

    @Test
    void getTasks_AllTasksFilter_ReturnsAllTasks() {
        toDoList.addTask("Task 1");
        toDoList.addTask("Task 2");
        List<ToDoList.Task> tasks = toDoList.getTasks(new ToDoList.AllTasksFilter());
        assertEquals(2, tasks.size());
    }

    @Test
    void getTasks_CompletedTasksFilter_ReturnsCompletedTasks() {
        toDoList.addTask("Task 1");
        toDoList.addTask("Task 2");
        toDoList.markTaskAsCompleted(1);
        List<ToDoList.Task> tasks = toDoList.getTasks(new ToDoList.CompletedTasksFilter());
        assertEquals(1, tasks.size());
    }

    @Test
    void getTasks_NewTasksFilter_ReturnsNewTasks() {
        toDoList.addTask("Task 1");
        toDoList.addTask("Task 2");
        List<ToDoList.Task> tasks = toDoList.getTasks(new ToDoList.NewTasksFilter());
        assertEquals(2, tasks.size());
    }

    @Test
    void taskToString_ReturnsCorrectStringRepresentation() {
        ToDoList.Task task = new ToDoList.Task("Task 1", ToDoList.TaskState.NEW);
        assertEquals("Task[title='Task 1', state='NEW']", task.toString());
    }

    @Test
    void taskConstructor_DefaultStateIsNew() {
        ToDoList.Task task = new ToDoList.Task("Task 1");
        assertEquals(ToDoList.TaskState.NEW, task.getState());
    }

    @Test
    void allTasksFilter_FilterReturnsCopy() {
        List<ToDoList.Task> tasks = List.of(
                new ToDoList.Task("Task 1"),
                new ToDoList.Task("Task 2")
        );
        List<ToDoList.Task> filteredTasks = new ToDoList.AllTasksFilter().filter(tasks);
        assertNotSame(tasks, filteredTasks);
    }

    @Test
    void completedTasksFilter_FiltersOnlyCompletedTasks() {
        List<ToDoList.Task> tasks = List.of(
                new ToDoList.Task("Task 1", ToDoList.TaskState.NEW),
                new ToDoList.Task("Task 2", ToDoList.TaskState.COMPLETED)
        );
        List<ToDoList.Task> filteredTasks = new ToDoList.CompletedTasksFilter().filter(tasks);
        assertEquals(1, filteredTasks.size());
        assertEquals("Task 2", filteredTasks.get(0).getTitle());
    }

    @Test
    void newTasksFilter_FiltersOnlyNewTasks() {
        List<ToDoList.Task> tasks = List.of(
                new ToDoList.Task("Task 1", ToDoList.TaskState.NEW),
                new ToDoList.Task("Task 2", ToDoList.TaskState.COMPLETED)
        );
        List<ToDoList.Task> filteredTasks = new ToDoList.NewTasksFilter().filter(tasks);
        assertEquals(1, filteredTasks.size());
        assertEquals("Task 1", filteredTasks.get(0).getTitle());
    }
}
