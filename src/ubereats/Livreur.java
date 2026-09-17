package ubereats;

import java.util.ArrayList;

public class Livreur extends Utilisateur {

    private boolean statutDispo;
    private String position;
    private double solde;

    // Agregation : 0..* commandes prises en charge par ce livreur
    private ArrayList<Commande> courses;


    public Livreur() {
        super();
        this.statutDispo = false;
        this.position = "position inconnue";
        this.solde = 0.0;
        this.courses = new ArrayList<Commande>();
    }

    public Livreur(int ID, String nom, String mail) {
        super(ID, nom, mail);
        this.statutDispo = true;
        this.position = "Depart";
        this.solde = 0.0;
        this.courses = new ArrayList<Commande>();
    }

    public void modifierStatut(boolean dispo) {
        this.statutDispo = dispo;
        System.out.println("[" + nom + "] disponibilite = " + dispo);
    }

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

    public void modifierPosition(String nouvellePosition) {
        this.position = nouvellePosition;
        System.out.println("[" + nom + "] modifierPosition() -> " + position);
    }

    public void modifierSolde(double montant) {
        this.solde = this.solde + montant;
        System.out.println("[" + nom + "] modifierSolde(+" + montant + ") -> solde = " + solde + " EUR");
    }

    public void validerLivraison(Commande c) {
        System.out.println("[" + nom + "] validerLivraison()");
        c.changerStatut("commande livree");
        statutDispo = true;
    }

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
