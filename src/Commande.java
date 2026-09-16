package ubereats;

import java.util.ArrayList;

/**
 * Classe Commande : objet metier central de l'application.
 * Reliee au Client et au Restaurant par une association, et au Livreur
 * par une agregation (0..1 livreur pour 0..* commandes).
 *
 * @author Equipe Uber Eats - TP3 POO Java
 */
public class Commande {

    private String date;
    private String statut;
    private double montant;
    private ArrayList<String> contenu;   // cardinalite > 1 => collection d'objets

    // Association avec Client et Restaurant, agregation avec Livreur
    private Client client;
    private Restaurant restaurant;
    private Livreur livreur;
    private String avis;

    /** Constructeur par defaut. */
    public Commande() {
        this.date = "01/01/2026";
        this.statut = "creee";
        this.montant = 0.0;
        this.contenu = new ArrayList<String>();
    }

    /** Constructeur surcharge : une commande lie un client a un restaurant. */
    public Commande(Client client, Restaurant restaurant) {
        this();                       // appel du constructeur par defaut
        this.client = client;
        this.restaurant = restaurant;
        System.out.println("   >> new Commande(" + client.getNom()
                + ", " + restaurant.getNom() + ") : commande creee");
    }

    /** Ajoute un plat au contenu de la commande. */
    public void ajouterPlat(String plat, double prix) {
        contenu.add(plat + " (" + prix + " EUR)");
        montant = montant + prix;
        System.out.println("   ajouterPlat(\"" + plat + "\") -> contenu = " + contenu.size() + " plat(s)");
    }

    /** Calcule et retourne le montant total de la commande. */
    public double calculerMontant() {
        System.out.println("   calculerMontant() -> " + montant + " EUR");
        return montant;
    }

    /** Change le statut de la commande et notifie le client (message asynchrone). */
    public void changerStatut(String nouveauStatut) {
        this.statut = nouveauStatut;
        System.out.println("   mettreAJour(\"" + nouveauStatut + "\")");
        if (client != null) {
            client.notifier(nouveauStatut);
        }
    }

    /** Affecte un livreur a la commande (agregation Livreur 0..1 - Commande 0..*). */
    public void affecterLivreur(Livreur livreur) {
        this.livreur = livreur;
    }

    /** Enregistre l'avis laisse par le client. */
    public void enregistrerAvis(String avis) {
        this.avis = avis;
        System.out.println("   enregistrerAvis() -> avis enregistre sur la commande");
    }

    public double getMontant() {
        return montant;
    }

    public String getAvis() {
        return avis;
    }

    public String getStatut() {
        return statut;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Livreur getLivreur() {
        return livreur;
    }

    /** Affiche le recapitulatif de la commande. */
    public void afficher() {
        System.out.println("--- Commande du " + date + " ---");
        System.out.println("  Client     : " + (client != null ? client.getNom() : "?"));
        System.out.println("  Restaurant : " + (restaurant != null ? restaurant.getNom() : "?"));
        System.out.println("  Livreur    : " + (livreur != null ? livreur.getNom() : "non affecte"));
        System.out.println("  Contenu    : " + contenu);
        System.out.println("  Montant    : " + montant + " EUR");
        System.out.println("  Statut     : " + statut);
    }
}
