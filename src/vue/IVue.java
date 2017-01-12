package vue;

import controleur.Controleur;
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
    String[] motsCles = new String[] {"si", "alors", "sinon", "fsi", "ecrire", "lire", "tantque", "faire", "ftq"};

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
     */
    void afficher(String[] tabLigneCode, Integer nLigne, ArrayList<Donnee> alDonnees, ArrayList<String> alConsole);
    void afficher(String[] tabLigneCode, Integer nLigne, ArrayList<Donnee> alDonnees, ArrayList<String> alConsole, String couleur);

    Controleur getControleur();

    String lire();
}
