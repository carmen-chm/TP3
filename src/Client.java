package ubereats;

import java.util.ArrayList;

/**
 * Classe Client : herite d'Utilisateur (relation de generalisation du
 * diagramme de classes, mot-cle extends).
 *
 * @author Equipe Uber Eats - TP3 POO Java
 */
public class Client extends Utilisateur {

    private String adresse;
    private ArrayList<Commande> historique;   // 1 client -> 0..* commandes
    private double solde;

    /** Constructeur par defaut. */
    public Client() {
        super();                              // appel du constructeur de la superclasse
        this.adresse = "adresse inconnue";
        this.historique = new ArrayList<Commande>();
        this.solde = 0.0;
    }

    /** Constructeur surcharge. */
    public Client(int ID, String nom, String mail) {
        super(ID, nom, mail);                 // super(...) : 1ere instruction obligatoire
        this.adresse = "12 rue de Paris";
        this.historique = new ArrayList<Commande>();
        this.solde = 100.0;
    }

    /**
     * Cas d'utilisation "Passer la commande".
     * Cree la commande, y ajoute les plats puis declenche obligatoirement
     * le paiement : c'est la traduction en Java de la relation <<includes>>.
     */
    public Commande commander(Restaurant restaurant, String plat, double prix, String modePaiement) {
        System.out.println("[" + nom + "] passerCommande()");
        Commande c = new Commande(this, restaurant);
        c.ajouterPlat(plat, prix);
        c.calculerMontant();
        historique.add(c);

        // <<includes>> : le paiement est OBLIGATOIRE, il est donc toujours appele
        payer(c, modePaiement);

        restaurant.prendreCommande(c);
        System.out.println("   <-- confirmationCommande()");
        return c;
    }

    /**
     * Cas d'utilisation "Payer la commande".
     * Le fragment alt du diagramme de sequences (CB / Apple Pay / especes)
     * est implemente par une structure conditionnelle if / else if / else.
     */
    public void payer(Commande c, String modePaiement) {
        double montant = c.getMontant();
        System.out.println("[" + nom + "] payer(" + montant + " EUR)");

        if (modePaiement.equals("CB")) {
            solde = solde - montant;
            System.out.println("   [alt : CB] paiement par carte bancaire accepte");
        } else if (modePaiement.equals("ApplePay")) {
            solde = solde - montant;
            System.out.println("   [alt : ApplePay] paiement Apple Pay accepte");
        } else {
            System.out.println("   [alt : especes] paiement en especes a la livraison");
        }
        System.out.println("   solde restant du client : " + solde + " EUR");
    }

    /** Cas d'utilisation "Suivre une commande en temps reel". */
    public void suivreCommande(Commande c) {
        System.out.println("[" + nom + "] suivreCommande()");
        Livreur l = c.getLivreur();
        if (l != null) {
            System.out.println("   position du livreur : " + l.getPosition());
        } else {
            System.out.println("   aucun livreur affecte pour l'instant");
        }
    }

    /** Reception d'une notification (message asynchrone du diagramme de sequences). */
    public void notifier(String message) {
        System.out.println("   --> notifier(\"" + message + "\") recu par " + nom);
    }

    /** Cas d'utilisation "Laisser un avis au livreur". */
    public void laisserAvis(Commande c, String avis) {
        System.out.println("[" + nom + "] laisserAvis(\"" + avis + "\")");
        c.enregistrerAvis(avis);
    }

    /**
     * Cas d'utilisation "Laisser un pourboire".
     * Relation <<extends>> : action OPTIONNELLE, traduite par un if
     * (equivalent du fragment opt du diagramme de sequences).
     */
    public void laisserPourboire(Commande c, double montant) {
        if (montant > 0) {                     // opt [le client souhaite laisser un pourboire]
            solde = solde - montant;
            Livreur l = c.getLivreur();
            if (l != null) {
                l.modifierSolde(montant);
            }
            System.out.println("[" + nom + "] laisserPourboire(" + montant + " EUR) -> envoye au livreur");
        }
    }

    public int nbCommandes() {
        return historique.size();
    }
}
