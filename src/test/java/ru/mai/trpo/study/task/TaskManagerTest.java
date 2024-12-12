/*
 * Copyright (c) 2024, TopS BI LLC. All rights reserved.
 * http://www.topsbi.ru
 */

package ru.mai.trpo.study.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import ru.mai.trpo.study.model.Task;
import ru.mai.trpo.study.service.TaskManager;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {

    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new TaskManager();
    }

    @Test
    void addTask_ShouldIncreaseTaskListSize() {
        taskManager.addTask("Task 1");
        assertEquals(1, taskManager.getTasks().size());
    }

    @Test
    void addTask_ShouldAddTaskWithCorrectTitle() {
        taskManager.addTask("Task 1");
        assertEquals("Task 1", taskManager.getTasks().get(0).getTitle());
    }

    @Test
    void removeTask_ShouldDecreaseTaskListSize() {
        taskManager.addTask("Task 1");
        taskManager.removeTask(0);
        assertEquals(0, taskManager.getTasks().size());
    }

    @Test
    void removeTask_ShouldNotThrowExceptionForInvalidIndex() {
        assertDoesNotThrow(() -> taskManager.removeTask(5));
    }

    @Test
    void removeTask_ShouldNotAffectListForInvalidIndex() {
        taskManager.addTask("Task 1");
        taskManager.removeTask(5);
        assertEquals(1, taskManager.getTasks().size());
    }

    @Test
    void markTaskAsCompleted_ShouldSetTaskAsCompleted() {
        taskManager.addTask("Task 1");
        taskManager.markTaskAsCompleted(0);
        assertTrue(taskManager.getTasks().get(0).isCompleted());
    }

    @Test
    void markTaskAsCompleted_ShouldNotThrowExceptionForInvalidIndex() {
        assertDoesNotThrow(() -> taskManager.markTaskAsCompleted(5));
    }

    @Test
    void getTasks_ShouldReturnAllAddedTasks() {
        taskManager.addTask("Task 1");
        taskManager.addTask("Task 2");
        List<Task> tasks = taskManager.getTasks();
        assertEquals(2, tasks.size());
        assertEquals("Task 1", tasks.get(0).getTitle());
        assertEquals("Task 2", tasks.get(1).getTitle());
    }

    @Test
    void addTask_ShouldNotAddNullTask() {
        taskManager.addTask(null);
        assertEquals(1, taskManager.getTasks().size());
    }

    @Test
    void addTask_ShouldAddUniqueTasks() {
        taskManager.addTask("Task 1");
        taskManager.addTask("Task 2");
        assertEquals(2, taskManager.getTasks().size());
    }

    @Test
    void taskDefaultCompletionState_ShouldBeFalse() {
        taskManager.addTask("Task 1");
        assertFalse(taskManager.getTasks().get(0).isCompleted());
    }

    @Test
    void tasksList_ShouldBeEmptyInitially() {
        assertTrue(taskManager.getTasks().isEmpty());
    }

    @Test
    void addTask_ShouldHandleEmptyStringTitle() {
        taskManager.addTask("");
        assertEquals("", taskManager.getTasks().get(0).getTitle());
    }

    @Test
    void addTask_ShouldHandleSpecialCharactersInTitle() {
        taskManager.addTask("@#$%^&*()_+");
        assertEquals("@#$%^&*()_+", taskManager.getTasks().get(0).getTitle());
    }

    @Test
    void markTaskAsCompleted_ShouldOnlyAffectSpecifiedTask() {
        taskManager.addTask("Task 1");
        taskManager.addTask("Task 2");
        taskManager.markTaskAsCompleted(1);
        assertFalse(taskManager.getTasks().get(0).isCompleted());
        assertTrue(taskManager.getTasks().get(1).isCompleted());
    }
}
