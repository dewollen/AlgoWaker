package controleur;
import vue.cui.CUI;

import util.donnee.Donnee;
import util.Lecteur;

import java.util.ArrayList;

/**
 * Classe qui contrôle et fait le lien entre le traducteur et la partie graphique
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class Controleur {
    private Lecteur           lecteur;
    private ArrayList<Donnee> donnees;

    public static void main(String[] args) {
        CUI cui = new CUI();
        System.out.println(cui.toString());
    }
}