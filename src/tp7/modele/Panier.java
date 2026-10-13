package tp7.modele;

import java.util.ArrayList;
import java.util.List;

/**
 * TP7 (MVC) : le panier devient une vraie classe du Modele.
 * Au TP6, le panier etait une ArrayList locale dans Main et le total etait
 * calcule dans le listener (donc dans la partie graphique).
 * Ici, le Panier garde les plats, calcule le total et NOTIFIE ses observateurs.
 */
public class Panier {

    private ArrayList<Plat> plats;
    private ArrayList<PanierListener> observateurs;   // les Vues abonnees

    public Panier() {
        this.plats = new ArrayList<Plat>();
        this.observateurs = new ArrayList<PanierListener>();
    }

    /** Une Vue s'abonne pour etre prevenue des changements. */
    public void ajouterObservateur(PanierListener l) {
        observateurs.add(l);
        l.panierModifie(lignes(), total());   // affichage initial (panier vide)
    }

    public void ajouterPlat(Plat plat) {
        plats.add(plat);
        notifierObservateurs();
    }

    public void vider() {
        plats.clear();
        notifierObservateurs();
    }

    /** Regle metier : le total est calcule par le Modele, pas par la Vue. */
    public double total() {
        double total = 0.0;
        for (Plat p : plats) {
            total += p.getPrix();
        }
        return total;
    }

    public boolean estVide() {
        return plats.isEmpty();
    }

    public ArrayList<Plat> getPlats() {
        return plats;
    }

    private List<String> lignes() {
        List<String> lignes = new ArrayList<String>();
        for (Plat p : plats) {
            lignes.add("1 x " + p);
        }
        return lignes;
    }

    /** Pattern Observer : chaque Vue abonnee se met a jour toute seule. */
    private void notifierObservateurs() {
        List<String> l = lignes();
        double t = total();
        for (PanierListener obs : observateurs) {
            obs.panierModifie(l, t);
        }
    }
}
