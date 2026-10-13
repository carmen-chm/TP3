package tp7.vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * VUE 4 : confirmation de la commande.
 * TP7 (MVC) : au TP6, le texte du recapitulatif etait assemble dans Main.
 * Maintenant c'est la Vue qui decide de la MISE EN FORME ; elle recoit
 * les informations brutes (detail, adresse, solde, historique) du Controleur.
 */
public class FenetreConfirmation extends JFrame {

    private JTextArea zoneRecapitulatif;
    private JButton boutonRetour;

    public FenetreConfirmation(int numeroCommande, String detailCommande, String adresse,
                               double nouveauSolde, List<String> historique) {
        super("Uber Eats - Confirmation");
        setSize(600, 420);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ---------- NORTH : message de confirmation ----------
        JLabel titre = new JLabel("Commande n." + numeroCommande + " confirmée !", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 26));
        titre.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(titre, BorderLayout.NORTH);

        // ---------- CENTER : recapitulatif mis en forme par la Vue ----------
        StringBuilder texte = new StringBuilder();
        texte.append(detailCommande)
             .append("\nLivraison à : ").append(adresse)
             .append("\nNouveau solde : ").append(nouveauSolde).append(" EUR\n\n")
             .append("Vos commandes (relues dans commandes_tp7.ser) :\n");
        for (String ligne : historique) {
            texte.append("  ").append(ligne).append("\n");
        }

        zoneRecapitulatif = new JTextArea(texte.toString());
        zoneRecapitulatif.setEditable(false);
        zoneRecapitulatif.setLineWrap(true);
        zoneRecapitulatif.setWrapStyleWord(true);
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

    // ----- Ce que la Vue offre au Controleur -----
    public void ajouterEcouteurRetour(ActionListener l) { boutonRetour.addActionListener(l); }
}
