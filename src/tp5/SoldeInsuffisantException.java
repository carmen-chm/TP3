package tp5;

/**
 * TP5 - Q1 : exception creee dans son propre fichier.
 * Levee par Client.payer() quand le solde ne suffit pas pour payer par CB ou ApplePay.
 * Elle herite de Exception => le compilateur oblige a la declarer (throws) ou a l'attraper (try/catch).
 */
public class SoldeInsuffisantException extends Exception {

    public SoldeInsuffisantException(double solde, double montant) {
        super("solde insuffisant : " + solde + " EUR disponibles pour payer " + montant + " EUR");
    }
}
