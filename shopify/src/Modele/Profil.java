package Modele;

public class Profil {
    private int id;
    private String mdp;
    private String name;
    private boolean admin;

    public Profil(int id, String mdp, String name, boolean admin) {
        this.id = id;
        this.mdp = mdp;
        this.name = name;
        this.admin = admin;
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

    public boolean isAdmin() {
        return admin;
    }
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }



}

