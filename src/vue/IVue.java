package vue;

import util.donnee.Donnee;

import java.util.ArrayList;

/**
 * Interface qui gère les deux modes de vue (CUI et GUI)
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public interface IVue {
    /**
     * Tableau de toutes les conditions et interaction avec l'utilisateur possible ainsi que leur couleur
     */
    String[][] motsCles = new String[][]{{"si", "\u001B[34m"},
            {"alors", "\u001B[34m"},
            {"sinon", "\u001B[34m"},
            {"fsi", "\u001B[34m"},
            {"ecrire", "\u001B[34m"},
            {"lire", "\u001B[33m"},
            {"tantque", "\u001B[34m"},
            {"ftq", "\u001B[34m"}};

    /**
     * Permet d'initialiser le fichier que l'utilisateur choisi
     *
     * @return le fichier que l'on veut ouvrir
     */
    String ouvrirFichier();

    /**
     * @param message
     */
    void afficherMessage(String message);

    /**
     * Permet d'afficher les lignes du pseudo-code ainsi que les variables
     *
     * @param tabLigneCode le pseudo-code à lire
     * @param nLigne       Numéro de la ligne actuel du pseudo-code
     * @return La ligne créée
     */
    String afficher(String[] tabLigneCode, Integer nLigne);

    /**
     * Permet d'initialiser l'ArrayList des variables
     *
     * @param alTraceVariables ArrayList comportant les variables
     */
    void setAlTraceVariables(ArrayList<Donnee> alTraceVariables);

    /**
     * Permet de mettre à jour l'affichage
     */
    void majIhm();
}
