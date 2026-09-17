package ubereats;

public class Utilisateur {


    protected int ID;
    protected String nom;
    protected String mail;
    protected boolean connecte;


    public Utilisateur(int ID, String nom, String mail) {
        this.ID = ID;
        this.nom = nom;
        this.mail = mail;
        this.connecte = false;
    }

    public void connection() {
        this.connecte = true;
        System.out.println("[" + nom + "] connexion() -> connecte a l'application");
    }

    public void profil() {

        System.out.println("[" + nom + "] profil() -> ID=" + ID + ", mail=" + mail);
    }

    public void modifierProfile(String nouveauMail) {
        this.mail = nouveauMail;
        System.out.println("[" + nom + "] modifierProfile() -> nouveau mail : " + mail);
    }

    public String getNom() {
        return nom;
    }

    public int getID() {
        return ID;
    }
}
