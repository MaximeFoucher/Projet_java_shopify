package Dao;

import Modele.Commander;
import java.util.List;

public interface CommanderDAO {
    boolean ajouterCommande(Commander commande, Profil profil);
    List<Commander> getCommandesClient(Profil profil);
    int getIdClient(Commander commande);
    List<Article> getArticlesCommande(Commander commande);
}
