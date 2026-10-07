package tp6;

import javax.swing.*;
import java.awt.*;

/**
 * Fenêtre 4 : confirmation de la commande (wireframe "Uber Eats - Confirmation").
 * Disposition : NORTH (message), CENTER (récapitulatif), SOUTH (bouton).
 */
public class FenetreConfirmation extends JFrame {

    private JTextArea zoneRecapitulatif;
    private JButton boutonRetour;

    public FenetreConfirmation(String message, String recapitulatif) {
        super("Uber Eats - Confirmation");
        setSize(600, 420);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ---------- NORTH : message de confirmation ----------
        JLabel titre = new JLabel(message, SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 26));
        titre.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(titre, BorderLayout.NORTH);

        // ---------- CENTER : récapitulatif (non éditable) ----------
        zoneRecapitulatif = new JTextArea(recapitulatif);
        zoneRecapitulatif.setEditable(false);                 // lecture seule
        zoneRecapitulatif.setLineWrap(true);                  // retour à la ligne automatique
        zoneRecapitulatif.setWrapStyleWord(true);             // ... sans couper les mots
        zoneRecapitulatif.setFont(new Font("SansSerif", Font.PLAIN, 15));

        JScrollPane defilement = new JScrollPane(zoneRecapitulatif);
        defilement.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 40, 10, 40),
                BorderFactory.createLineBorder(Color.BLACK, 2)));
        add(defilement, BorderLayout.CENTER);

        // ---------- SOUTH : bouton de retour ----------
        boutonRetour = new JButton("Retour à l'accueil");
        boutonRetour.setPreferredSize(new Dimension(180, 45));
        JPanel panneauBas = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panneauBas.add(boutonRetour);
        add(panneauBas, BorderLayout.SOUTH);
    }

    public JTextArea getZoneRecapitulatif() { return zoneRecapitulatif; }
    public JButton getBoutonRetour() { return boutonRetour; }
}
