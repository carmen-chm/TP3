package tp6;

import javax.swing.*;
import java.awt.*;

/**
 * Fenêtre 3 : menu du restaurant et panier (wireframe "Uber Eats - Menu et panier").
 * Disposition : NORTH (titre), WEST (plats), CENTER (panier), SOUTH (total + boutons).
 * TP6 Q4 : les plats viennent du restaurant choisi, le panier démarre vide,
 * le solde du client est affiché et un bouton "Retour" ramène à la liste des restaurants.
 */
public class FenetreMenu extends JFrame {

    private JList<String> listePlats;
    private DefaultListModel<String> modelePanier;    // modifié par le listener "Ajouter au panier"
    private JLabel labelTotal;
    private JButton boutonAjouter;
    private JButton boutonCommander;
    private JButton boutonRetour;

    public FenetreMenu(Restaurant restaurant, Client client) {
        super("Uber Eats - Menu et panier");
        setSize(750, 460);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ---------- NORTH : nom du restaurant + client connecté ----------
        JLabel titre = new JLabel("Menu - " + restaurant.getNom(), SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 26));
        JLabel infoClient = new JLabel("Connecté : " + client.getNom()
                + "   |   Solde : " + client.getSolde() + " EUR", SwingConstants.CENTER);

        JPanel panneauHaut = new JPanel(new GridLayout(2, 1));
        panneauHaut.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));
        panneauHaut.add(titre);
        panneauHaut.add(infoClient);
        add(panneauHaut, BorderLayout.NORTH);

        // ---------- WEST : liste des plats du restaurant ----------
        DefaultListModel<String> modelePlats = new DefaultListModel<>();
        for (Plat p : restaurant.getMenu()) {
            modelePlats.addElement(p.toString());             // "Margherita - 11.0 EUR"
        }
        listePlats = new JList<>(modelePlats);
        listePlats.setFont(new Font("SansSerif", Font.PLAIN, 17));
        listePlats.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel panneauPlats = new JPanel(new BorderLayout(0, 5));
        panneauPlats.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 5));
        panneauPlats.setPreferredSize(new Dimension(260, 0));
        panneauPlats.add(new JLabel("Plats"), BorderLayout.NORTH);
        panneauPlats.add(new JScrollPane(listePlats), BorderLayout.CENTER);
        add(panneauPlats, BorderLayout.WEST);

        // ---------- CENTER : panier (vide au départ) ----------
        modelePanier = new DefaultListModel<>();
        JList<String> listePanier = new JList<>(modelePanier);
        listePanier.setFont(new Font("SansSerif", Font.PLAIN, 17));

        JPanel panneauPanier = new JPanel(new BorderLayout(0, 5));
        panneauPanier.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 15));
        panneauPanier.add(new JLabel("Panier"), BorderLayout.NORTH);
        panneauPanier.add(new JScrollPane(listePanier), BorderLayout.CENTER);
        add(panneauPanier, BorderLayout.CENTER);

        // ---------- SOUTH : total + boutons ----------
        labelTotal = new JLabel("Total : 0.0 EUR");
        labelTotal.setFont(new Font("SansSerif", Font.BOLD, 18));
        boutonAjouter = new JButton("Ajouter au panier");
        boutonCommander = new JButton("Commander");
        boutonRetour = new JButton("Retour");
        boutonAjouter.setPreferredSize(new Dimension(170, 45));
        boutonCommander.setPreferredSize(new Dimension(150, 45));
        boutonRetour.setPreferredSize(new Dimension(110, 45));

        JPanel panneauBas = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panneauBas.add(labelTotal);
        panneauBas.add(boutonAjouter);
        panneauBas.add(boutonCommander);
        panneauBas.add(boutonRetour);
        add(panneauBas, BorderLayout.SOUTH);
    }

    public JList<String> getListePlats() { return listePlats; }
    public DefaultListModel<String> getModelePanier() { return modelePanier; }
    public JLabel getLabelTotal() { return labelTotal; }
    public JButton getBoutonAjouter() { return boutonAjouter; }
    public JButton getBoutonCommander() { return boutonCommander; }
    public JButton getBoutonRetour() { return boutonRetour; }
}
