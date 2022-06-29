package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;

public class LivreDeleteServlet extends HttpServletBase {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String action = req.getServletPath();
        if (action.equals("/livre_delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            try {
                req.setAttribute("livre", livreService.getById(id));
            } catch (ServiceException e) {
                throw new ServletException(e.getMessage());
            }
            req.getRequestDispatcher("/WEB-INF/View/livre_delete.jsp").forward(req, resp);

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            livreService.delete(Integer.parseInt(req.getParameter("id")));
        } catch (ServiceException e) {
            throw new ServletException(e.getMessage());
        }
        resp.sendRedirect("/LibraryManager/livre_list");
    }
}
