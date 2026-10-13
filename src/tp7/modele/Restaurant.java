package tp7.modele;

import java.util.ArrayList;

public class Restaurant extends Utilisateur implements Evaluable {

    //Atributs
    private boolean statutDispo;
    private ArrayList<Plat> menu;      // TP6 : des Plat (nom + prix) au lieu de simples String
    private double solde;
    private ArrayList<String> avis;

    // TP6 : valeurs lues dans restaurants.txt (plus d'adresse ecrite en dur)
    public Restaurant(int ID, String nom, String mail, boolean ouvert) {
        super(ID, nom, mail);
        this.statutDispo = ouvert;
        this.menu = new ArrayList<Plat>();
        this.solde = 0.0;
        this.avis = new ArrayList<String>();
    }


    public void modifierStatut(boolean dispo) {
        this.statutDispo = dispo;
        System.out.println("[" + nom + "] modifierStatut() -> disponible = " + dispo);
    }


    public void ajouterPlat(Plat plat) {
        menu.add(plat);
    }

    public ArrayList<Plat> getMenu() {
        return menu;
    }

    public boolean estOuvert() {
        return statutDispo;
    }


    public void prendreCommande(Commande c) throws RestaurantFermeException {
        System.out.println("   --> nouvelleCommande() recue par " + nom);
        accepterCommande(c);
    }

    public void accepterCommande(Commande c) throws RestaurantFermeException {
        if (statutDispo) {
            System.out.println("[" + nom + "] accepterCommande() -> commande acceptee");
            c.changerStatut("en preparation");
        } else {
            c.changerStatut("refusee");
            throw new RestaurantFermeException(nom);
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
