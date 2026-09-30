package tp5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * TP5 - Q2 et Q3 : les 5 informations d'une commande que l'on sauvegarde :
 * nom du restaurant, nom du client, nom du livreur, numero de commande, montant.
 *
 * Q2 : fichier texte, une ligne par commande, donnees separees par ';'
 *      exemple : Chez Mario;Lea;Hugo;1;12.5
 * Q3 : serialisation des memes objets dans un fichier binaire .ser
 *      (implements Serializable : l'interface n'a aucune methode, c'est un simple "marqueur").
 */
public class RecapCommande implements Serializable {

    // numero de version de la classe, verifie a la deserialisation
    private static final long serialVersionUID = 1L;

    private String nomRestaurant;
    private String nomClient;
    private String nomLivreur;
    private int numeroCommande;
    private double montant;

    public RecapCommande(String nomRestaurant, String nomClient, String nomLivreur,
                         int numeroCommande, double montant) {
        this.nomRestaurant = nomRestaurant;
        this.nomClient = nomClient;
        this.nomLivreur = nomLivreur;
        this.numeroCommande = numeroCommande;
        this.montant = montant;
    }

    /** Construit le recapitulatif a partir d'une commande du programme. */
    public RecapCommande(Commande c) {
        this(c.getRestaurant().getNom(), c.getClient().getNom(),
             c.getLivreur() != null ? c.getLivreur().getNom() : "aucun",
             c.getNumero(), c.getMontant());
    }

    public void afficher() {
        System.out.println("Commande n." + numeroCommande + " : restaurant = " + nomRestaurant
                + ", client = " + nomClient + ", livreur = " + nomLivreur + ", montant = " + montant + " EUR");
    }

    // ================= Q2 : fichier texte =================

    /** Ecrit une ligne par commande : restaurant;client;livreur;numero;montant */
    public static void ecrireFichierTexte(String nomFichier, ArrayList<RecapCommande> liste) throws IOException {
        FileWriter fw = new FileWriter(nomFichier);
        for (RecapCommande r : liste) {
            fw.write(r.nomRestaurant + ";" + r.nomClient + ";" + r.nomLivreur + ";"
                    + r.numeroCommande + ";" + r.montant + "\n");
        }
        fw.close();
    }

    /** Lit le fichier ligne par ligne avec Scanner, decoupe chaque ligne avec split(";"). */
    public static ArrayList<RecapCommande> lireFichierTexte(String nomFichier) throws FileNotFoundException {
        ArrayList<RecapCommande> liste = new ArrayList<RecapCommande>();
        Scanner sc = new Scanner(new File(nomFichier));
        while (sc.hasNextLine()) {
            String[] d = sc.nextLine().split(";");
            liste.add(new RecapCommande(d[0], d[1], d[2], Integer.parseInt(d[3]), Double.parseDouble(d[4])));
        }
        sc.close();
        return liste;
    }

    // ================= Q3 : serialisation =================

    /** Serialise les objets : d'abord leur nombre (writeInt), puis chaque objet (writeObject). */
    public static void serialiser(String nomFichier, ArrayList<RecapCommande> liste) throws IOException {
        FileOutputStream fos = new FileOutputStream(nomFichier);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeInt(liste.size());
        for (RecapCommande r : liste) {
            oos.writeObject(r);
        }
        oos.close();
    }

    /** Deserialise les objets dans le meme ordre : readInt() puis readObject() (a caster). */
    public static ArrayList<RecapCommande> deserialiser(String nomFichier) throws IOException, ClassNotFoundException {
        ArrayList<RecapCommande> liste = new ArrayList<RecapCommande>();
        FileInputStream fis = new FileInputStream(nomFichier);
        ObjectInputStream ois = new ObjectInputStream(fis);
        int nb = ois.readInt();
        for (int i = 0; i < nb; i++) {
            liste.add((RecapCommande) ois.readObject());
        }
        ois.close();
        return liste;
    }

    /** Affiche les commandes recuperees (depuis le .txt ou le .ser). */
    public static void afficherListe(ArrayList<RecapCommande> liste) {
        for (RecapCommande r : liste) {
            r.afficher();
        }
    }
}
