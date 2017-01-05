package controleur;
import vue.IVue;
import vue.cui.CUI;

import util.donnee.Donnee;
import util.Lecteur;
import vue.gui.GUI;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe qui contrôle et fait le lien entre le traducteur et la partie graphique
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class Controleur {
    private IVue vue;
    private Lecteur           lecteur;
    private ArrayList<Donnee> donnees;

    public Controleur(IVue vue) {
        this.vue     = vue;
        this.lecteur = null;
        this.donnees = null;
    }

    public static void main(String[] args) {
        Controleur controleur;
        System.out.println("Voulez-vous lancer le programme en mode Graphique ? (O/N)");
        Scanner scClavier = new Scanner(System.in);

        if(scClavier.hasNext() && scClavier.next().equals("O"))
            controleur = new Controleur(new GUI());
        else
            controleur = new Controleur(new CUI());

        controleur.ouvrirFichier();

        //CUI cui = new CUI();
        //System.out.println(cui.toString());
    }

    private void ouvrirFichier() {
        this.vue.ouvrirFichier();
    }

    private void selectionnerVariables() {
        this.vue.afficherMessage("Quelles variables voulez-vous suivre via la trace des variables ?");

    }
}