package tp6;

import java.io.Serializable;

/**
 * Resume d'une commande passee, enregistre dans commandes.ser (serialisation).
 * TP6 : les lectures / ecritures de fichiers sont regroupees dans la classe Fichiers.
 */
public class RecapCommande implements Serializable {

    // numero de version de la classe, verifie a la deserialisation
    // (passe a 2 au TP6 car les attributs ont change : un ancien commandes.ser du TP5 est refuse)
    private static final long serialVersionUID = 2L;

    private int numeroCommande;
    private String date;
    private String nomClient;
    private String mailClient;
    private String nomRestaurant;
    private String nomLivreur;
    private String contenu;
    private double montant;

    /** Construit le recapitulatif a partir d'une commande du programme. */
    public RecapCommande(Commande c) {
        this.numeroCommande = c.getNumero();
        this.date = c.getDate();
        this.nomClient = c.getClient().getNom();
        this.mailClient = c.getClient().getMail();
        this.nomRestaurant = c.getRestaurant().getNom();
        this.nomLivreur = c.getLivreur() != null ? c.getLivreur().getNom() : "aucun";
        this.contenu = String.join(", ", c.getContenu());
        this.montant = c.getMontant();
    }

    public int getNumero() {
        return numeroCommande;
    }

    public String getMailClient() {
        return mailClient;
    }

    /** Une ligne : utilisee pour la liste de l'historique. */
    @Override
    public String toString() {
        return "n." + numeroCommande + " du " + date + " - " + nomRestaurant + " - " + montant + " EUR";
    }

    /** Plusieurs lignes : utilisees pour le detail dans la fenetre de confirmation. */
    public String detail() {
        return "Commande n." + numeroCommande + " du " + date + "\n"
                + "Client : " + nomClient + "\n"
                + "Restaurant : " + nomRestaurant + "\n"
                + "Plats : " + contenu + "\n"
                + "Montant : " + montant + " EUR\n"
                + "Livreur : " + nomLivreur;
    }
}
