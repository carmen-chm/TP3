package ubereats;

import java.util.ArrayList;

/**
 * Classe Restaurant : herite d'Utilisateur.
 * Correspond a l'acteur "Restaurateur" du diagramme de cas d'utilisation.
 *
 * @author Equipe Uber Eats - TP3 POO Java
 */
public class Restaurant extends Utilisateur {

    private boolean statutDispo;
    private String adresse;
    private ArrayList<String> menu;
    private double solde;

    /** Constructeur par defaut. */
    public Restaurant() {
        super();
        this.statutDispo = false;
        this.adresse = "adresse inconnue";
        this.menu = new ArrayList<String>();
        this.solde = 0.0;
    }

    /** Constructeur surcharge. */
    public Restaurant(int ID, String nom, String mail) {
        super(ID, nom, mail);
        this.statutDispo = true;
        this.adresse = "5 avenue des Gourmets";
        this.menu = new ArrayList<String>();
        this.solde = 0.0;
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

    /** Cas d'utilisation "Consulter les avis". */
    public void consulterAvis(Commande c) {
        System.out.println("[" + nom + "] consulterAvis() -> \"" + c.getAvis() + "\"");
    }
}
