package vue.gui;

import util.donnee.Donnee;
import vue.IVue;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe qui gère l'affichage en mode graphique (Graphical User Interface)
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class GUI implements IVue {

    private HashMap<Integer, String> numLignes;

    @Override
    public void setNumLignes(HashMap<Integer, String> numLignes) {
        this.numLignes = numLignes;
    }

    @Override
    public String ouvrirFichier() {
        return null;
    }

    @Override
    public void afficherMessage(String message) {

    }

    @Override
    public void setAlTraceVariables(ArrayList<Donnee> alTraceVariables) {

    }
}
