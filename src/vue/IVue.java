package vue;

import util.donnee.Donnee;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Interface qui gère les deux modes de vue (CUI et GUI)
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public interface IVue {
    String[] motsCles = new String[]{"si",
                                     "alors",
                                     "sinon",
                                     "fsi",
                                     "ecrire",
                                     "lire",
                                     "tantque",
                                     "ftq"};

    String ouvrirFichier();
    void afficherMessage(String message);
    String afficher(Integer nLigne);
    void setNumLignes(HashMap<Integer, String> numLignes);
    void setAlTraceVariables(ArrayList<Donnee> alTraceVariables);
    void majIhm();
}
