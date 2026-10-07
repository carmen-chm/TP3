package tp6;

/** TP6 : un plat de la carte d'un restaurant (nom + prix), lu dans restaurants.txt. */
public class Plat {

    private String nom;
    private double prix;

    public Plat(String nom, double prix) {
        this.nom = nom;
        this.prix = prix;
    }

    public String getNom() {
        return nom;
    }

    public double getPrix() {
        return prix;
    }

    /** Texte affiche dans les listes de la fenetre Menu. */
    @Override
    public String toString() {
        return nom + " - " + prix + " EUR";
    }
}
