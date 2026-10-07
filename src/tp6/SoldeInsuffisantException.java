package tp6;

public class SoldeInsuffisantException extends Exception {

    public SoldeInsuffisantException(double solde, double montant) {
        super("solde insuffisant : " + solde + " EUR disponibles pour payer " + montant + " EUR");
    }
}
