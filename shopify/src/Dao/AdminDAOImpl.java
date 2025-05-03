package Dao;

import Modele.Admin;
import Modele.Profil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAOImpl implements ProfilDao {

    private DaoFactory daoFactory;

    public AdminDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public boolean ajouter(Profil profil) {
        if (profil instanceof Admin) {
            try (Connection connexion = daoFactory.getConnection()) {
                String sql = "INSERT INTO Profils (nom, email, mdp, is_admin) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = connexion.prepareStatement(sql);
                stmt.setString(1, profil.getName());
                stmt.setString(2, profil.getEmail());
                stmt.setString(3, profil.getMdp());
                stmt.setBoolean(4, true); // admin → is_admin = true
                stmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'ajout d'un admin : " + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public Profil RechercheEmail(String email) {
        try (Connection connexion = daoFactory.getConnection()) {
            String sql = "SELECT * FROM Profils WHERE email = ? AND is_admin = true";
            PreparedStatement stmt = connexion.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Admin(
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getInt("id"),
                        rs.getString("mdp")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche d'un admin : " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Profil> ToutLister() {
        List<Profil> admins = new ArrayList<>();
        try (Connection connexion = daoFactory.getConnection()) {
            String sql = "SELECT * FROM Profils WHERE is_admin = true";
            Statement stmt = connexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                admins.add(new Admin(
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getInt("id"),
                        rs.getString("mdp")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors du listing des admins : " + e.getMessage());
        }
        return admins;
    }
}