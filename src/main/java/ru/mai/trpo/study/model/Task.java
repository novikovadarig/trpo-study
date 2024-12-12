/*
 * Copyright (c) 2024, TopS BI LLC. All rights reserved.
 * http://www.topsbi.ru
 */

package ru.mai.trpo.study.model;

import lombok.Data;

@Data
public class Task {
    private String title;
    private boolean completed;

    public Task(String title) {
        this.title = title;
        this.completed = false;
    }
}
