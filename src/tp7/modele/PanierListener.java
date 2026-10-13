package tp7.modele;

import java.util.List;

/**
 * TP7 (MVC) : interface "observateur" du panier (pattern Observer).
 * Le Modele (Panier) previent ses observateurs a chaque changement,
 * SANS connaitre la classe concrete qui l'observe (ici la Vue FenetreMenu).
 * => le Modele ne depend d'aucune classe Swing.
 */
public interface PanierListener {

    /**
     * Appelee par le Panier apres chaque modification.
     * @param lignes texte de chaque plat du panier ("1 x Margherita - 11.0 EUR")
     * @param total  montant total du panier
     */
    void panierModifie(List<String> lignes, double total);
}
