package tp5;

public class SoldeInsuffisantException extends Exception {

    public SoldeInsuffisantException(double solde, double montant) {
        super("solde insuffisant : " + solde + " EUR disponibles pour payer " + montant + " EUR");
    }
}
