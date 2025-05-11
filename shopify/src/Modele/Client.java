package Modele;

public class Client extends Profil {

    public Client(String name, String email, int id, String mdp) {
        super(id, mdp, name, email);
    }

    @Override
    public String getRole() {
        return "Client";
    }
    public String getEmail() {return super.getEmail();}
    public String getMdp() {return super.getMdp();}
    public String getNom() {return super.getName();}
}

