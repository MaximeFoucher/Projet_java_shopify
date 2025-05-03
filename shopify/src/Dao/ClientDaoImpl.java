package Dao;

import Modele.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class ClientDAOImpl implements ProfilDao {
    private Connection connexion;

    public ClientDAOImpl(Connection connexion) {
        this.connexion = connexion;
    }

    @Override
    public void ajouter(Profil Profil) {
        if (Profil instanceof Client) {
            try {
                String sql = "INSERT INTO Profils (nom, email, is_admin) VALUES (?, ?, ?)";
                PreparedStatement stmt = connexion.prepareStatement(sql);
                stmt.setString(1, Profil.getName());
                stmt.setString(2, Profil.getEmail());
                stmt.setBoolean(3, false); // Client → false
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Profil RechercheEmail(String email) {
        try {
            String sql = "SELECT * FROM Profils WHERE email = ? AND is_admin = false";
            PreparedStatement stmt = connexion.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Client(rs.getString("name"), rs.getString("email"), rs.getInt("id"), rs.getString("mdp"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Profil> ToutLister() {
        List<Profil> clients = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Profils WHERE is_admin = false";
            Statement stmt = connexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                clients.add(new Client(rs.getString("name"), rs.getString("email"), rs.getInt("id"), rs.getString("mdp")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }
}

