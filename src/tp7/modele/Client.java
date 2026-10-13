package tp7.modele;

import java.util.ArrayList;

public class Client extends Utilisateur {

    private String motDePasse;
    private String adresse;
    private HistoriqueCommandes historique;   // 1 client -> 0..* commandes
    private double solde;


    // TP6 : toutes les valeurs viennent de clients.txt (plus d'adresse ni de solde ecrits en dur)
    public Client(int ID, String nom, String mail, String motDePasse, String adresse, double solde) {
        super(ID, nom, mail);
        this.motDePasse = motDePasse;
        this.adresse = adresse;
        this.historique = new HistoriqueCommandes();
        this.solde = solde;
    }


    // TP5 : commander() ne traite pas les erreurs, elle les PROPAGE (throws) a l'appelant
    // TP6 : la commande contient tous les plats du panier
    public Commande commander(Restaurant restaurant, ArrayList<Plat> panier, String modePaiement)
            throws RestaurantFermeException, SoldeInsuffisantException {
        System.out.println("[" + nom + "] passerCommande()");
        Commande c = new Commande(this, restaurant);
        for (Plat p : panier) {
            c.ajouterPlat(p);
        }
        c.calculerMontant();

        //on verifie d'abord que le restaurant accepte (s'il est ferme, rien n'est paye)
        restaurant.prendreCommande(c);   // peut lever RestaurantFermeException
        payer(c, modePaiement);          // peut lever SoldeInsuffisantException

        historique.ajouterCommande(c);   // ajoutee seulement si tout s'est bien passe
        System.out.println("   <-- confirmationCommande()");
        return c;
    }

    // TP5 : leve une SoldeInsuffisantException si le solde ne suffit pas (CB ou ApplePay)
    public void payer(Commande c, String modePaiement) throws SoldeInsuffisantException {
        double montant = c.getMontant();
        System.out.println("[" + nom + "] payer(" + montant + " EUR)");

        if ((modePaiement.equals("CB") || modePaiement.equals("ApplePay")) && montant > solde) {
            c.changerStatut("annulee");
            throw new SoldeInsuffisantException(solde, montant);
        }

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


    public void suivreCommande(Commande c) {
        System.out.println("[" + nom + "] suivreCommande()");
        Livreur l = c.getLivreur();
        if (l != null) {
            System.out.println("   position du livreur : " + l.getPosition());
        } else {
            System.out.println("   aucun livreur affecte pour l'instant");
        }
    }

    public void notifier(String message) {
        System.out.println("   --> notifier(\"" + message + "\") recu par " + nom);
    }

    public void laisserAvis(Commande c, String avis) {
        System.out.println("[" + nom + "] laisserAvis(\"" + avis + "\")");
        c.enregistrerAvis(avis);

        // on transmet aussi l'avis au restaurant et au livreur concernes
        Restaurant r = c.getRestaurant();
        if (r != null) {
            r.recevoirAvis(avis);
        }

        Livreur l = c.getLivreur();
        if (l != null) {
            l.recevoirAvis(avis);
        }
    }


    public void laisserPourboire(Commande c, double montant) {
        if (montant > 0) {
            solde = solde - montant;
            Livreur l = c.getLivreur();
            if (l != null) {
                l.modifierSolde(montant);
            }
            System.out.println("[" + nom + "] laisserPourboire(" + montant + " EUR) -> envoye au livreur");
        }
    }
    @Override
    public void afficherActivite() {
        System.out.println("[" + nom + "] Client - solde: " + solde
                + " EUR, commandes passees: " + historique.nbCommandes());
    }

    public int nbCommandes() {
        return historique.nbCommandes();
    }

    // TP6 : verification du mot de passe a la connexion
    public boolean motDePasseCorrect(String mdp) {
        return motDePasse.equals(mdp);
    }

    // TP6 : accesseurs utilises pour l'affichage et pour reecrire clients.txt
    public String getMotDePasse() {
        return motDePasse;
    }

    public String getAdresse() {
        return adresse;
    }

    public double getSolde() {
        return solde;
    }
}
