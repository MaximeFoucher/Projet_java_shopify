package Vue;

import Modele.Article;
import java.util.ArrayList;

public class VueArticle {
    public void afficherArticle(Article article) {
        System.out.println("Id article :" + article.getArticleId() + "\nNom article :" + article.getArticleNom() +
                "\nMarque article :"+ article.getArticleMarque() + "\nPrix unité article :" + article.getArticlePrixUnite() +
                "\nPrix groupe article :" + article.getArticlePrixGroupe() + "\nValeur lot article :" + article.getArticleValeurLot() +
                "\nStock article :" + article.getArticleStock());
    }
}
