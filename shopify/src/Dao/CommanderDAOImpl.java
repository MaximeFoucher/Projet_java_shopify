package Dao;

import Modele.Commander;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommanderDAOImpl implements CommanderDAO {
    private DaoFactory daoFactory;

    public CommanderDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public boolean ajouterCommande(Commander commande) {
        try (Connection connexion = daoFactory.getConnection()){
            String sql = "INSERT INTO item (Id_article, Id_commande, Quantité, Prix) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connexion.prepareStatement(sql);
            stmt.setInt(1, commande.getProduitId());
            stmt.setInt(2, commande.getClientId());
            stmt.setInt(3, commande.getQuantite());
            stmt.setInt(4, 0); // Prix à calculer ailleurs si besoin

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout d'une commande : " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Commander> getCommandesClient(int clientId) {
        List<Commander> commandes = new ArrayList<>();
        try (Connection connexion = daoFactory.getConnection()){
            String sql = "SELECT Id_commande, Id_article, Quantité FROM item WHERE Id_commande = ?";
            PreparedStatement stmt = connexion.prepareStatement(sql);
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int produitId = rs.getInt("Id_article");
                int quantite = rs.getInt("Quantité");
                commandes.add(new Commander(clientId, produitId, quantite));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des commandes : " + e.getMessage());
        }
        return commandes;
    }
}
