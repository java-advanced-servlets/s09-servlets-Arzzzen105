package com.softserve.itacademy.model;

public class Task {

    private final int id;
    private String title;
    private Priority priority;

    private static int counter = 1;

    { id = counter++; }

    public Task() {

    }

    public Task(String title, Priority priority) {
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
}
