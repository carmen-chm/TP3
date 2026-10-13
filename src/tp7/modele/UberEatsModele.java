package tp7.modele;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * TP7 (MVC) : point d'entree unique du MODELE (facade).
 * Toute la logique metier qui etait dans Main au TP6 est regroupee ici :
 * chargement des fichiers, connexion, commande, livraison, sauvegarde.
 * Cette classe n'importe RIEN de javax.swing ni java.awt :
 * on pourrait la reutiliser telle quelle avec une interface console ou web.
 */
public class UberEatsModele {

    // Donnees chargees depuis les fichiers (TP5/TP6)
    private ArrayList<Restaurant> restaurants;
    private ArrayList<Client> clients;
    private ArrayList<Livreur> livreurs;
    private ArrayList<RecapCommande> historique;

    // Etat de la session
    private Client clientConnecte;
    private Restaurant restaurantChoisi;
    private Panier panier;

    /** Lecture des fichiers texte + deserialisation de l'historique. */
    public void charger() throws FileNotFoundException {
        restaurants = Fichiers.lireRestaurants();
        clients = Fichiers.lireClients();
        livreurs = Fichiers.lireLivreurs();

        try {
            historique = Fichiers.deserialiserHistorique();
        } catch (IOException | ClassNotFoundException e) {
            historique = new ArrayList<RecapCommande>();   // premier lancement : pas encore de commandes_tp7.ser
        }
        // Les nouvelles commandes reprennent la numerotation apres la derniere enregistree
        if (!historique.isEmpty()) {
            Commande.initialiserCompteur(historique.get(historique.size() - 1).getNumero());
        }
    }

    // ===================== Connexion (TP5 : exception personnalisee) =====================

    /** Cherche le compte ; leve une ConnexionException si email/mot de passe incorrects. */
    public Client connecter(String email, String motDePasse) throws ConnexionException {
        email = email.trim();
        if (!email.contains("@")) {
            throw new ConnexionException("email invalide \"" + email + "\"");
        }
        for (Client c : clients) {
            if (c.getMail().equalsIgnoreCase(email)) {
                if (!c.motDePasseCorrect(motDePasse)) {
                    throw new ConnexionException("mot de passe incorrect");
                }
                c.connection();
                clientConnecte = c;
                return c;
            }
        }
        throw new ConnexionException("aucun compte pour " + email);
    }

    public void deconnecter() {
        clientConnecte = null;
        restaurantChoisi = null;
        panier = null;
    }

    // ===================== Choix du restaurant et panier =====================

    /** Choisit le restaurant et cree un panier vide pour ce restaurant. */
    public void choisirRestaurant(int index) {
        restaurantChoisi = restaurants.get(index);
        panier = new Panier();
    }

    /** Ajoute au panier le plat numero index du menu du restaurant choisi. */
    public void ajouterAuPanier(int index) {
        panier.ajouterPlat(restaurantChoisi.getMenu().get(index));
    }

    // ===================== Commande (TP3 a TP5) =====================

    /**
     * Passe la commande du panier : Client.commander() peut lever les exceptions du TP5.
     * Si tout va bien : preparation, livraison, sauvegarde (fichier texte + serialisation).
     */
    public RecapCommande commander() throws RestaurantFermeException, SoldeInsuffisantException, IOException {
        Commande commande = clientConnecte.commander(restaurantChoisi, panier.getPlats(), "CB");
        restaurantChoisi.mettreAJourPreparation(commande);
        livrer(commande);

        RecapCommande recap = new RecapCommande(commande);
        historique.add(recap);
        sauvegarder();
        panier.vider();
        return recap;
    }

    /** Les livreurs de livreurs.txt prennent les courses chacun leur tour. */
    private void livrer(Commande commande) {
        if (livreurs.isEmpty()) {
            return;
        }
        Livreur livreur = livreurs.get(commande.getNumero() % livreurs.size());
        livreur.reserverCourse(commande);
        livreur.validerLivraison(commande);
    }

    /** Ecrit le nouveau solde dans clients.txt et serialise l'historique dans commandes_tp7.ser. */
    private void sauvegarder() throws IOException {
        Fichiers.ecrireClients(clients);
        Fichiers.serialiserHistorique(historique);
    }

    /** Relit commandes_tp7.ser (deserialisation) et garde les commandes du client connecte. */
    public ArrayList<RecapCommande> historiqueClientConnecte() throws IOException, ClassNotFoundException {
        ArrayList<RecapCommande> resultat = new ArrayList<RecapCommande>();
        for (RecapCommande r : Fichiers.deserialiserHistorique()) {
            if (clientConnecte.getMail().equals(r.getMailClient())) {
                resultat.add(r);
            }
        }
        return resultat;
    }

    // ===================== Accesseurs (lecture seule pour le Controleur) =====================

    public ArrayList<Restaurant> getRestaurants() { return restaurants; }
    public Client getClientConnecte() { return clientConnecte; }
    public Restaurant getRestaurantChoisi() { return restaurantChoisi; }
    public Panier getPanier() { return panier; }
}
