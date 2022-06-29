package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MembreListServlet extends HttpServletBase {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String action = req.getServletPath();

        if (action.equals("/membre_list")) {
            try {
                req.setAttribute("membreList", membreService.getList());
            } catch (Exception e) {
                e.printStackTrace();
            }
            req.getRequestDispatcher("/WEB-INF/View/membre_list.jsp").forward(req, resp);

        }
    }
}
