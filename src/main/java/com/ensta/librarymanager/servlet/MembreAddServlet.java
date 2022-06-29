package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;

public class MembreAddServlet extends HttpServletBase {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String action = req.getServletPath();
        if (action.equals("/membre_add")) {
            req.getRequestDispatcher("/WEB-INF/View/membre_add.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = -1;
        String nom = req.getParameter("nom");
        String prenom = req.getParameter("prenom");
        if (nom == null || nom.isEmpty() || prenom == null || prenom.isEmpty()) {
            throw new ServletException("empty name");
        }
        try {
            id = membreService.create(nom,
                    prenom,
                    req.getParameter("adresse"),
                    req.getParameter("email"),
                    req.getParameter("telephone"));
        } catch (ServiceException e) {
            throw new ServletException(e.getMessage());
        }

        resp.sendRedirect("/LibraryManager/membre_details?id=" + id);
    }
}
