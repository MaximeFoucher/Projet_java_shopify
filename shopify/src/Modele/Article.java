package Modele;

public class Article {
    private int articleId;
    private String articleMarque;
    private String articleNom;
    private double articlePrixUnite;
    private double articlePrixGroupe;
    private int articleValeurLot;
    private double articleStock;

    // constructor
    public Article(int articleId, String articleMarque, String articleNom, double articlePrixUnite, double articlePrixGroupe, int articleValeurLot, double articleStock) {
        this.articleId = articleId;
        this.articleMarque = articleMarque;
        this.articleNom = articleNom;
        this.articlePrixUnite = articlePrixUnite;
        this.articlePrixGroupe = articlePrixGroupe;
        this.articleValeurLot = articleValeurLot;
        this.articleStock = articleStock;
    }

    // getters
    public int getArticleId() {return articleId;}
    public String getArticleMarque() {return articleMarque;}
    public String getArticleNom() {return articleNom;}
    public double getArticlePrixUnite() {return articlePrixUnite;}
    public double getArticlePrixGroupe() {return articlePrixGroupe;}
    public int getArticleValeurLot() {return articleValeurLot;}
    public double getArticleStock() {return articleStock;}

    // setters
    public void setArticleId(int articleId) {this.articleId = articleId;}
    public void setArticleMarque(String articleMarque) {this.articleMarque = articleMarque;}
    public void setArticleNom(String articleNom) {this.articleNom = articleNom;}
    public void setArticlePrixUnite(double articlePrixUnite) {this.articlePrixUnite = articlePrixUnite;}
    public void setArticlePrixGroupe(double articlePrixGroupe) {this.articlePrixGroupe = articlePrixGroupe;}
    public void setArticleValeurLot(int articleValeurLot) {this.articleValeurLot = articleValeurLot;}
    public void setArticleStock(double articleStock) {this.articleStock = articleStock;}

}
