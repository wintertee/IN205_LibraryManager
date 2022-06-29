package com.ensta.librarymanager.servlet;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;

public class EmpruntAddServlet extends HttpServletBase {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getServletPath();
        if (action.equals("/emprunt_add")) {
            try {
                req.setAttribute("listDispo", livreService.getListDispo());
                req.setAttribute("listMembreEmpruntPossible", membreService.getListMembreEmpruntPossible());
            } catch (ServiceException e) {
                throw new ServletException(e.getMessage());
            }
            req.getRequestDispatcher("/WEB-INF/View/emprunt_add.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            empruntService.create(Integer.parseInt(req.getParameter("idMembre")),
                    Integer.parseInt(req.getParameter("idLivre")), LocalDate.now());
        } catch (ServiceException e) {
            throw new ServletException(e.getMessage());
        }

        try {
            req.setAttribute("listDispo", livreService.getListDispo());
            req.setAttribute("listMembreEmpruntPossible", membreService.getListMembreEmpruntPossible());
        } catch (ServiceException e) {
            throw new ServletException(e.getMessage());
        }
        resp.sendRedirect("/LibraryManager/emprunt_list");
    }

}