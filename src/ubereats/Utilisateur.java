package ubereats;

public abstract class Utilisateur {

    // Attributs : visibilite protected pour etre accessibles aux classes filles
    protected int ID;
    protected String nom;
    protected String mail;
    protected boolean connecte;

    /** Constructeur par defaut (sans parametre). */
    public Utilisateur() {
        this.ID = 0;
        this.nom = "inconnu";
        this.mail = "inconnu@mail.com";
        this.connecte = false;
    }

    /** Constructeur surcharge avec parametres. */
    public Utilisateur(int ID, String nom, String mail) {
        this.ID = ID;
        this.nom = nom;
        this.mail = mail;
        this.connecte = false;
    }

    /** Connexion de l'utilisateur a l'application. */
    public void connection() {
        this.connecte = true;
        System.out.println("[" + nom + "] connexion() -> connecte a l'application");
    }

    /** Affiche le profil de l'utilisateur. */
    public void profil() {
        System.out.println("[" + nom + "] profil() -> ID=" + ID + ", mail=" + mail);
    }

    /** Modifie le mail du profil. */
    public void modifierProfile(String nouveauMail) {
        this.mail = nouveauMail;
        System.out.println("[" + nom + "] modifierProfile() -> nouveau mail : " + mail);
    }

    // Accesseurs (encapsulation)
    public String getNom() {
        return nom;
    }

    public int getID() {
        return ID;
    }

    public abstract void afficherActivite();
}
