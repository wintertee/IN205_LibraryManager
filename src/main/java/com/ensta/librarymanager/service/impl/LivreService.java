package com.ensta.librarymanager.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ensta.librarymanager.dao.impl.LivreDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.service.ILivreService;

public class LivreService implements ILivreService {
    private LivreDao livreDao = LivreDao.getInstance();
    private EmpruntService enpruntService = EmpruntService.getInstance();
    private static LivreService instance;

    private LivreService() {
    }

    public static LivreService getInstance() {
        if (instance == null) {
            instance = new LivreService();
        }
        return instance;
    }

    @Override
    public List<Livre> getList() throws ServiceException {
        List<Livre> list = new ArrayList<>();
        try {
            list = livreDao.getList();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return list;
    }

    public List<Livre> getListDispo() throws ServiceException {
        List<Livre> listDispo = getList();
        Iterator<Livre> iter = listDispo.iterator();
        while (iter.hasNext()) {
            Livre livre = iter.next();
            if (!enpruntService.isLivreDispo(livre.getId())) {
                iter.remove();
            }
        }
        return listDispo;

    }

    public Livre getById(int id) throws ServiceException {
        Livre livre = null;
        try {
            livre = livreDao.getById(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return livre;
    }

    public int create(String titre, String auteur, String isbn) throws ServiceException {
        int id = -1;
        if (titre == null) {
            throw new ServiceException("Empty title");
        }
        try {
            id = livreDao.create(titre, auteur, isbn);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
        return id;
    }

    public void update(Livre livre) throws ServiceException {
        if (livre.getTitre() == null)
            throw new ServiceException("Empty title");
        try {
            LivreDao.getInstance().update(livre);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void delete(int id) throws ServiceException {
        try {
            LivreDao.getInstance().delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public int count() throws ServiceException {
        int count = 0;
        try {
            count = LivreDao.getInstance().count();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return count;
    }
}
