package ubereats;

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

        //polymorphisme
        Utilisateur[] tousLesUtilisateurs = { client, restaurant, livreur };
        for (Utilisateur u : tousLesUtilisateurs) {
            u.afficherActivite();
        }

        //Passer commande
        Commande commande = client.commander(restaurant, "Pizza", 12.50, "CB");
        boolean commandeAcceptee = commande.getStatut().equals("en preparation");

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
    }
}