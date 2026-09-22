package ubereats;

import java.util.ArrayList;

public class Restaurant extends Utilisateur implements Evaluable {

    private boolean statutDispo;
    private String adresse;
    private ArrayList<String> menu;
    private double solde;
    private ArrayList<String> avis;   // <-- nouvel attribut

    public Restaurant() {
        super();
        this.statutDispo = false;
        this.adresse = "adresse inconnue";
        this.menu = new ArrayList<String>();
        this.solde = 0.0;
        this.avis = new ArrayList<String>();   // <-- initialisation
    }

    public Restaurant(int ID, String nom, String mail) {
        super(ID, nom, mail);
        this.statutDispo = true;
        this.adresse = "5 avenue des Gourmets";
        this.menu = new ArrayList<String>();
        this.solde = 0.0;
        this.avis = new ArrayList<String>();   // <-- initialisation
    }

    /** Cas d'utilisation "Indiquer sa disponibilite". */
    public void modifierStatut(boolean dispo) {
        this.statutDispo = dispo;
        System.out.println("[" + nom + "] modifierStatut() -> disponible = " + dispo);
    }

    /** Cas d'utilisation "Consulter le menu" : ajoute et affiche les plats. */
    public void menu(String plat) {
        menu.add(plat);
        System.out.println("[" + nom + "] menu() -> " + menu);
    }

    /** Reception d'une nouvelle commande (message nouvelleCommande du diagramme). */
    public void prendreCommande(Commande c) {
        System.out.println("   --> nouvelleCommande() recue par " + nom);
        accepterCommande(c);
    }

    /**
     * Cas d'utilisation "Accepter ou non la commande".
     * Le restaurant n'accepte que s'il est disponible (fragment alt).
     */
    public void accepterCommande(Commande c) {
        if (statutDispo) {
            System.out.println("[" + nom + "] accepterCommande() -> commande acceptee");
            c.changerStatut("en preparation");
        } else {
            System.out.println("[" + nom + "] accepterCommande() -> commande REFUSEE (restaurant ferme)");
            c.changerStatut("refusee");
        }
    }

    /** Cas d'utilisation "Mettre a jour le statut de preparation". */
    public void mettreAJourPreparation(Commande c) {
        System.out.println("[" + nom + "] mise a jour du statut de preparation");
        c.changerStatut("commande prete");
    }

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
        return avis.size();   // simplifie : nb d'avis (ou calcul reel si tes avis contiennent une note)
    }

    @Override
    public void afficherActivite() {
        System.out.println("[" + nom + "] Restaurant - disponible: " + statutDispo
                + ", plats au menu: " + menu.size());
    }
}
