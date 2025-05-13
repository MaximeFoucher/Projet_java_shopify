package Modele;

public class Admin extends Profil {
    public Admin(String name, String email, int id, String mdp) {
        super(id, mdp, name, email);
    }

    @Override
    public String getRole() {
        return "Admin";
    }
    public String getEmail() {return super.getEmail();}
    public String getMdp() {return super.getMdp();}
    public String getNom() {return super.getName();}
}
