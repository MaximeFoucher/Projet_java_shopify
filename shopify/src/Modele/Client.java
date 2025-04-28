package Modele;

import java.util.List;

public class Client {
    private int id;
    private String mdp;
    private String name;

    public Client(int id, String mdp, String name) {
        this.id = id;
        this.mdp = mdp;
        this.name = name;
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


}

