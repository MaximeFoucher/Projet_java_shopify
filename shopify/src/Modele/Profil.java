package Modele;

public class Profil {
    private int id;
    private String mdp;
    private String name;
    private String email;

    public Profil(int id, String mdp, String name, String email) {
        this.id = id;
        this.mdp = mdp;
        this.name = name;
        this.email = email;
    }

    public String getRole() {
        return "Utilisateur";
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getMdp() {
        return mdp;
    }
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail () {
        return email;
    }
    public void setEmail (String email) {
        this.email = email;
    }

}
