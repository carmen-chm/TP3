package ubereats;

import java.util.ArrayList;

public class Livreur extends Utilisateur implements Evaluable {

    //Attributs
    private HistoriqueCommandes courses;
    private boolean statutDispo;
    private String position;
    private double solde;
    private ArrayList<String> avis;


    //Constructeur par defaut
    public Livreur() {
        super();
        this.statutDispo = false;
        this.position = "position inconnue";
        this.solde = 0.0;
        this.courses = new HistoriqueCommandes();
        this.avis = new ArrayList<String>();
    }

    //Constructeur surcharge
    public Livreur(int ID, String nom, String mail) {
        super(ID, nom, mail);
        this.statutDispo = true;
        this.position = "Depart";
        this.solde = 0.0;
        this.courses = new HistoriqueCommandes();
        this.avis = new ArrayList<String>();
    }


    public void modifierStatut(boolean dispo) {
        this.statutDispo = dispo;
        System.out.println("[" + nom + "] disponibilite = " + dispo);
    }


    public boolean reserverCourse(Commande c) {
        System.out.println("   --> proposerCourse() recue par " + nom);
        if (statutDispo) {
            courses.ajouterCommande(c);    // <-- change : ajouterCommande() au lieu de add()
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

    public String getPosition() {
        return position;
    }

    public int nbCourses() {
        return courses.nbCommandes();
    }

    //Utilisation de l'interface
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

    //Utilisation de la méthode abstraite
    @Override
    public void afficherActivite() {
        System.out.println("[" + nom + "] Livreur - disponible: " + statutDispo
                + ", position: " + position + ", courses effectuees: " + courses.nbCommandes());
    }
}
