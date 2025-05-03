package Vue;

import Modele.Admin;
import java.util.ArrayList;

public class VueAdmin {
    public void afficherAdmin(Admin admin) {
        System.out.println("\nId admin:" + admin.getId() + "\nNom admin:" + admin.getName()
                + "\nEmail admin:" + admin.getEmail() + "\nMdp admin:" + admin.getMdp());
    }

    public void afficherListeadmin(ArrayList<Admin> admins) {
        for (Admin admin : admins) {
            afficherAdmin(admin);
        }
    }
}
