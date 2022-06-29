package com.ensta.librarymanager.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.persistence.ConnectionManager;
import com.ensta.librarymanager.dao.ILivreDao;

public class LivreDao implements ILivreDao {

    private static LivreDao instance;

    private LivreDao() {
    }

    public static LivreDao getInstance() {
        if (instance == null) {
            instance = new LivreDao();
        }
        return instance;
    }

    public List<Livre> getList() throws DaoException {
        List<Livre> livreList = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "SELECT id, titre, auteur, isbn FROM livre;";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                int id = res.getInt("id");
                String titre = res.getString("titre");
                String auteur = res.getString("auteur");
                String isbn = res.getString("isbn");
                livreList.add(new Livre(id, titre, auteur, isbn));
            }

        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return livreList;
    }

    public Livre getById(int id) throws DaoException {
        Livre livre = null;
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "SELECT id, titre, auteur, isbn FROM livre WHERE id = ?;";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet res = stmt.executeQuery();
            res.next();
            String titre = res.getString("titre");
            String auteur = res.getString("auteur");
            String isbn = res.getString("isbn");
            livre = new Livre(id, titre, auteur, isbn);

        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return livre;
    }

    public int create(String titre, String auteur, String isbn) throws DaoException {
        int id = -1;
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "INSERT INTO livre(titre, auteur, isbn) VALUES (?, ?, ?);";
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, titre);
            stmt.setString(2, auteur);
            stmt.setString(3, isbn);
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

    public void update(Livre livre) throws DaoException {
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "UPDATE livre SET titre = ?, auteur = ?, isbn = ? WHERE id = ?;";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, livre.getTitre());
            stmt.setString(2, livre.getAuteur());
            stmt.setString(3, livre.getIsbn());
            stmt.setInt(4, livre.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) throws DaoException {
        try (Connection connection = ConnectionManager.getConnection()) {
            String sql = "DELETE FROM livre WHERE id = ?;";
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
            String sql = "SELECT COUNT(id) AS count FROM livre;";
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
