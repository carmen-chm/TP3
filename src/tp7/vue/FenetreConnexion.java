package tp7.vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * VUE 1 : ecran de connexion.
 * TP7 (MVC) : la Vue ne contient AUCUNE regle metier et ne connait pas le Modele.
 * Elle affiche des composants, donne les valeurs saisies et laisse le Controleur
 * s'abonner aux clics via ajouterEcouteurConnexion().
 */
public class FenetreConnexion extends JFrame {

    private JTextField champEmail;
    private JPasswordField champMotDePasse;
    private JButton boutonConnexion;

    public FenetreConnexion() {
        super("Uber Eats - Connexion");
        setSize(600, 420);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ---------- NORTH : titre de bienvenue ----------
        JLabel titre = new JLabel("Bienvenue sur Uber Eats", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 26));
        titre.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(titre, BorderLayout.NORTH);

        // ---------- CENTER : formulaire (2 lignes x 2 colonnes) ----------
        JPanel formulaire = new JPanel(new GridLayout(2, 2, 15, 25));
        formulaire.setBorder(BorderFactory.createEmptyBorder(30, 60, 60, 60));
        champEmail = new JTextField();
        champMotDePasse = new JPasswordField();
        formulaire.add(new JLabel("Email"));
        formulaire.add(champEmail);
        formulaire.add(new JLabel("Mot de passe"));
        formulaire.add(champMotDePasse);
        add(formulaire, BorderLayout.CENTER);

        // ---------- SOUTH : bouton de connexion ----------
        boutonConnexion = new JButton("Se connecter");
        boutonConnexion.setPreferredSize(new Dimension(160, 45));
        JPanel panneauBas = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panneauBas.add(boutonConnexion);
        add(panneauBas, BorderLayout.SOUTH);
    }

    // ----- Ce que la Vue offre au Controleur -----

    /** Le Controleur s'abonne au clic (le bouton reste prive : encapsulation). */
    public void ajouterEcouteurConnexion(ActionListener l) { boutonConnexion.addActionListener(l); }

    public String getEmail() { return champEmail.getText(); }
    public String getMotDePasse() { return new String(champMotDePasse.getPassword()); }

    public void afficherErreur(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur de connexion", JOptionPane.ERROR_MESSAGE);
    }
}
