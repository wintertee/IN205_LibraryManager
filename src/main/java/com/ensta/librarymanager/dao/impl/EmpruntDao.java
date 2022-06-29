package com.ensta.librarymanager.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.dao.IEmpruntDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Abonnement;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class EmpruntDao implements IEmpruntDao {

    private static EmpruntDao instance;

    private EmpruntDao() {
    }

    public static EmpruntDao getInstance() {
        if (instance == null) {
            instance = new EmpruntDao();
        }
        return instance;
    }

    @Override
    public List<Emprunt> getList() throws DaoException {
        List<Emprunt> emprutList = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = """
                    SELECT e.id AS id, idMembre, nom, prenom, adresse, email,
                        telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,
                        dateRetour
                    FROM emprunt AS e
                    INNER JOIN membre ON membre.id = e.idMembre
                    INNER JOIN livre ON livre.id = e.idLivre
                    ORDER BY dateRetour DESC;
                    """;
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                int id = res.getInt("id");
                int idMembre = res.getInt("idMembre");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                String adresse = res.getString("adresse");
                String email = res.getString("email");
                String telephone = res.getString("telephone");
                Abonnement abonnement = Abonnement.valueOf(res.getString("abonnement"));
                int idLivre = res.getInt("idLivre");
                String titre = res.getString("titre");
                String auteur = res.getString("auteur");
                String isbn = res.getString("isbn");
                LocalDate dateEmprunt = res.getDate("dateEmprunt").toLocalDate();
                LocalDate dateRetour = null;
                if (res.getDate("dateRetour") != null)
                    dateRetour = res.getDate("dateRetour").toLocalDate();
                emprutList.add(
                        new Emprunt(id,
                                new Membre(idMembre, nom, prenom, adresse, email, telephone, abonnement),
                                new Livre(idLivre, titre, auteur, isbn),
                                dateEmprunt,
                                dateRetour));
            }

        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
            throw new DaoException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emprutList;

    }

    @Override
    public List<Emprunt> getListCurrent() throws DaoException {
        List<Emprunt> emprutList = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = """
                    SELECT e.id AS id, idMembre, nom, prenom, adresse, email,
                        telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,
                        dateRetour
                    FROM emprunt AS e
                    INNER JOIN membre ON membre.id = e.idMembre
                    INNER JOIN livre ON livre.id = e.idLivre
                    WHERE dateRetour IS NULL;
                    """;
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                int id = res.getInt("id");
                int idMembre = res.getInt("idMembre");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                String adresse = res.getString("adresse");
                String email = res.getString("email");
                String telephone = res.getString("telephone");
                Abonnement abonnement = Abonnement.valueOf(res.getString("abonnement"));
                int idLivre = res.getInt("idLivre");
                String titre = res.getString("titre");
                String auteur = res.getString("auteur");
                String isbn = res.getString("isbn");
                LocalDate dateEmprunt = res.getDate("dateEmprunt").toLocalDate();
                LocalDate dateRetour = null;
                emprutList.add(
                        new Emprunt(id,
                                new Membre(idMembre, nom, prenom, adresse, email, telephone, abonnement),
                                new Livre(idLivre, titre, auteur, isbn),
                                dateEmprunt,
                                dateRetour));
            }

        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
            throw new DaoException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emprutList;
    }

    @Override
    public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException {
        List<Emprunt> emprutList = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = """
                    SELECT e.id AS id, idMembre, nom, prenom, adresse, email,
                        telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,
                        dateRetour
                    FROM emprunt AS e
                    INNER JOIN membre ON membre.id = e.idMembre
                    INNER JOIN livre ON livre.id = e.idLivre
                    WHERE dateRetour IS NULL AND membre.id = ?;
                    """;
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idMembre);
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                int id = res.getInt("id");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                String adresse = res.getString("adresse");
                String email = res.getString("email");
                String telephone = res.getString("telephone");
                Abonnement abonnement = Abonnement.valueOf(res.getString("abonnement"));
                int idLivre = res.getInt("idLivre");
                String titre = res.getString("titre");
                String auteur = res.getString("auteur");
                String isbn = res.getString("isbn");
                LocalDate dateEmprunt = res.getDate("dateEmprunt").toLocalDate();
                LocalDate dateRetour = null;
                emprutList.add(
                        new Emprunt(id,
                                new Membre(idMembre, nom, prenom, adresse, email, telephone, abonnement),
                                new Livre(idLivre, titre, auteur, isbn),
                                dateEmprunt,
                                dateRetour));
            }

        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
            throw new DaoException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emprutList;
    }

    @Override
    public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException {
        List<Emprunt> emprutList = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = """
                    SELECT e.id AS id, idMembre, nom, prenom, adresse, email,
                        telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,
                        dateRetour
                    FROM emprunt AS e
                    INNER JOIN membre ON membre.id = e.idMembre
                    INNER JOIN livre ON livre.id = e.idLivre
                    WHERE dateRetour IS NULL AND livre.id = ?;
                    """;
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idLivre);
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                int id = res.getInt("id");
                int idMembre = res.getInt("idMembre");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                String adresse = res.getString("adresse");
                String email = res.getString("email");
                String telephone = res.getString("telephone");
                Abonnement abonnement = Abonnement.valueOf(res.getString("abonnement"));
                String titre = res.getString("titre");
                String auteur = res.getString("auteur");
                String isbn = res.getString("isbn");
                LocalDate dateEmprunt = res.getDate("dateEmprunt").toLocalDate();
                LocalDate dateRetour = null;
                emprutList.add(
                        new Emprunt(id,
                                new Membre(idMembre, nom, prenom, adresse, email, telephone, abonnement),
                                new Livre(idLivre, titre, auteur, isbn),
                                dateEmprunt,
                                dateRetour));
            }

        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
            throw new DaoException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emprutList;
    }

    @Override
    public Emprunt getById(int id) throws DaoException {
        Emprunt emprut = null;

        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = """
                    SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email,
                        telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,
                        dateRetour
                    FROM emprunt AS e
                    INNER JOIN membre ON membre.id = e.idMembre
                    INNER JOIN livre ON livre.id = e.idLivre
                    WHERE e.id = ?;
                    """;
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet res = stmt.executeQuery();
            res.next();
            int idMembre = res.getInt("idMembre");
            String nom = res.getString("nom");
            String prenom = res.getString("prenom");
            String adresse = res.getString("adresse");
            String email = res.getString("email");
            String telephone = res.getString("telephone");
            Abonnement abonnement = Abonnement.valueOf(res.getString("abonnement"));
            int idLivre = res.getInt("idLivre");
            String titre = res.getString("titre");
            String auteur = res.getString("auteur");
            String isbn = res.getString("isbn");
            LocalDate dateEmprunt = res.getDate("dateEmprunt").toLocalDate();
            LocalDate dateRetour = null;
            if (res.getDate("dateRetour") != null)
                dateRetour = res.getDate("dateRetour").toLocalDate();
            emprut = new Emprunt(id,
                    new Membre(idMembre, nom, prenom, adresse, email, telephone, abonnement),
                    new Livre(idLivre, titre, auteur, isbn),
                    dateEmprunt,
                    dateRetour);

        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
            throw new DaoException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emprut;
    }

    @Override
    public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException {
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = """
                    INSERT INTO emprunt(idMembre, idLivre, dateEmprunt, dateRetour)
                    VALUES (?, ?, ?, ?);
                    """;
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idMembre);
            stmt.setInt(2, idLivre);
            stmt.setDate(3, Date.valueOf(dateEmprunt));
            stmt.setDate(4, null);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
            throw new DaoException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Emprunt emprunt) throws DaoException {
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = """
                    UPDATE emprunt
                    SET idMembre = ?, idLivre = ?, dateEmprunt = ?, dateRetour = ?
                    WHERE id = ?;
                    """;
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, emprunt.getMembre().getId());
            stmt.setInt(2, emprunt.getLivre().getId());
            stmt.setDate(3, Date.valueOf(emprunt.getDateEmprunt()));
            stmt.setDate(4, Date.valueOf(emprunt.getDateRetour()));
            stmt.setInt(5, emprunt.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
            throw new DaoException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int count() throws DaoException {
        int result = 0;
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "SELECT COUNT(id) AS count FROM emprunt;";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet res = stmt.executeQuery();
            res.next();
            result = res.getInt(1);

        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
            throw new DaoException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

}
