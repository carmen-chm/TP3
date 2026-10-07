package tp6;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Fenêtre 2 : choix du restaurant (wireframe "Uber Eats - Restaurants").
 * Disposition : NORTH (titre), CENTER (liste), SOUTH (2 boutons).
 */
public class FenetreRestaurants extends JFrame {

    private JList<String> listeRestaurants;
    private JButton boutonVoirMenu;
    private JButton boutonQuitter;

    // TP6 Q4 : la liste est remplie avec les restaurants lus dans restaurants.txt
    public FenetreRestaurants(ArrayList<Restaurant> restaurants) {
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
        // DefaultListModel = données de la liste, dans le même ordre que l'ArrayList restaurants
        DefaultListModel<String> modele = new DefaultListModel<>();
        for (Restaurant r : restaurants) {
            modele.addElement(r.estOuvert() ? r.getNom() : r.getNom() + "  (fermé)");
        }

        listeRestaurants = new JList<>(modele);
        listeRestaurants.setFont(new Font("SansSerif", Font.PLAIN, 18));
        listeRestaurants.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 1 seul choix
        if (!restaurants.isEmpty()) {
            listeRestaurants.setSelectedIndex(0);                               // 1er élément présélectionné
        }

        JScrollPane defilement = new JScrollPane(listeRestaurants); // barre de défilement si besoin
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

    public JList<String> getListeRestaurants() { return listeRestaurants; }
    public JButton getBoutonVoirMenu() { return boutonVoirMenu; }
    public JButton getBoutonQuitter() { return boutonQuitter; }
}
