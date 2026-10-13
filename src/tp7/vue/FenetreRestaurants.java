package tp7.vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * VUE 2 : choix du restaurant.
 * TP7 (MVC) : au TP6 la fenetre recevait une ArrayList<Restaurant> et decidait
 * elle-meme d'ajouter "(ferme)". Maintenant elle recoit seulement des textes
 * prepares par le Controleur : elle ne depend plus du Modele.
 */
public class FenetreRestaurants extends JFrame {

    private JList<String> listeRestaurants;
    private JButton boutonVoirMenu;
    private JButton boutonQuitter;

    public FenetreRestaurants(List<String> libellesRestaurants) {
        super("Uber Eats - Restaurants");
        setSize(600, 420);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ---------- NORTH : titre ----------
        JLabel titre = new JLabel("Choisissez un restaurant", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 26));
        titre.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(titre, BorderLayout.NORTH);

        // ---------- CENTER : liste des restaurants ----------
        DefaultListModel<String> modele = new DefaultListModel<>();
        for (String libelle : libellesRestaurants) {
            modele.addElement(libelle);
        }
        listeRestaurants = new JList<>(modele);
        listeRestaurants.setFont(new Font("SansSerif", Font.PLAIN, 18));
        listeRestaurants.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        if (!libellesRestaurants.isEmpty()) {
            listeRestaurants.setSelectedIndex(0);
        }
        JScrollPane defilement = new JScrollPane(listeRestaurants);
        defilement.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 40, 10, 40),
                BorderFactory.createLineBorder(Color.BLACK, 2)));
        add(defilement, BorderLayout.CENTER);

        // ---------- SOUTH : boutons ----------
        boutonVoirMenu = new JButton("Voir le menu");
        boutonQuitter = new JButton("Quitter");
        boutonVoirMenu.setPreferredSize(new Dimension(150, 45));
        boutonQuitter.setPreferredSize(new Dimension(120, 45));
        JPanel panneauBas = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panneauBas.add(boutonVoirMenu);
        panneauBas.add(boutonQuitter);
        add(panneauBas, BorderLayout.SOUTH);
    }

    // ----- Ce que la Vue offre au Controleur -----
    public void ajouterEcouteurVoirMenu(ActionListener l) { boutonVoirMenu.addActionListener(l); }
    public void ajouterEcouteurQuitter(ActionListener l) { boutonQuitter.addActionListener(l); }

    /** -1 si aucun restaurant n'est selectionne. */
    public int getIndexSelectionne() { return listeRestaurants.getSelectedIndex(); }

    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
