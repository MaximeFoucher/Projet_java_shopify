package Dao;

import Modele.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAOImpl implements AdminDAO {
    private Connection connexion;
    
    public AdminDAOImpl(Connection connexion) {
        this.connexion = connexion;
    }

        @Override
        public void ajouter(Profil Profil) {
            if (Profil instanceof Admin) {
                try {
                    String sql = "INSERT INTO profil (Nom, Email, Admin, Mdp) VALUES (?, ?, ?, ?)";
                    PreparedStatement stmt = connexion.prepareStatement(sql);
                    stmt.setString(1, Profil.getName());
                    stmt.setString(2, Profil.getEmail());
                    stmt.setBoolean(3, true); // Admin → true
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
                String sql = "SELECT * FROM profil WHERE Email = ? AND Admin = true";
                PreparedStatement stmt = connexion.prepareStatement(sql);
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return new Admin(rs.getString("Nom"), rs.getString("Email"), rs.getInt("Id"), rs.getString("Mdp"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public List<Profil> ToutLister() {
            List<Profil> admins = new ArrayList<>();
            try {
                String sql = "SELECT * FROM profil WHERE Admin = true";
                Statement stmt = connexion.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    admins.add(new Admin(rs.getString("Nom"), rs.getString("Email"), rs.getInt("Id"), rs.getString("Mdp")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return admins;
        }
    }

