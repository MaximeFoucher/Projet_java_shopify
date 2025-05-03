package Dao;

import Modele.Client;
import Modele.Profil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAOImpl implements ProfilDao {

    private DaoFactory daoFactory;

    public ClientDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public boolean ajouter(Profil profil) {
        if (profil instanceof Client) {
            try (Connection connexion = daoFactory.getConnection()) {
                String sql = "INSERT INTO Profils (nom, email, mdp, is_admin) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = connexion.prepareStatement(sql);
                stmt.setString(1, profil.getName());
                stmt.setString(2, profil.getEmail());
                stmt.setString(3, profil.getMdp());
                stmt.setBoolean(4, false); // client → is_admin = false
                stmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'ajout d'un client : " + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public Profil RechercheEmail(String email) {
        try (Connection connexion = daoFactory.getConnection()) {
            String sql = "SELECT * FROM Profils WHERE email = ? AND is_admin = false";
            PreparedStatement stmt = connexion.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Client(
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getInt("id"),
                        rs.getString("mdp")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche d'un client : " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Profil> ToutLister() {
        List<Profil> clients = new ArrayList<>();
        try (Connection connexion = daoFactory.getConnection()) {
            String sql = "SELECT * FROM Profils WHERE is_admin = false";
            Statement stmt = connexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                clients.add(new Client(
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getInt("id"),
                        rs.getString("mdp")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors du listing des clients : " + e.getMessage());
        }
        return clients;
    }
}