package Modele;

public class Client extends Profil {

    public Client(String name, String email, int id, String mdp) {
        super(id, mdp, name, email);
    }

    @Override
    public String getRole() {
        return "Client";
    }
}

