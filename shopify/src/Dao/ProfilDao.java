package Dao;

import Modele.*;
import java.util.List;

public interface ProfilDao {
    void ajouter(Profil Profil);
    Profil RechercheEmail(String email);
    List<Profil> ToutLister();
}
