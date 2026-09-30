package tp5;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Client client = new Client(1, "Lea", "lea@mail.com");
        Restaurant restaurant = new Restaurant(2, "Chez Mario", "mario@mail.com");
        Livreur livreur = new Livreur(3, "Hugo", "hugo@mail.com");

        //connexion
        client.connection();
        restaurant.connection();
        livreur.connection();
        restaurant.menu("Pizza");
        restaurant.menu("Tiramisu");

        //polymorphisme
        Utilisateur[] tousLesUtilisateurs = { client, restaurant, livreur };
        for (Utilisateur u : tousLesUtilisateurs) {
            u.afficherActivite();
        }

        // Liste des commandes livrees, sauvegardees ensuite dans les fichiers (Q2 et Q3)
        ArrayList<RecapCommande> recaps = new ArrayList<RecapCommande>();

        // ================= Q1 : scenario normal, protege par try / catch =================
        System.out.println("\n===== Q1 : scenario normal =====");
        try {
            //Passer commande
            Commande commande = client.commander(restaurant, "Pizza", 12.50, "CB");

            //Preparation de la commande
            restaurant.mettreAJourPreparation(commande);

            //Proposer la course au livreur
            livreur.reserverCourse(commande);

            //Suivre la livraison
            livreur.modifierPosition("Rue");
            client.suivreCommande(commande);
            livreur.modifierPosition("Devant chez le client");
            livreur.validerLivraison(commande);

            //Laisser pourboire et avis
            client.laisserAvis(commande, "Livraison rapide, livreur tres sympa !");
            restaurant.afficherAvis();
            livreur.afficherAvis();
            client.laisserPourboire(commande, 3.0);
            recaps.add(new RecapCommande(commande));

            //Deuxieme commande, payee en especes
            Commande commande2 = client.commander(restaurant, "Tiramisu", 6.0, "Especes");
            restaurant.mettreAJourPreparation(commande2);
            livreur.reserverCourse(commande2);
            livreur.validerLivraison(commande2);
            recaps.add(new RecapCommande(commande2));

        } catch (RestaurantFermeException e) {
            System.out.println("ERREUR : " + e.getMessage());
        } catch (SoldeInsuffisantException e) {
            System.out.println("ERREUR : " + e.getMessage());
        }



        // 1) Solde insuffisant : SoldeInsuffisantException levee dans payer(), propagee par commander()
        try {
            client.commander(restaurant, "Pizza", 500.0, "CB");
        } catch (RestaurantFermeException e) {
            System.out.println("ERREUR : " + e.getMessage());
        } catch (SoldeInsuffisantException e) {
            System.out.println("ERREUR : " + e.getMessage());
        }

        // 2) Restaurant ferme : RestaurantFermeException levee dans accepterCommande(),
        //    propagee par prendreCommande() puis commander()
        Restaurant burger = new Restaurant(4, "Burger Night", "burger@mail.com");
        burger.modifierStatut(false);
        try {
            client.commander(burger, "Burger", 15.0, "CB");
        } catch (RestaurantFermeException e) {
            System.out.println("ERREUR : " + e.getMessage());
        } catch (SoldeInsuffisantException e) {
            System.out.println("ERREUR : " + e.getMessage());
        }

        client.afficherActivite();   // le solde n'a pas bouge pendant les erreurs

        try {
            RecapCommande.ecrireFichierTexte("commandes.txt", recaps);
            System.out.println(recaps.size() + " commande(s) ecrite(s) dans commandes.txt");

            ArrayList<RecapCommande> lues = RecapCommande.lireFichierTexte("commandes.txt");
            System.out.println(lues.size() + " commande(s) lue(s) dans commandes.txt :");
            RecapCommande.afficherListe(lues);
        } catch (FileNotFoundException e) {
            System.out.println("ERREUR : fichier introuvable " + e.getMessage());
        } catch (IOException e) {
            System.out.println("ERREUR d'ecriture : " + e.getMessage());
        }


        try {
            RecapCommande.serialiser("commandes.ser", recaps);
            System.out.println(recaps.size() + " objet(s) serialise(s) dans commandes.ser");

            ArrayList<RecapCommande> recuperes = RecapCommande.deserialiser("commandes.ser");
            System.out.println(recuperes.size() + " objet(s) deserialise(s) depuis commandes.ser :");
            RecapCommande.afficherListe(recuperes);
        } catch (IOException e) {
            System.out.println("ERREUR de lecture / ecriture : " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("ERREUR classe introuvable : " + e.getMessage());
        }
    }


    public static class RestaurantFermeException extends Exception {

        public RestaurantFermeException(String nomRestaurant) {
            super("le restaurant " + nomRestaurant + " est ferme, commande refusee");
        }
    }
}
