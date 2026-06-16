package com.softserve.itacademy.model;

import java.util.Objects;

public class Task {

    private final int id;
    private String title;
    private Priority priority;

    private static int counter = 0;


    public Task() {
        this.id = counter++;
    }

    public Task(String title, Priority priority) {
        this();
        this.title = title;
        this.priority = priority;
    }

    public Task(int id, String title, Priority priority) {
        this.id = id;
        this.title = title;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", priority=" + priority +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(title, task.title) && priority == task.priority;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, priority);
    }
}
