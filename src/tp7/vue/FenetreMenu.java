package tp7.vue;

import tp7.modele.PanierListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * VUE 3 : menu du restaurant et panier.
 * TP7 (MVC) : la Vue OBSERVE le Panier du Modele (implements PanierListener).
 * Quand le Modele change, il appelle panierModifie() et la Vue se redessine :
 * le Controleur n'a plus besoin de toucher a la liste ni au label du total.
 */
public class FenetreMenu extends JFrame implements PanierListener {

    private JList<String> listePlats;
    private DefaultListModel<String> modelePanier;
    private JLabel labelTotal;
    private JButton boutonAjouter;
    private JButton boutonCommander;
    private JButton boutonRetour;

    public FenetreMenu(String nomRestaurant, String infoClient, List<String> libellesPlats) {
        super("Uber Eats - Menu et panier");
        setSize(750, 460);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ---------- NORTH : nom du restaurant + client connecte ----------
        JLabel titre = new JLabel("Menu - " + nomRestaurant, SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 26));
        JLabel labelClient = new JLabel(infoClient, SwingConstants.CENTER);
        JPanel panneauHaut = new JPanel(new GridLayout(2, 1));
        panneauHaut.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));
        panneauHaut.add(titre);
        panneauHaut.add(labelClient);
        add(panneauHaut, BorderLayout.NORTH);

        // ---------- WEST : liste des plats ----------
        DefaultListModel<String> modelePlats = new DefaultListModel<>();
        for (String libelle : libellesPlats) {
            modelePlats.addElement(libelle);
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

        // ---------- CENTER : panier (rempli par panierModifie()) ----------
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

    // ----- Pattern Observer : appele par le Modele (Panier) -----
    @Override
    public void panierModifie(List<String> lignes, double total) {
        modelePanier.clear();
        for (String ligne : lignes) {
            modelePanier.addElement(ligne);
        }
        labelTotal.setText("Total : " + total + " EUR");
    }

    // ----- Ce que la Vue offre au Controleur -----
    public void ajouterEcouteurAjouter(ActionListener l) { boutonAjouter.addActionListener(l); }
    public void ajouterEcouteurCommander(ActionListener l) { boutonCommander.addActionListener(l); }
    public void ajouterEcouteurRetour(ActionListener l) { boutonRetour.addActionListener(l); }

    /** -1 si aucun plat n'est selectionne. */
    public int getIndexPlatSelectionne() { return listePlats.getSelectedIndex(); }

    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void afficherErreur(String titre, String message) {
        JOptionPane.showMessageDialog(this, message, titre, JOptionPane.ERROR_MESSAGE);
    }
}
