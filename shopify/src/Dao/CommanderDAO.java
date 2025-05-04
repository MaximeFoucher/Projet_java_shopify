package Dao;

import Modele.*;
import java.util.List;

public interface CommanderDAO {
    void ajouterCommande(Commander commande, Client client);
    List<Commander> getCommandesClient(Client client);
    int getIdClient(Commander commande);
    List<Article> getArticlesCommande(Commander commande);
    Client getClientFromCommande(Commander commande);
    public int getQuantiteArticleFromCommande(Commander commande, Article article);
    public boolean VerifierExistancePanier(Commander commande, Client client);
    public void MAJTableCommande(Commander commande);
    public void reglerPanier(Commander commande, Client client);
    public void ajouterArticleDansCommande(Commander commande, Article article, Client client, int quantiteArticle);
    }
