package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;

public class EmpruntListServlet extends HttpServletBase {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();
        if (action.equals("/emprunt_list")) {
            try {
                if (req.getParameter("show") != null && req.getParameter("show").equals("all"))
                    req.setAttribute("list", empruntService.getList());
                else
                    req.setAttribute("list", empruntService.getListCurrent());
            } catch (ServiceException e) {
                throw new ServletException(e.getMessage());
            }
        }
        req.getRequestDispatcher("/WEB-INF/View/emprunt_list.jsp").forward(req, resp);

    }

}