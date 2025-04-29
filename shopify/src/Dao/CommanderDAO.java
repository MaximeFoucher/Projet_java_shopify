package Dao;

import Modele.*;
import java.util.List;

public interface CommanderDAO {
    boolean ajouterCommande(Commander commande, clients profil);
    List<Commander> getCommandesClient(clients profil);
    int getIdClient(Commander commande);
    List<Article> getArticlesCommande(Commander commande);
    int getArticleQuantite(Commander commande, Article article);

    }
