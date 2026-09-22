package ubereats;

public abstract class Utilisateur {

    // Attributs
    protected int ID;
    protected String nom;
    protected String mail;
    protected boolean connecte;

    // Constructeur par défaut
    public Utilisateur() {
        this.ID = 0;
        this.nom = "inconnu";
        this.mail = "inconnu@mail.com";
        this.connecte = false;
    }

    // Constructeur surchage
    public Utilisateur(int ID, String nom, String mail) {
        this.ID = ID;
        this.nom = nom;
        this.mail = mail;
        this.connecte = false;
    }

    // Connexion à la plateforme
    public void connection() {
        this.connecte = true;
        System.out.println("[" + nom + "] connexion() -> connecte a l'application");
    }

    // Accesseurs
    public String getNom() {
        return nom;
    }
    public int getID() {
        return ID;
    }

    // Methode abstraite
    public abstract void afficherActivite();
}
