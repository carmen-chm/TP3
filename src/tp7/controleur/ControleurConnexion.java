package tp7.controleur;

import tp7.modele.ConnexionException;
import tp7.modele.UberEatsModele;
import tp7.vue.FenetreConnexion;

/**
 * CONTROLEUR de la fenetre Connexion.
 * Role : recevoir l'action de l'utilisateur (clic), la transmettre au Modele,
 * puis choisir quoi afficher (fenetre suivante ou message d'erreur).
 */
public class ControleurConnexion {

    private final UberEatsModele modele;
    private final ControleurApplication application;
    private final FenetreConnexion vue;

    public ControleurConnexion(UberEatsModele modele, ControleurApplication application) {
        this.modele = modele;
        this.application = application;
        this.vue = new FenetreConnexion();

        // ActionListener : le Controleur s'abonne a l'evenement de la Vue
        vue.ajouterEcouteurConnexion(e -> seConnecter());
        vue.setVisible(true);
    }

    private void seConnecter() {
        try {
            // 1) Vue -> Controleur : on lit la saisie
            // 2) Controleur -> Modele : la verification (regle metier) est faite par le Modele
            modele.connecter(vue.getEmail(), vue.getMotDePasse());
            // 3) Controleur -> navigation
            vue.dispose();
            application.afficherRestaurants();
        } catch (ConnexionException ex) {
            // 3 bis) Controleur -> Vue : affichage de l'erreur levee par le Modele
            vue.afficherErreur(ex.getMessage());
        }
    }
}
