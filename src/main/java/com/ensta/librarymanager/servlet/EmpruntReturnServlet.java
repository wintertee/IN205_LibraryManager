package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;

public class EmpruntReturnServlet extends HttpServletBase {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();
        int id = -1;
        if (action.equals("/emprunt_return")) {
            if (req.getParameter("id") != null) {
                id = Integer.parseInt(req.getParameter("id"));
            }
            try {
                if (id != -1) {
                    req.setAttribute("empruntById", empruntService.getById(id));
                }
                req.setAttribute("listCurrent", empruntService.getListCurrent());
            } catch (ServiceException e) {
                throw new ServletException(e.getMessage());
            }
            req.getRequestDispatcher("/WEB-INF/View/emprunt_return.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("id") == null) {
                throw new ServletException("empty id");
            } else {
                empruntService.returnBook(Integer.parseInt(req.getParameter("id")));
            }
        } catch (ServiceException e) {
            throw new ServletException(e.getMessage());
        }

        try {
            req.setAttribute("listCurrent", empruntService.getListCurrent());
        } catch (ServiceException e) {
            throw new ServletException(e.getMessage());
        }
        resp.sendRedirect("/LibraryManager/emprunt_list");
    }

}
