package Dao;

import Modele.Article;
import java.util.ArrayList;

public interface ArticleDao {
    public ArrayList<Article> getAll();
    public Article chercher(int id);
    public void ajouter(Article article);
    public Article modifier(Article article);
    public void supprimer(Article article);
}
