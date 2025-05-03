package Modele;

public class Admin extends Profil {
    public Admin(String name, String email, int id, String mdp) {
        super(id, mdp, name, email);
    }

    @Override
    public String getRole() {
        return "Admin";
    }
}
