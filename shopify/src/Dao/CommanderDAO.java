package Dao;

import Modele.Commander;
import java.util.List;

public interface CommanderDAO {
    boolean ajouterCommande(Commander commande);
    List<Commander> getCommandesClient(int clientId);
}
