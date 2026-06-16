package com.softserve.itacademy.controller;

import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.repository.TaskRepository;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/create-task")
public class CreateTaskServlet extends HttpServlet {

    private TaskRepository taskRepository;

    @Override
    public void init() {
        taskRepository = TaskRepository.getTaskRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.CREATE.webInfUrl());
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String priorityStr = request.getParameter("priority");

        if (title != null && !title.isEmpty() && priorityStr != null && !priorityStr.isEmpty()) {
            Priority priority = Priority.valueOf(priorityStr);
            Task task = new Task(title, priority);

            boolean created = taskRepository.create(task);

            if (created) {
                response.sendRedirect(Pages.LIST.url());
            } else {
                request.setAttribute("error", "Task with a given name already exists!");
                request.setAttribute("title", title);
                request.setAttribute("priority", priority);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.CREATE.webInfUrl());
                requestDispatcher.forward(request, response);
            }
        } else {
            request.setAttribute("error", "Title and priority must not be empty!");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.CREATE.webInfUrl());
            requestDispatcher.forward(request, response);
        }
    }
}
