package com.ensta.librarymanager.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ensta.librarymanager.dao.impl.MembreDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.service.IMembreService;

public class MembreService implements IMembreService {
    private MembreDao membreDao = MembreDao.getInstance();
    private EmpruntService empruntService = EmpruntService.getInstance();
    private static MembreService instance;

    private MembreService() {
    }

    public static MembreService getInstance() {
        if (instance == null) {
            instance = new MembreService();
        }
        return instance;
    }

    @Override
    public List<Membre> getList() throws ServiceException {
        List<Membre> list = new ArrayList<>();
        try {
            list = membreDao.getList();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return list;
    }

    @Override
    public List<Membre> getListMembreEmpruntPossible() throws ServiceException {
        List<Membre> list = this.getList();
        Iterator<Membre> iter = list.iterator();
        while (iter.hasNext()) {
            Membre member = iter.next();
            if (!empruntService.isEmpruntPossible(member)) {
                iter.remove();
            }
        }
        return list;
    }

    @Override
    public Membre getById(int id) throws ServiceException {
        Membre member = null;
        try {
            member = membreDao.getById(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return member;
    }

    @Override
    public int create(String nom, String prenom, String adresse, String email, String telephone)
            throws ServiceException {
        if (nom == null & prenom == null) {
            throw new ServiceException("Empty name");
        }
        nom = nom.toUpperCase();
        int id = -1;
        try {
            id = MembreDao.getInstance().create(nom, prenom, adresse, email, telephone);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return id;
    }

    @Override
    public void update(Membre membre) throws ServiceException {
        if (membre.getNom() == null & membre.getPrenom() == null) {
            throw new ServiceException("Empty name");
        }
        membre.setNom(membre.getNom().toUpperCase());
        try {
            membreDao.update(membre);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }

    }

    @Override
    public void delete(int id) throws ServiceException {
        try {
            membreDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }

    }

    @Override
    public int count() throws ServiceException {
        int count = 0;
        try {
            count = membreDao.count();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return count;
    }

}