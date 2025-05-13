package Dao;

import Modele.*;
import java.util.List;

public interface AdminDAO {
    void ajouter(Profil Profil);
    Profil RechercheEmail(String email);
    List<Admin> ToutLister();
    void switchStatutAdmin(Admin admin);


}