package Dao;

import Modele.*;
import java.util.List;

public interface ClientDAO {
    void AjouterClient(Profil Profil);
    Client FindClientFromId(int id);
    Profil RechercheEmail(String email);
    List<Client> ToutLister();
    void MAJclient(Client client);
    void switchStatutAdmin(Client client);
    void changerNomClient(Client client, String nom);
    void changerEmailClient(Client client, String email);
    void changerMDPClient(Client client, String mdp);
    void changerAdmin(Client client);
    void SupprimerClient(Client client);
}