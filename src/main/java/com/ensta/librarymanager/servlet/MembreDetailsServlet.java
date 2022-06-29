package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Abonnement;
import com.ensta.librarymanager.modele.Membre;

public class MembreDetailsServlet extends HttpServletBase {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String action = req.getServletPath();
        if (action.equals("/membre_details")) {
            try {
                int id = -1;
                if (req.getParameter("id") != null) {
                    id = Integer.parseInt(req.getParameter("id"));
                } else {
                    throw new ServletException("null id");
                }
                req.setAttribute("membre", membreService.getById(id));
                req.setAttribute("listEmprunt", empruntService.getListCurrentByMembre(id));
            } catch (ServiceException e) {
                throw new ServletException(e.getMessage());
            }
            req.getRequestDispatcher("/WEB-INF/View/membre_details.jsp").forward(req, resp);

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = -1;
        if (req.getParameter("id") != null) {
            id = Integer.parseInt(req.getParameter("id"));
        } else {
            throw new ServletException("null id");
        }

        String nom = req.getParameter("nom");
        String prenom = req.getParameter("prenom");
        if (nom == null || nom.isEmpty() || prenom == null || prenom.isEmpty()) {
            throw new ServletException("empty name");
        }

        try {
            Membre membre = membreService.getById(id);
            membre.setNom(nom);
            membre.setPrenom(prenom);
            membre.setAdresse(req.getParameter("adresse"));
            membre.setEmail(req.getParameter("email"));
            membre.setTelephone(req.getParameter("telephone"));
            membre.setAbonnement(Abonnement.valueOf(req.getParameter("abonnement")));

            membreService.update(membre);
            req.setAttribute("membre", membreService.getById(membre.getId()));
            req.setAttribute("listEmprunt", empruntService.getListCurrentByMembre(membre.getId()));
            resp.sendRedirect("/LibraryManager/membre_details?id=" + membre.getId());
        } catch (ServiceException e) {
            throw new ServletException(e.getMessage());
        }

    }
}
