package tp7.controleur;

import tp7.modele.Client;
import tp7.modele.Plat;
import tp7.modele.RecapCommande;
import tp7.modele.Restaurant;
import tp7.modele.RestaurantFermeException;
import tp7.modele.SoldeInsuffisantException;
import tp7.modele.UberEatsModele;
import tp7.vue.FenetreMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * CONTROLEUR de la fenetre Menu et panier.
 * - "Ajouter au panier" : le Controleur demande au Modele d'ajouter le plat.
 *   Il NE met PAS a jour l'affichage : c'est le Panier (Modele) qui notifie la Vue.
 * - "Commander" : le Controleur appelle le Modele et traduit les exceptions
 *   du TP5 en messages d'erreur dans la Vue.
 */
public class ControleurMenu {

    private final UberEatsModele modele;
    private final ControleurApplication application;
    private final FenetreMenu vue;

    public ControleurMenu(UberEatsModele modele, ControleurApplication application) {
        this.modele = modele;
        this.application = application;

        Restaurant restaurant = modele.getRestaurantChoisi();
        Client client = modele.getClientConnecte();
        List<String> plats = new ArrayList<String>();
        for (Plat p : restaurant.getMenu()) {
            plats.add(p.toString());
        }
        this.vue = new FenetreMenu(restaurant.getNom(),
                "Connecté : " + client.getNom() + "   |   Solde : " + client.getSolde() + " EUR",
                plats);

        // Pattern Observer : la Vue s'abonne au Panier du Modele
        modele.getPanier().ajouterObservateur(vue);

        vue.ajouterEcouteurAjouter(e -> ajouterAuPanier());
        vue.ajouterEcouteurCommander(e -> commander());
        vue.ajouterEcouteurRetour(e -> retour());
        vue.setVisible(true);
    }

    private void ajouterAuPanier() {
        int i = vue.getIndexPlatSelectionne();
        if (i == -1) {
            vue.afficherMessage("Sélectionnez un plat.");
            return;
        }
        modele.ajouterAuPanier(i);   // => Panier.notifierObservateurs() => vue.panierModifie()
    }

    private void commander() {
        if (modele.getPanier().estVide()) {
            vue.afficherMessage("Le panier est vide.");
            return;
        }
        try {
            RecapCommande recap = modele.commander();
            vue.dispose();
            application.afficherConfirmation(recap);
        } catch (RestaurantFermeException ex) {
            vue.afficherErreur("Restaurant fermé", ex.getMessage());
        } catch (SoldeInsuffisantException ex) {
            vue.afficherErreur("Paiement refusé", ex.getMessage());
        } catch (IOException ex) {
            vue.afficherErreur("Erreur fichier", "Erreur d'écriture : " + ex.getMessage());
        }
    }

    private void retour() {
        vue.dispose();
        application.afficherRestaurants();
    }
}
