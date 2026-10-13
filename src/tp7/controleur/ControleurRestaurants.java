package tp7.controleur;

import tp7.modele.Restaurant;
import tp7.modele.UberEatsModele;
import tp7.vue.FenetreRestaurants;

import java.util.ArrayList;
import java.util.List;

/**
 * CONTROLEUR de la fenetre Restaurants.
 * Il traduit les objets du Modele (Restaurant) en textes pour la Vue,
 * et transmet le choix de l'utilisateur au Modele.
 */
public class ControleurRestaurants {

    private final UberEatsModele modele;
    private final ControleurApplication application;
    private final FenetreRestaurants vue;

    public ControleurRestaurants(UberEatsModele modele, ControleurApplication application) {
        this.modele = modele;
        this.application = application;

        // Modele -> Controleur -> Vue : on prepare les libelles a afficher
        List<String> libelles = new ArrayList<String>();
        for (Restaurant r : modele.getRestaurants()) {
            libelles.add(r.estOuvert() ? r.getNom() : r.getNom() + "  (fermé)");
        }
        this.vue = new FenetreRestaurants(libelles);

        vue.ajouterEcouteurVoirMenu(e -> voirMenu());
        vue.ajouterEcouteurQuitter(e -> application.quitter());
        vue.setVisible(true);
    }

    private void voirMenu() {
        int i = vue.getIndexSelectionne();
        if (i == -1) {
            vue.afficherMessage("Sélectionnez un restaurant.");
            return;
        }
        modele.choisirRestaurant(i);        // le Modele memorise le choix et cree un panier vide
        vue.dispose();
        application.afficherMenu();
    }
}
