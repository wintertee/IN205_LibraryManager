package com.ensta.librarymanager.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.dao.IMembreDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Abonnement;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class MembreDao implements IMembreDao {

    private static MembreDao instance;

    private MembreDao() {
    }

    public static MembreDao getInstance() {
        if (instance == null) {
            instance = new MembreDao();
        }
        return instance;
    }

    public List<Membre> getList() throws DaoException {
        List<Membre> membreList = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = """
                    SELECT id, nom, prenom, adresse, email, telephone, abonnement
                    FROM membre
                    ORDER BY nom, prenom;
                    """;
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                int id = res.getInt("id");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                String adresse = res.getString("adresse");
                String email = res.getString("email");
                String telephone = res.getString("telephone");
                Abonnement abonnement = Abonnement.valueOf(res.getString("abonnement"));
                membreList.add(new Membre(id, nom, prenom, adresse, email, telephone, abonnement));
            }

        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return membreList;
    }

    public Membre getById(int id) throws DaoException {
        Membre membre = null;
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = """
                    SELECT id, nom, prenom, adresse, email, telephone, abonnement
                    FROM membre
                    WHERE id = ?;
                    """;
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet res = stmt.executeQuery();
            res.next();
            String nom = res.getString("nom");
            String prenom = res.getString("prenom");
            String adresse = res.getString("adresse");
            String email = res.getString("email");
            String telephone = res.getString("telephone");
            Abonnement abonnement = Abonnement.valueOf(res.getString("abonnement"));
            membre = new Membre(id, nom, prenom, adresse, email, telephone, abonnement);

        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return membre;
    }

    public int create(String nom, String prenom, String adresse, String email, String telephone) throws DaoException {
        int id = -1;
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = """
                    INSERT INTO membre(nom, prenom, adresse, email, telephone, abonnement)
                    VALUES (?, ?, ?, ?, ?, ?);
                    """;
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            stmt.setString(3, adresse);
            stmt.setString(4, email);
            stmt.setString(5, telephone);
            stmt.setString(6, Abonnement.BASIC.toString());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating book failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public void update(Membre membre) throws DaoException {
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = """
                    UPDATE membre SET nom = ?, prenom = ?, adresse = ?, email = ?, telephone = ?, abonnement = ?
                    WHERE id = ?;
                    """;
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, membre.getNom());
            stmt.setString(2, membre.getPrenom());
            stmt.setString(3, membre.getAdresse());
            stmt.setString(4, membre.getEmail());
            stmt.setString(5, membre.getTelephone());
            stmt.setString(6, membre.getAbonnement().toString());
            stmt.setInt(7, membre.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) throws DaoException {
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "DELETE FROM membre WHERE id = ?;";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int count() throws DaoException {
        int result = 0;
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "SELECT COUNT(id) AS count FROM membre;";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet res = stmt.executeQuery();
            res.next();
            result = res.getInt(1);

        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
