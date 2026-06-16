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

// URL path for accessing this servlet
@WebServlet("/edit-task")
public class UpdateTaskServlet extends HttpServlet {

    private TaskRepository taskRepository;

    @Override
    public void init() {
        taskRepository = TaskRepository.getTaskRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.isBlank()) {
            try {
                int id = Integer.parseInt(idStr);
                Task task = taskRepository.read(id);
                if (task != null) {
                    request.setAttribute("task", task);
                    request.getRequestDispatcher(Pages.EDIT.webInfUrl()).forward(request, response);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    request.setAttribute("message", "Task with ID '" + id + "' not found!");
                    request.getRequestDispatcher(Pages.ERROR.webInfUrl()).forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("message", "Invalid Task ID format!");
                request.getRequestDispatcher(Pages.ERROR.webInfUrl()).forward(request, response);
            }
        } else {
            request.setAttribute("message", "Task ID is missing!");
            request.getRequestDispatcher(Pages.ERROR.webInfUrl()).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String priorityStr = request.getParameter("priority");
        String idStr = request.getParameter("id");
        int id;
        Task task;

        if (idStr != null && !idStr.isBlank()) {
            try {
                id = Integer.parseInt(idStr);
                boolean taskFound = (task = taskRepository.read(id)) != null;
                if (!taskFound) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    request.setAttribute("message", "Task with ID '" + id + "' not found!");
                    request.getRequestDispatcher(Pages.ERROR.webInfUrl()).forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                request.setAttribute("message", "Invalid Task ID format!");
                request.getRequestDispatcher(Pages.ERROR.webInfUrl()).forward(request, response);
                return;
            }
        } else {
            request.setAttribute("message", "Task ID is missing!");
            request.getRequestDispatcher(Pages.ERROR.webInfUrl()).forward(request, response);
            return;
        }


        if (title != null && !title.isBlank() && priorityStr != null && !priorityStr.isBlank()) {
            try {
                Priority priority = Priority.valueOf(priorityStr);
                Task newTask = new Task(id, title, priority);
                if (!newTask.equals(task)) {
                    boolean created = taskRepository.update(newTask);
                    if (created) {
                        response.sendRedirect(Pages.LIST.url());
                    } else {
                        request.setAttribute("error", "Task with a given name already exists!");
                        request.setAttribute("prevTitle", title);
                        request.setAttribute("prevPriority", priority);
                        request.setAttribute("task", task);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.EDIT.webInfUrl());
                        requestDispatcher.forward(request, response);
                    }
                } else {
                    request.setAttribute("error", "No changes were made!");
                    request.setAttribute("prevTitle", title);
                    request.setAttribute("prevPriority", priority);
                    request.setAttribute("task", task);
                    request.getRequestDispatcher(Pages.EDIT.webInfUrl()).forward(request, response);
                }
            } catch (IllegalArgumentException e) {
                request.setAttribute("error", "Invalid priority!");
                request.setAttribute("prevTitle", title);
                request.setAttribute("task", task);
                request.getRequestDispatcher(Pages.EDIT.webInfUrl()).forward(request, response);
            }
        } else {
            request.setAttribute("error", "Title and priority must not be empty!");
            request.setAttribute("task", task);
            request.getRequestDispatcher(Pages.EDIT.webInfUrl()).forward(request, response);
        }
    }
}