package Modele;

public class Commander {
    private int commandeId;
    private int note;
    private boolean paye;

    // Constructeur
    public Commander(int commandeId, int note, boolean paye) {
        this.commandeId = commandeId;
        this.note = note;
        this.paye = false;
    }

    // Getters
    public int getCommandeId() {
        return commandeId;
    }

    public int getNote() {
        return note;
    }

    public boolean getPaye() {
        return paye;
    }

    // Setter
    public void setPaye() {
        this.paye = true;
    }

}
