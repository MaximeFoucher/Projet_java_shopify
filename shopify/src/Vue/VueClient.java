package Vue;

import Modele.Client;
import java.util.ArrayList;

public class VueClient {
    public void afficherClient(Client client) {
        System.out.print("\nId client:" + client.getId() + "\nNom client:" + client.getName()
        + "\nEmail client:" + client.getEmail() + "\nMdp client:" + client.getMdp());
    }

    public void afficherListeClient(ArrayList<Client> clients) {
        for (Client client : clients) {
            afficherClient(client);
        }
    }
}
