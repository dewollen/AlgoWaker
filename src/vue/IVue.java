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
    String[][] motsCles = new String[][]{{"si"     , "\u001B[34m"},
                                         {"alors"  , "\u001B[34m"},
                                         {"sinon"  , "\u001B[34m"},
                                         {"fsi"    , "\u001B[34m"},
                                         {"ecrire" , "\u001B[34m"},
                                         {"lire"   , "\u001B[33m"},
                                         {"tantque", "\u001B[34m"},
                                         {"ftq"    , "\u001B[34m"}};

    String ouvrirFichier();
    void afficherMessage(String message);
    String afficher(Integer nLigne);
    void setNumLignes(HashMap<Integer, String> numLignes);
    void setAlTraceVariables(ArrayList<Donnee> alTraceVariables);
    void majIhm();
}
