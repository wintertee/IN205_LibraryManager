package com.ensta.librarymanager.servlet;

import javax.servlet.http.HttpServlet;

import com.ensta.librarymanager.service.impl.EmpruntService;
import com.ensta.librarymanager.service.impl.LivreService;
import com.ensta.librarymanager.service.impl.MembreService;

public abstract class HttpServletBase extends HttpServlet {

    protected final MembreService membreService = MembreService.getInstance();
    protected final LivreService livreService = LivreService.getInstance();
    protected final EmpruntService empruntService = EmpruntService.getInstance();
}
