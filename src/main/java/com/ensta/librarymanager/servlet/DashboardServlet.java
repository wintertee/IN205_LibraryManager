package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;

public class DashboardServlet extends HttpServletBase {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();
        if (action.equals("/dashboard")) {

            try {
                req.setAttribute("membreCount", membreService.count());
                req.setAttribute("livreCount", livreService.count());
                req.setAttribute("empruntCount", empruntService.count());
                req.setAttribute("empruntList", empruntService.getListCurrent());
            } catch (ServiceException e) {
                throw new ServletException(e.getMessage());
            }
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/View/dashboard.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
