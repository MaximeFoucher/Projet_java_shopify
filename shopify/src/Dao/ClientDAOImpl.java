package Dao;

import Modele.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAOImpl implements ProfilDao {
    private Connection connexion;

    public ClientDAOImpl(Connection connexion) {
        this.connexion = connexion;
    }

    @Override
    public void ajouter(Profil Profil) {
        if (Profil instanceof Client) {
            try {
                String sql = "INSERT INTO profil (Nom, Email, Admin, Mdp) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = connexion.prepareStatement(sql);
                stmt.setString(1, Profil.getName());
                stmt.setString(2, Profil.getEmail());
                stmt.setBoolean(3, false); // Client → false
                stmt.setString(4, Profil.getMdp());
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Profil RechercheEmail(String email) {
        try {
            String sql = "SELECT * FROM profil WHERE Email = ? AND Admin = false";
            PreparedStatement stmt = connexion.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Client(rs.getString("Nom"), rs.getString("Email"), rs.getInt("Id"), rs.getString("Mdp"));
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
            String sql = "SELECT * FROM profil WHERE Admin = false";
            Statement stmt = connexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                clients.add(new Client(rs.getString("Nom"), rs.getString("Email"), rs.getInt("Id"), rs.getString("Mdp")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }
}

