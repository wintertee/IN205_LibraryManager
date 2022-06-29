package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;

public class LivreListServlet extends HttpServletBase {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String action = req.getServletPath();

        if (action.equals("/livre_list")) {
            try {
                req.setAttribute("list", livreService.getList());
            } catch (ServiceException e) {
                throw new ServletException(e.getMessage());
            }
            req.getRequestDispatcher("/WEB-INF/View/livre_list.jsp").forward(req, resp);

        }
    }
}
