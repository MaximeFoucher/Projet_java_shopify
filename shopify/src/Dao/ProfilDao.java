package Dao;

import Modele.*;
import java.util.List;

public interface ProfilDao {
    void ajouter(Profil Profil);
    Client FindClientFromId(int id);
    Profil RechercheEmail(String email);
    List<Profil> ToutLister();
}
