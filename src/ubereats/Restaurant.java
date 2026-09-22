package ubereats;

import java.util.ArrayList;

public class Restaurant extends Utilisateur implements Evaluable {

    //Atributs
    private boolean statutDispo;
    private String adresse;
    private ArrayList<String> menu;
    private double solde;
    private ArrayList<String> avis;

    //Constructeur par defaut
    public Restaurant() {
        super();
        this.statutDispo = false;
        this.adresse = "adresse inconnue";
        this.menu = new ArrayList<String>();
        this.solde = 0.0;
        this.avis = new ArrayList<String>();
    }

    //Constructeur surcharge
    public Restaurant(int ID, String nom, String mail) {
        super(ID, nom, mail);
        this.statutDispo = true;
        this.adresse = "5 avenue des Gourmets";
        this.menu = new ArrayList<String>();
        this.solde = 0.0;
        this.avis = new ArrayList<String>();
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

    //utilisation de l'interface
    @Override
    public void recevoirAvis(String avis) {
        this.avis.add(avis);
        System.out.println("[" + nom + "] recevoirAvis() -> avis enregistre");
    }

    @Override
    public void afficherAvis() {
        System.out.println("[" + nom + "] avis recus : " + avis);
    }

    @Override
    public double getNoteMoyenne() {
        return avis.size();
    }

    //utilisation de la méthode abstraite
    @Override
    public void afficherActivite() {
        System.out.println("[" + nom + "] Restaurant - disponible: " + statutDispo
                + ", plats au menu: " + menu.size());
    }
}
