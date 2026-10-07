package tp6;

// TP6 : levee quand l'email est invalide, le compte inconnu ou le mot de passe incorrect
public class ConnexionException extends Exception {

    public ConnexionException(String message) {
        super("connexion refusee : " + message);
    }
}
