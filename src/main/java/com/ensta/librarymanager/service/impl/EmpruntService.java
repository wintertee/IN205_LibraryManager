package com.ensta.librarymanager.service.impl;

import java.time.LocalDate;
import java.util.List;

import com.ensta.librarymanager.dao.impl.EmpruntDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Abonnement;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.service.IEmpruntService;

public class EmpruntService implements IEmpruntService {
    private EmpruntDao empruntDao = EmpruntDao.getInstance();
    private static EmpruntService instance;

    private EmpruntService() {
    }

    public static EmpruntService getInstance() {
        if (instance == null) {
            instance = new EmpruntService();
        }
        return instance;
    }

    @Override
    public List<Emprunt> getList() throws ServiceException {
        try {
            return empruntDao.getList();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Emprunt> getListCurrent() throws ServiceException {
        try {
            return empruntDao.getListCurrent();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException {
        try {
            return empruntDao.getListCurrentByMembre(idMembre);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException {
        try {
            return empruntDao.getListCurrentByLivre(idLivre);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Emprunt getById(int id) throws ServiceException {
        try {
            return empruntDao.getById(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws ServiceException {
        try {
            empruntDao.create(idMembre, idLivre, dateEmprunt);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void returnBook(int id) throws ServiceException {
        try {
            Emprunt emprunt = empruntDao.getById(id);
            emprunt.setDateRetour(LocalDate.now());
            empruntDao.update(emprunt);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public int count() throws ServiceException {
        try {
            return empruntDao.count();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean isLivreDispo(int idLivre) throws ServiceException {
        try {
            List<Emprunt> empruntList = empruntDao.getListCurrentByLivre(idLivre);
            if (empruntList.size() > 0 && empruntList.get(empruntList.size() - 1).getDateRetour() != null) {
                return true;
            }
            if (empruntList.size() == 0) {
                return true;
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean isEmpruntPossible(Membre membre) throws ServiceException {
        try {
            List<Emprunt> empruntList = empruntDao.getListCurrentByMembre(membre.getId());
            if (empruntList.size() >= Abonnement.getMaxEmprunt(membre.getAbonnement())) {
                return false;
            } else
                return true;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
