package tp7.controleur;

import tp7.modele.RecapCommande;
import tp7.modele.UberEatsModele;

/**
 * CONTROLEUR principal : la navigation entre les fenetres (storyboard du TP6).
 * Connexion -> Restaurants -> Menu et panier -> Confirmation -> Connexion.
 * Chaque etape est confiee a un controleur dedie (1 controleur = 1 vue).
 * Tous partagent le MEME objet Modele.
 */
public class ControleurApplication {

    private final UberEatsModele modele;

    public ControleurApplication(UberEatsModele modele) {
        this.modele = modele;
    }

    public void demarrer() { afficherConnexion(); }

    public void afficherConnexion() { new ControleurConnexion(modele, this); }

    public void afficherRestaurants() { new ControleurRestaurants(modele, this); }

    public void afficherMenu() { new ControleurMenu(modele, this); }

    public void afficherConfirmation(RecapCommande recap) { new ControleurConfirmation(modele, this, recap); }

    public void quitter() { System.exit(0); }
}
