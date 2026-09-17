package ubereats;

import java.util.ArrayList;

/**
 * Classe Livreur : herite d'Utilisateur.
 * Relation d'AGREGATION avec Commande (0..1 livreur pour 0..* commandes) :
 * le livreur possede une collection de commandes, mais les commandes
 * continuent d'exister meme sans livreur affecte.
 *
 * @author Equipe Uber Eats - TP3 POO Java
 */
public class Livreur extends Utilisateur {

    private boolean statutDispo;
    private String position;
    private double solde;

    // Agregation : 0..* commandes prises en charge par ce livreur
    private ArrayList<Commande> courses;

    /** Constructeur par defaut. */
    public Livreur() {
        super();
        this.statutDispo = false;
        this.position = "position inconnue";
        this.solde = 0.0;
        this.courses = new ArrayList<Commande>();
    }

    /** Constructeur surcharge. */
    public Livreur(int ID, String nom, String mail) {
        super(ID, nom, mail);
        this.statutDispo = true;
        this.position = "Depart";
        this.solde = 0.0;
        this.courses = new ArrayList<Commande>();
    }

    /** Cas d'utilisation "Indiquer sa disponibilite". */
    public void modifierStatut(boolean dispo) {
        this.statutDispo = dispo;
        System.out.println("[" + nom + "] disponibilite = " + dispo);
    }

    /**
     * Cas d'utilisation "Accepter ou non la course".
     * Message proposerCourse() du diagramme de sequences.
     */
    public boolean reserverCourse(Commande c) {
        System.out.println("   --> proposerCourse() recue par " + nom);
        if (statutDispo) {
            courses.add(c);            // agregation : la commande rejoint les courses du livreur
            c.affecterLivreur(this);
            statutDispo = false;
            System.out.println("[" + nom + "] reserverCourse() -> course acceptee");
            System.out.println("   <-- confirmationCourse()");
            return true;
        }
        System.out.println("[" + nom + "] reserverCourse() -> course refusee (indisponible)");
        return false;
    }

    /** Cas d'utilisation "Afficher le chemin pris" : mise a jour de la position. */
    public void modifierPosition(String nouvellePosition) {
        this.position = nouvellePosition;
        System.out.println("[" + nom + "] modifierPosition() -> " + position);
    }

    /** Ajoute un montant au solde du livreur (utilise pour le pourboire). */
    public void modifierSolde(double montant) {
        this.solde = this.solde + montant;
        System.out.println("[" + nom + "] modifierSolde(+" + montant + ") -> solde = " + solde + " EUR");
    }

    /** Cas d'utilisation "Valider la livraison". */
    public void validerLivraison(Commande c) {
        System.out.println("[" + nom + "] validerLivraison()");
        c.changerStatut("commande livree");
        statutDispo = true;
    }

    /** Cas d'utilisation "Consulter les avis". */
    public void consulterAvis(Commande c) {
        System.out.println("[" + nom + "] consulterAvis() -> \"" + c.getAvis() + "\"");
    }

    public String getPosition() {
        return position;
    }

    public int nbCourses() {
        return courses.size();
    }
}
