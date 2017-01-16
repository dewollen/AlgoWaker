package vue;

import bsh.EvalError;
import exception.ConstantChangeException;

/**
 * Interface qui gère les deux modes de vue (CUI et GUI)
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public interface IVue {
    /**
     * Tableau de toutes les conditions et interaction avec l'utilisateur possible
     */
    String[] motsCles = new String[]{"si", "alors", "sinon", "fsi", "ecrire", "lire", "tantque", "faire", "ftq"};

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

    void lancer() throws ConstantChangeException, EvalError;
}