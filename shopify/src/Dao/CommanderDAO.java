package Dao;

import Modele.*;
import java.util.List;

public interface CommanderDAO {
    boolean ajouterCommande(Commander commande, Client client);
    List<Commander> getCommandesClient(Client client);
    int getIdClient(Commander commande);
    List<Article> getArticlesCommande(Commander commande);
    Client getClientFromCommande(Commander commande);
    public int getQuantiteArticleFromCommande(Commander commande, Article article);
    public void ajouterArticleDansCommande(Commander commande, Article article);
    }
