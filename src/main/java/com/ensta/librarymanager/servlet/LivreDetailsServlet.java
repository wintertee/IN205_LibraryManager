package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Livre;

public class LivreDetailsServlet extends HttpServletBase {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String action = req.getServletPath();
        if (action.equals("/livre_details")) {
            try {
                req.setAttribute("livre", livreService.getById(Integer.parseInt(req.getParameter("id"))));
                req.setAttribute("empruntList",
                        empruntService.getListCurrentByLivre(Integer.parseInt(req.getParameter("id"))));
            } catch (ServiceException e) {
                throw new ServletException(e.getMessage());
            }
            req.getRequestDispatcher("/WEB-INF/View/livre_details.jsp").forward(req, resp);

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String titre = req.getParameter("titre");
        String auteur = req.getParameter("auteur");
        String isbn = req.getParameter("isbn");
        if (titre == null || titre.isEmpty()) {
            throw new ServletException("empty title");
        }
        try {
            Livre livre = livreService.getById(Integer.parseInt(req.getParameter("id")));
            livre.setTitre(titre);
            livre.setAuteur(auteur);
            livre.setIsbn(isbn);

            livreService.update(livre);
            req.setAttribute("livre", livreService.getById(Integer.parseInt(req.getParameter("id"))));
            req.setAttribute("empruntList",
                    empruntService.getListCurrentByLivre(Integer.parseInt(req.getParameter("id"))));
            resp.sendRedirect("/LibraryManager/livre_details?id=" + livre.getId());
        } catch (ServiceException e) {
            throw new ServletException(e.getMessage());
        }

    }
}
