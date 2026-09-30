package tp5;

public class Client extends Utilisateur {

    private String adresse;
    private HistoriqueCommandes historique;   // 1 client -> 0..* commandes
    private double solde;


    public Client() {
        super();
        this.adresse = "adresse inconnue";
        this.historique = new HistoriqueCommandes();
        this.solde = 0.0;
    }

    public Client(int ID, String nom, String mail) {
        super(ID, nom, mail);
        this.adresse = "12 rue de Paris";
        this.historique = new HistoriqueCommandes();
        this.solde = 100.0;
    }


    // TP5 : commander() ne traite pas les erreurs, elle les PROPAGE (throws) au Main
    public Commande commander(Restaurant restaurant, String plat, double prix, String modePaiement)
            throws Main.RestaurantFermeException, SoldeInsuffisantException {
        System.out.println("[" + nom + "] passerCommande()");
        Commande c = new Commande(this, restaurant);
        c.ajouterPlat(plat, prix);
        c.calculerMontant();

        // TP5 : on verifie d'abord que le restaurant accepte (s'il est ferme, rien n'est paye)
        restaurant.prendreCommande(c);   // peut lever Main.RestaurantFermeException
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
        return historique.nbCommandes();        // <-- changement ici
    }
}