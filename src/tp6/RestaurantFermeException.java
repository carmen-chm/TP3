package tp6;

// TP6 : deplacee de Main (classe interne au TP5) vers son propre fichier, comme SoldeInsuffisantException
public class RestaurantFermeException extends Exception {

    public RestaurantFermeException(String nomRestaurant) {
        super("le restaurant " + nomRestaurant + " est ferme, commande refusee");
    }
}
