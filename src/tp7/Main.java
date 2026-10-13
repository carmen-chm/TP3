package tp7;

import tp7.controleur.ControleurApplication;
import tp7.modele.UberEatsModele;

import javax.swing.*;
import java.io.FileNotFoundException;

/**
 * TP7 : application Uber Eats (TP2 a TP6) reorganisee avec le pattern MVC.
 *   - tp7.modele     : donnees + regles metier + fichiers (aucun import Swing)
 *   - tp7.vue        : fenetres Swing (aucune regle metier)
 *   - tp7.controleur : listeners, appels au Modele, navigation entre les Vues
 * Le Main se contente d'assembler les trois parties.
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UberEatsModele modele = new UberEatsModele();          // M
            try {
                modele.charger();                                   // lecture des fichiers (TP5/TP6)
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Fichier introuvable : " + e.getMessage()
                                + "\nLes fichiers .txt doivent être dans : " + System.getProperty("user.dir"),
                        "Erreur au démarrage", JOptionPane.ERROR_MESSAGE);
                return;
            }
            new ControleurApplication(modele).demarrer();           // C (qui cree les V)
        });
    }
}
