package ubereats;

import java.util.ArrayList;

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


    public Commande() {
        this.date = "01/01/2026";
        this.statut = "creee";
        this.montant = 0.0;
        this.contenu = new ArrayList<String>();
    }

    public Commande(Client client, Restaurant restaurant) {
        this();                       // appel du constructeur par defaut
        this.client = client;
        this.restaurant = restaurant;
        System.out.println("   >> new Commande(" + client.getNom()
                + ", " + restaurant.getNom() + ") : commande creee");
    }

    public void ajouterPlat(String plat, double prix) {
        contenu.add(plat + " (" + prix + " EUR)");
        montant = montant + prix;
        System.out.println("   ajouterPlat(\"" + plat + "\") -> contenu = " + contenu.size() + " plat(s)");
    }

    public double calculerMontant() {
        System.out.println("   calculerMontant() -> " + montant + " EUR");
        return montant;
    }

    public void changerStatut(String nouveauStatut) {
        this.statut = nouveauStatut;
        System.out.println("   mettreAJour(\"" + nouveauStatut + "\")");
        if (client != null) {
            client.notifier(nouveauStatut);
        }
    }

    public void affecterLivreur(Livreur livreur) {
        this.livreur = livreur;
    }

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
