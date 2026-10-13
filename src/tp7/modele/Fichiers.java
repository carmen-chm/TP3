package tp7.modele;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * TP6 : toutes les lectures / ecritures de fichiers de l'application.
 * - fichiers texte : restaurants.txt et livreurs.txt (lecture), clients.txt (lecture + ecriture)
 * - serialisation  : commandes_tp7.ser (historique des commandes)
 * Dans les fichiers texte, les lignes vides et celles qui commencent par # sont ignorees.
 */
public class Fichiers {

    public static final String RESTAURANTS = "restaurants.txt";
    public static final String CLIENTS = "clients.txt";
    public static final String LIVREURS = "livreurs.txt";
    public static final String COMMANDES = "commandes_tp7.ser";

    /** Lit toutes les lignes utiles d'un fichier texte avec Scanner. */
    private static ArrayList<String> lireLignes(String nomFichier) throws FileNotFoundException {
        ArrayList<String> lignes = new ArrayList<String>();
        Scanner sc = new Scanner(new File(nomFichier));
        while (sc.hasNextLine()) {
            String ligne = sc.nextLine().trim();
            if (!ligne.isEmpty() && !ligne.startsWith("#")) {
                lignes.add(ligne);
            }
        }
        sc.close();
        return lignes;
    }

    /** Format d'une ligne : nom;mail;ouvert|ferme;plat=prix;plat=prix;... */
    public static ArrayList<Restaurant> lireRestaurants() throws FileNotFoundException {
        ArrayList<Restaurant> liste = new ArrayList<Restaurant>();
        for (String ligne : lireLignes(RESTAURANTS)) {
            String[] d = ligne.split(";");
            Restaurant r = new Restaurant(liste.size() + 1, d[0], d[1], d[2].equals("ouvert"));
            for (int i = 3; i < d.length; i++) {
                String[] plat = d[i].split("=");          // "Margherita=11" -> ["Margherita", "11"]
                r.ajouterPlat(new Plat(plat[0], Double.parseDouble(plat[1])));
            }
            liste.add(r);
        }
        return liste;
    }

    /** Format d'une ligne : email;motDePasse;nom;adresse;solde */
    public static ArrayList<Client> lireClients() throws FileNotFoundException {
        ArrayList<Client> liste = new ArrayList<Client>();
        for (String ligne : lireLignes(CLIENTS)) {
            String[] d = ligne.split(";");
            liste.add(new Client(liste.size() + 1, d[2], d[0], d[1], d[3], Double.parseDouble(d[4])));
        }
        return liste;
    }

    /** Format d'une ligne : nom;mail */
    public static ArrayList<Livreur> lireLivreurs() throws FileNotFoundException {
        ArrayList<Livreur> liste = new ArrayList<Livreur>();
        for (String ligne : lireLignes(LIVREURS)) {
            String[] d = ligne.split(";");
            liste.add(new Livreur(liste.size() + 1, d[0], d[1]));
        }
        return liste;
    }

    /** Reecrit clients.txt : le solde du client change apres chaque commande. */
    public static void ecrireClients(ArrayList<Client> clients) throws IOException {
        FileWriter fw = new FileWriter(CLIENTS);
        fw.write("# email;motDePasse;nom;adresse;solde\n");
        for (Client c : clients) {
            fw.write(c.getMail() + ";" + c.getMotDePasse() + ";" + c.getNom() + ";"
                    + c.getAdresse() + ";" + c.getSolde() + "\n");
        }
        fw.close();
    }


    /** Serialise les objets : d'abord leur nombre (writeInt), puis chaque objet (writeObject). */
    public static void serialiserHistorique(ArrayList<RecapCommande> liste) throws IOException {
        FileOutputStream fos = new FileOutputStream(COMMANDES);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeInt(liste.size());
        for (RecapCommande r : liste) {
            oos.writeObject(r);
        }
        oos.close();
    }

    /** Deserialise les objets dans le meme ordre : readInt() puis readObject() (a caster). */
    public static ArrayList<RecapCommande> deserialiserHistorique() throws IOException, ClassNotFoundException {
        ArrayList<RecapCommande> liste = new ArrayList<RecapCommande>();
        FileInputStream fis = new FileInputStream(COMMANDES);
        ObjectInputStream ois = new ObjectInputStream(fis);
        int nb = ois.readInt();
        for (int i = 0; i < nb; i++) {
            liste.add((RecapCommande) ois.readObject());
        }
        ois.close();
        return liste;
    }
}
