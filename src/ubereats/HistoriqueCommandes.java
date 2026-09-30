package ubereats;

import java.util.ArrayList;

public class HistoriqueCommandes implements Comparable<HistoriqueCommandes> {

    private ArrayList<Commande> commandes;

    public HistoriqueCommandes() {
        this.commandes = new ArrayList<Commande>();
    }

    @Override
    public int compareTo(HistoriqueCommandes autre) {
        return Double.compare(this.montantTotal(), autre.montantTotal());
    }
    public void ajouterCommande(Commande c) {
        commandes.add(c);
        System.out.println("   ajouterCommande() -> historique = " + commandes.size() + " commande(s)");
    }

    public double montantTotal() {
        double total = 0.0;
        for (Commande c : commandes) {
            total += c.getMontant();
        }
        return total;
    }

    public int nbCommandes() {
        return commandes.size();
    }

}
