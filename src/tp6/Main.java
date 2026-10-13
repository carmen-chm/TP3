package tp6;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Application Uber Eats (TP2 a TP6) : point d'entree unique.
 * 1) Au demarrage : lecture des fichiers texte et deserialisation de l'historique.
 * 2) Ensuite, les ActionListener enchainent les fenetres comme dans le storyboard (Q2) :
 *    Connexion -> Restaurants -> Menu et panier -> Confirmation -> Connexion.
 * Aucune donnee n'est ecrite en dur : tout vient de restaurants.txt, clients.txt et livreurs.txt.
 */
public class Main {

    // Donnees chargees depuis les fichiers au demarrage
    private static ArrayList<Restaurant> restaurants;
    private static ArrayList<Client> clients;
    private static ArrayList<Livreur> livreurs;
    private static ArrayList<RecapCommande> historique;

    private static Client client;   // client actuellement connecte

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> demarrer());   // Swing est lance dans l'Event Dispatch Thread
    }

    private static void demarrer() {
        // Lecture des fichiers texte : sans eux l'application ne peut pas fonctionner
        try {
            restaurants = Fichiers.lireRestaurants();
            clients = Fichiers.lireClients();
            livreurs = Fichiers.lireLivreurs();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Fichier introuvable : " + e.getMessage()
                            + "\nLes fichiers .txt doivent etre dans : " + System.getProperty("user.dir"),
                    "Erreur au demarrage", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Deserialisation de l'historique (absent au tout premier lancement)
        try {
            historique = Fichiers.deserialiserHistorique();
        } catch (IOException e) {
            historique = new ArrayList<RecapCommande>();
        } catch (ClassNotFoundException e) {
            historique = new ArrayList<RecapCommande>();
        }
        // Les nouvelles commandes reprennent la numerotation apres la derniere enregistree
        if (!historique.isEmpty()) {
            Commande.initialiserCompteur(historique.get(historique.size() - 1).getNumero());
        }

        ouvrirConnexion();
    }

    // ===================== Fenetre 1 : Connexion =====================
    private static void ouvrirConnexion() {
        FenetreConnexion fenetre = new FenetreConnexion();

        // ActionListener : clic sur "Se connecter"
        fenetre.getBoutonConnexion().addActionListener(e -> {
            try {
                client = connecter(fenetre.getEmail().trim(), fenetre.getMotDePasse());
                client.connection();
                fenetre.dispose();                     // ferme la fenetre actuelle
                ouvrirRestaurants();                   // ouvre la fenetre suivante
            } catch (ConnexionException ex) {
                JOptionPane.showMessageDialog(fenetre, ex.getMessage(),
                        "Erreur de connexion", JOptionPane.ERROR_MESSAGE);
            }
        });
        fenetre.setVisible(true);
    }

    /** Cherche le compte dans la liste lue depuis clients.txt ; leve une ConnexionException sinon. */
    private static Client connecter(String email, String motDePasse) throws ConnexionException {
        if (!email.contains("@")) {
            throw new ConnexionException("email invalide \"" + email + "\"");
        }
        for (Client c : clients) {
            if (c.getMail().equalsIgnoreCase(email)) {
                if (!c.motDePasseCorrect(motDePasse)) {
                    throw new ConnexionException("mot de passe incorrect");
                }
                return c;
            }
        }
        throw new ConnexionException("aucun compte pour " + email);
    }

    // ===================== Fenetre 2 : Restaurants =====================
    private static void ouvrirRestaurants() {
        FenetreRestaurants fenetre = new FenetreRestaurants(restaurants);

        // ActionListener : clic sur "Voir le menu"
        fenetre.getBoutonVoirMenu().addActionListener(e -> {
            int i = fenetre.getListeRestaurants().getSelectedIndex();                            // -1 si rien n'est selectionne
            if (i == -1) {
                JOptionPane.showMessageDialog(fenetre, "Selectionnez un restaurant.");
                return;
            }
            fenetre.dispose();
            ouvrirMenu(restaurants.get(i));
        });

        // ActionListener : clic sur "Quitter" -> ferme l'application
        fenetre.getBoutonQuitter().addActionListener(e -> System.exit(0));
        fenetre.setVisible(true);
    }

    // ===================== Fenetre 3 : Menu et panier =====================
    private static void ouvrirMenu(Restaurant restaurant) {
        FenetreMenu fenetre = new FenetreMenu(restaurant, client);
        ArrayList<Plat> panier = new ArrayList<Plat>();

        // ActionListener : clic sur "Ajouter au panier" -> met a jour le panier et le total
        fenetre.getBoutonAjouter().addActionListener(e -> {
            int i = fenetre.getListePlats().getSelectedIndex();
            if (i == -1) {
                JOptionPane.showMessageDialog(fenetre, "Selectionnez un plat.");
                return;
            }
            Plat plat = restaurant.getMenu().get(i);
            panier.add(plat);
            fenetre.getModelePanier().addElement("1 x " + plat);
            fenetre.getLabelTotal().setText("Total : " + calculerTotal(panier) + " EUR");
        });

        // ActionListener : clic sur "Commander" -> Client.commander() du TP5 et ses exceptions
        fenetre.getBoutonCommander().addActionListener(e -> {
            if (panier.isEmpty()) {
                JOptionPane.showMessageDialog(fenetre, "Le panier est vide.");
                return;
            }
            try {
                Commande commande = client.commander(restaurant, panier, "CB");
                restaurant.mettreAJourPreparation(commande);
                livrer(commande);

                RecapCommande recap = new RecapCommande(commande);
                historique.add(recap);
                sauvegarder();
                fenetre.dispose();
                ouvrirConfirmation(recap);
            } catch (RestaurantFermeException ex) {
                JOptionPane.showMessageDialog(fenetre, ex.getMessage(),
                        "Restaurant ferme", JOptionPane.ERROR_MESSAGE);
            } catch (SoldeInsuffisantException ex) {
                JOptionPane.showMessageDialog(fenetre, ex.getMessage(),
                        "Paiement refuse", JOptionPane.ERROR_MESSAGE);
            }
        });

        // ActionListener : clic sur "Retour" -> retour a la liste des restaurants
        fenetre.getBoutonRetour().addActionListener(e -> {
            fenetre.dispose();
            ouvrirRestaurants();
        });
        fenetre.setVisible(true);
    }

    private static double calculerTotal(ArrayList<Plat> panier) {
        double total = 0.0;
        for (Plat p : panier) {
            total += p.getPrix();
        }
        return total;
    }

    /** Les livreurs de livreurs.txt prennent les courses chacun leur tour. */
    private static void livrer(Commande commande) {
        if (livreurs.isEmpty()) {
            return;
        }
        Livreur livreur = livreurs.get(commande.getNumero() % livreurs.size());
        livreur.reserverCourse(commande);
        livreur.validerLivraison(commande);
    }

    // ===================== Fenetre 4 : Confirmation =====================
    private static void ouvrirConfirmation(RecapCommande recap) {
        String texte = recap.detail()
                + "\nLivraison a : " + client.getAdresse()
                + "\nNouveau solde : " + client.getSolde() + " EUR\n\n";

        // Relecture de commandes.ser (deserialisation) pour afficher les commandes de ce client
        try {
            texte += "Vos commandes (relues dans " + Fichiers.COMMANDES + ") :\n";
            for (RecapCommande r : Fichiers.deserialiserHistorique()) {
                if (client.getMail().equals(r.getMailClient())) {
                    texte += "  " + r + "\n";
                }
            }
        } catch (IOException ex) {
            texte += "Historique illisible : " + ex.getMessage();
        } catch (ClassNotFoundException ex) {
            texte += "Historique illisible : " + ex.getMessage();
        }

        FenetreConfirmation fenetre = new FenetreConfirmation(
                "Commande n." + recap.getNumero() + " confirmée !", texte);

        // ActionListener : clic sur "Retour a l'accueil" -> deconnexion, retour a la fenetre 1
        fenetre.getBoutonRetour().addActionListener(e -> {
            client = null;
            fenetre.dispose();
            ouvrirConnexion();
        });
        fenetre.setVisible(true);
    }

    // ===================== Sauvegarde (TP5) =====================

    /** Ecrit le nouveau solde dans clients.txt et serialise l'historique dans commandes.ser. */
    private static void sauvegarder() {
        try {
            Fichiers.ecrireClients(clients);
            Fichiers.serialiserHistorique(historique);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erreur d'ecriture : " + e.getMessage(),
                    "Erreur fichier", JOptionPane.ERROR_MESSAGE);
        }
    }
}
