package com.softserve.itacademy.controller;

public enum Pages {
    CREATE("create-task"),
    EDIT("edit-task"),
    ERROR("error"),
    HOME("home"),
    READ("read-task"),
    LIST("tasks-list");

    private final String url;

    Pages(String filename) {
        this.url = filename;
    }

    public String url() {
        return "/" + url;
    }

    public String webInfUrl() {
        return "/WEB-INF/pages/" + url + ".jsp";
    }
}
