package Dao;

import Modele.*;
import Dao.*;

import java.io.Serial;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAOImpl implements ClientDAO {
    private Connection connexion;

    public ClientDAOImpl(Connection connexion) {
        this.connexion = connexion;
    }

    @Override
    public void AjouterClient(Profil Profil) {
            try {

                DaoFactory daoFactory = DaoFactory.getInstance("shopify", "root", "");

                String sql = "INSERT INTO profil (Nom, Email, Admin, Mdp) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = connexion.prepareStatement(sql);
                stmt.setString(1, Profil.getName());
                stmt.setString(2, Profil.getEmail());
                stmt.setBoolean(3, false); // Client → false
                stmt.setString(4, Profil.getMdp());
                stmt.executeUpdate();

                int id = 0;
                /// recupere l'id du profil qui vient d'etre creer car id est en AI
                String sql2 = "SELECT Id FROM profil WHERE Nom = ? AND Email = ? AND Mdp = ?";
                PreparedStatement stmt2 = connexion.prepareStatement(sql2);
                stmt2.setString(1, Profil.getName());
                stmt2.setString(2, Profil.getEmail());
                stmt2.setString(3, Profil.getMdp());

                ResultSet rs = stmt2.executeQuery();
                if (rs.next()) {
                    id = rs.getInt("Id");
                }
                /// print l'id du client
                System.out.println(id);
                CommanderDAOImpl commanderDaoImpl = new CommanderDAOImpl(daoFactory);
                /// faire un new client pour faire fonctionner ajouterCommande()
                /// le nouveau client à part défaut dès son arrivé un panier (qui est vide)
                Client client = new Client(Profil.getName(), Profil.getEmail(), id, Profil.getMdp());

                commanderDaoImpl.ajouterCommande(client);


            } catch (SQLException e) {
                e.printStackTrace();
            }

    }

    @Override
    public Client FindClientFromId(int id) {
        Client client = null;
        try {
            String sql = "SELECT * FROM profil WHERE Id = ? AND Admin = false";
            PreparedStatement stmt = connexion.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nom = rs.getString("Nom");
                String email = rs.getString("Email");
                String mdp = rs.getString("Mdp");

                client = new Client(nom, email, id, mdp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
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

    @Override
    public void MAJclient(Client client) {
        try {

            //DaoFactory daoFactory = new DaoFactory("shopify", "root", "");

            String updateSql = "UPDATE profil SET Mdp = ?, Email = ?, Nom = ? WHERE Id = ?";
            PreparedStatement stmt = connexion.prepareStatement(updateSql);
            stmt.setString(1, client.getMdp());
            stmt.setString(2, client.getEmail());
            stmt.setString(3, client.getName());
            stmt.setInt(4, client.getId());
            stmt.executeUpdate();


        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la table client : " + e.getMessage());
        }
    }

    @Override
    public void switchStatutAdmin(Client client) {
        client.SwitchAdmin();
        MAJclient(client);
    }

    @Override
    public void changerNomClient(Client client, String nom){
        client.setName(nom);
        MAJclient(client);
    }

    @Override
    public void changerEmailClient(Client client, String email){
        client.setEmail(email);
        MAJclient(client);
    }

    @Override
    public void changerMDPClient(Client client, String mdp){
        client.setMdp(mdp);
        MAJclient(client);
    }

    @Override
    public void changerAdmin(Client client){
        client.SwitchAdmin();
        MAJclient(client);
    }

    @Override
    public void SupprimerClient(Client client) {
        try {

            DaoFactory daoFactory = new DaoFactory("shopify", "root", "");
            /// supprimer toutes les tables commandes et item qui sont liée
            /// supprimer dans la table historique
            /// supprimer le profil


            CommanderDAOImpl commandeDao = new CommanderDAOImpl(daoFactory);

            /// Récupérer toutes les commandes du client
            String sqlCmds = "SELECT * FROM commande WHERE Id_client = ?";
            PreparedStatement stmtCmds = connexion.prepareStatement(sqlCmds);
            stmtCmds.setInt(1, client.getId());
            ResultSet rs = stmtCmds.executeQuery();

            while (rs.next()) {
                int commandeId = rs.getInt("Id");
                int note = rs.getInt("Note");
                boolean paye = rs.getBoolean("Payé");
                Commander commande = new Commander(commandeId, note, paye);
                commandeDao.supprimerCommande(commande); /// Supprime la commande + items (la fonction le fait)
            }

            /// Supprimer l'historique
            String deleteHistorique = "DELETE FROM historique WHERE Id_client = ?";
            PreparedStatement stmtHist = connexion.prepareStatement(deleteHistorique);
            stmtHist.setInt(1, client.getId());
            stmtHist.executeUpdate();

            /// Supprimer le profil
            String deleteProfil = "DELETE FROM profil WHERE Id = ?";
            PreparedStatement stmtProfil = connexion.prepareStatement(deleteProfil);
            stmtProfil.setInt(1, client.getId());
            stmtProfil.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du client : " + e.getMessage());
        }
    }
}

