package ubereats;

import java.util.ArrayList;

public class Restaurant extends Utilisateur {

    private boolean statutDispo;
    private String adresse;
    private ArrayList<String> menu;
    private double solde;


    public Restaurant() {
        super();
        this.statutDispo = false;
        this.adresse = "adresse inconnue";
        this.menu = new ArrayList<String>();
        this.solde = 0.0;
    }

    public Restaurant(int ID, String nom, String mail) {
        super(ID, nom, mail);
        this.statutDispo = true;
        this.adresse = "5 avenue des Gourmets";
        this.menu = new ArrayList<String>();
        this.solde = 0.0;
    }

    public void modifierStatut(boolean dispo) {
        this.statutDispo = dispo;
        System.out.println("[" + nom + "] modifierStatut() -> disponible = " + dispo);
    }

    public void menu(String plat) {
        menu.add(plat);
        System.out.println("[" + nom + "] menu() -> " + menu);
    }

    public void prendreCommande(Commande c) {
        System.out.println("   --> nouvelleCommande() recue par " + nom);
        accepterCommande(c);
    }

    public void accepterCommande(Commande c) {
        if (statutDispo) {
            System.out.println("[" + nom + "] accepterCommande() -> commande acceptee");
            c.changerStatut("en preparation");
        } else {
            System.out.println("[" + nom + "] accepterCommande() -> commande REFUSEE (restaurant ferme)");
            c.changerStatut("refusee");
        }
    }

    public void mettreAJourPreparation(Commande c) {
        System.out.println("[" + nom + "] mise a jour du statut de preparation");
        c.changerStatut("commande prete");
    }

    public void consulterAvis(Commande c) {
        System.out.println("[" + nom + "] consulterAvis() -> \"" + c.getAvis() + "\"");
    }
}
