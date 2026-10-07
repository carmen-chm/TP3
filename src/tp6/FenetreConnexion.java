package tp6;

import javax.swing.*;
import java.awt.*;
/**
 * Fenêtre 1 : écran de connexion (wireframe "Uber Eats - Connexion").
 * Disposition : BorderLayout avec NORTH (titre), CENTER (formulaire), SOUTH (bouton).
 */
public class FenetreConnexion extends JFrame {

    // Composants conservés en attributs : ils serviront aux listeners (Question 4)
    private JTextField champEmail;
    private JPasswordField champMotDePasse;
    private JButton boutonConnexion;

    public FenetreConnexion() {
        super("Uber Eats - Connexion");               // titre de la fenêtre (constructeur de JFrame)
        setSize(600, 420);                            // taille de la fenêtre
        setDefaultCloseOperation(EXIT_ON_CLOSE);      // fermer la fenêtre arrête le programme
        setLocationRelativeTo(null);                  // centre la fenêtre sur l'écran
        setLayout(new BorderLayout(10, 10));          // gestionnaire de disposition : 5 zones

        // ---------- NORTH : titre de bienvenue ----------
        JLabel titre = new JLabel("Bienvenue sur Uber Eats", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 26));
        titre.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10)); // marges
        add(titre, BorderLayout.NORTH);

        // ---------- CENTER : formulaire (2 lignes x 2 colonnes) ----------
        JPanel formulaire = new JPanel(new GridLayout(2, 2, 15, 25));
        formulaire.setBorder(BorderFactory.createEmptyBorder(30, 60, 60, 60));

        champEmail = new JTextField();                // saisie de l'email
        champMotDePasse = new JPasswordField();       // saisie masquée du mot de passe

        formulaire.add(new JLabel("Email"));
        formulaire.add(champEmail);
        formulaire.add(new JLabel("Mot de passe"));
        formulaire.add(champMotDePasse);
        add(formulaire, BorderLayout.CENTER);

        // ---------- SOUTH : bouton de connexion ----------
        boutonConnexion = new JButton("Se connecter");
        boutonConnexion.setPreferredSize(new Dimension(160, 45));
        JPanel panneauBas = new JPanel(new FlowLayout(FlowLayout.CENTER)); // centre le bouton
        panneauBas.add(boutonConnexion);
        add(panneauBas, BorderLayout.SOUTH);
    }

    // Accesseurs utiles pour la Question 4 (ajout des listeners)
    public JButton getBoutonConnexion() { return boutonConnexion; }
    public String getEmail() { return champEmail.getText(); }
    public String getMotDePasse() { return new String(champMotDePasse.getPassword()); }
}
