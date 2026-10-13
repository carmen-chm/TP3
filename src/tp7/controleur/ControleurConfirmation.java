package tp7.controleur;

import tp7.modele.Client;
import tp7.modele.RecapCommande;
import tp7.modele.UberEatsModele;
import tp7.vue.FenetreConfirmation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * CONTROLEUR de la fenetre Confirmation.
 * Il recupere dans le Modele la commande, le client et l'historique deserialise,
 * puis les donne a la Vue qui s'occupe seule de la mise en forme.
 */
public class ControleurConfirmation {

    private final FenetreConfirmation vue;

    public ControleurConfirmation(UberEatsModele modele, ControleurApplication application, RecapCommande recap) {
        Client client = modele.getClientConnecte();

        // Relecture de commandes_tp7.ser par le Modele (deserialisation)
        List<String> historique = new ArrayList<String>();
        try {
            for (RecapCommande r : modele.historiqueClientConnecte()) {
                historique.add(r.toString());
            }
        } catch (IOException | ClassNotFoundException ex) {
            historique.add("Historique illisible : " + ex.getMessage());
        }

        this.vue = new FenetreConfirmation(recap.getNumero(), recap.detail(),
                client.getAdresse(), client.getSolde(), historique);

        // "Retour a l'accueil" : deconnexion dans le Modele, puis retour a la fenetre 1
        vue.ajouterEcouteurRetour(e -> {
            modele.deconnecter();
            vue.dispose();
            application.afficherConnexion();
        });
        vue.setVisible(true);
    }
}
