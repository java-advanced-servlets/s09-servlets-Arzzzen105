package com.softserve.itacademy.controller;

import com.softserve.itacademy.repository.TaskRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


// Specifies the URL path for this servlet
@WebServlet("/delete-task")
public class DeleteTaskServlet extends HttpServlet {

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
                boolean status = taskRepository.delete(id);
                if (status) {
                    response.sendRedirect(Pages.LIST.url());
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
}