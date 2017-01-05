package vue.gui;

import vue.IVue;

import java.util.HashMap;

/**
 * Classe qui gère l'affichage en mode graphique (Graphical User Interface)
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class GUI implements IVue {

    public GUI() {
        System.out.println("Hello world !");
    }

    @Override
    public String ouvrirFichier() {
        return null;
    }

    @Override
    public void afficherMessage(String message) {

    }

    @Override
    public void setNumLignes(HashMap<Integer, String> numLignes) {

    }
}
