package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;

public class LivreAddServlet extends HttpServletBase {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String action = req.getServletPath();
        if (action.equals("/livre_add")) {
            req.getRequestDispatcher("/WEB-INF/View/livre_add.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = -1;
        String titre = req.getParameter("titre");
        String auteur = req.getParameter("auteur");
        String isbn = req.getParameter("isbn");
        if (titre == null || titre.isEmpty()) {
            throw new ServletException("empty title");
        }
        try {
            id = livreService.create(titre, auteur, isbn);
        } catch (ServiceException e) {
            throw new ServletException(e.getMessage());
        }

        if (id == -1) {
            throw new ServletException("error book index");
        }

        resp.sendRedirect("/LibraryManager/livre_details?id=" + id);
    }

}
