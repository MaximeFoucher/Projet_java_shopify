package Modele;

public class Commander {
    private int clientId;
    private int produitId;
    private int quantite;

    // Constructeur
    public Commander(int clientId, int produitId, int quantite) {
        this.clientId = clientId;
        this.produitId = produitId;
        this.quantite = quantite;
    }

    // Getters
    public int getClientId() {
        return clientId;
    }

    public int getProduitId() {
        return produitId;
    }

    public int getQuantite() {
        return quantite;
    }

    // Setter
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}
