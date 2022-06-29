package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;

public class MembreDeleteServlet extends HttpServletBase {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String action = req.getServletPath();
        if (action.equals("/membre_delete")) {
            int id = -1;
            if (req.getParameter("id") != null) {
                id = Integer.parseInt(req.getParameter("id"));
            } else {
                throw new ServletException("null id");
            }
            try {
                if (id != -1) {
                    req.setAttribute("membre", membreService.getById(id));
                }
            } catch (ServiceException e) {
                throw new ServletException(e.getMessage());
            }
            req.getRequestDispatcher("/WEB-INF/View/membre_delete.jsp").forward(req, resp);

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            membreService.delete(Integer.parseInt(req.getParameter("id")));
        } catch (ServiceException e) {
            throw new ServletException(e.getMessage());
        }
        resp.sendRedirect("/LibraryManager/membre_list");
    }
}
