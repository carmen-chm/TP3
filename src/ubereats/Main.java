package ubereats;

/**
 * Classe Main : point d'entree du programme.
 * Le main() reproduit pas a pas le scenario du DIAGRAMME DE SEQUENCES du TP2 :
 * connexion -> passerCommande -> payer -> accepterCommande -> preparation
 * -> proposerCourse -> livraison -> suivi -> avis et pourboire.
 *
 * @author Equipe Uber Eats - TP3 POO Java
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("=====================================================");
        System.out.println("   APPLICATION UBER EATS - SCENARIO PASSER COMMANDE  ");
        System.out.println("=====================================================\n");

        // --- Instanciation des objets avec new (constructeurs surcharges) ---
        Client client = new Client(1, "Lea", "lea@mail.com");
        Restaurant restaurant = new Restaurant(2, "Chez Mario", "mario@mail.com");
        Livreur livreur = new Livreur(3, "Hugo", "hugo@mail.com");

        System.out.println("--- ETAPE 1 : connexion des acteurs ---");
        client.connection();
        restaurant.connection();
        livreur.connection();
        restaurant.menu("Pizza Margherita");

        System.out.println("\n--- ETAPE 2 : passerCommande() + payer() [includes] ---");
        Commande commande = client.commander(restaurant, "Pizza Margherita", 12.50, "CB");

        System.out.println("\n--- ETAPE 3 : preparation par le restaurant ---");
        restaurant.mettreAJourPreparation(commande);

        System.out.println("\n--- ETAPE 4 : proposerCourse() au livreur ---");
        livreur.reserverCourse(commande);

        System.out.println("\n--- ETAPE 5 : suivi en temps reel de la livraison ---");
        livreur.modifierPosition("Rue Victor Hugo");
        client.suivreCommande(commande);
        livreur.modifierPosition("Devant chez le client");
        livreur.validerLivraison(commande);

        System.out.println("\n--- ETAPE 6 : laisserAvis() + laisserPourboire() [extends] ---");
        client.laisserAvis(commande, "Livraison rapide, livreur tres sympa !");
        client.laisserPourboire(commande, 3.0);
        livreur.consulterAvis(commande);
        restaurant.consulterAvis(commande);

        System.out.println("\n--- ETAPE 7 : recapitulatif de la commande ---");
        commande.afficher();

        // --- Demonstration du POLYMORPHISME (transtypage ascendant) ---
        System.out.println("\n--- BONUS : polymorphisme / upcasting ---");
        Utilisateur[] utilisateurs = { client, restaurant, livreur };
        for (int i = 0; i < utilisateurs.length; i++) {
            utilisateurs[i].profil();   // meme appel, objets de classes differentes
        }

        System.out.println("\nNombre de commandes du client : " + client.nbCommandes());
        System.out.println("Nombre de courses du livreur  : " + livreur.nbCourses());
        System.out.println("\n================== FIN DU SCENARIO ==================");
    }
}
