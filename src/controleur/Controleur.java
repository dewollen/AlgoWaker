package controleur;
import util.donnee.Entier;
import vue.IVue;
import vue.cui.CUI;

import util.donnee.Donnee;
import util.Lecteur;

import java.util.ArrayList;
import java.util.Iterator;
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
        /*System.out.println("Voulez-vous lancer le programme en mode Graphique ? (O/N)");
        Scanner scClavier = new Scanner(System.in);

        if(scClavier.hasNext() && scClavier.next().toUpperCase().equals("O"))
            controleur = new Controleur(new GUI());
        else*/
        controleur = new Controleur(new CUI());

        controleur.ouvrirFichier();

        controleur.vue.setNumLignes(controleur.lecteur.getNumLignes());

        controleur.donnees = new ArrayList<Donnee>();
        controleur.donnees.add(new Entier("x", true, false));
        controleur.donnees.get(0).setValeur("5");
        controleur.donnees.add(new Entier("y", true, false));
        controleur.donnees.get(1).setValeur("7");

        controleur.vue.setAlTraceVariables(controleur.donnees);


        controleur.commencerLecture();
    }

    private void ouvrirFichier() {
        this.lecteur = new Lecteur(this.vue.ouvrirFichier());
    }

    private void selectionnerVariables() {
        this.vue.afficherMessage("Quelles variables voulez-vous suivre via la trace des variables ?");

    }

    private void commencerLecture() {
        Iterator<Integer> i = this.lecteur.getNumLignes().keySet().iterator();

        System.out.println(this.vue.afficher(i.next()));

        while(i.hasNext()) {
            System.out.print("Action : ");
            Scanner scClavier = new Scanner(System.in);

            if(scClavier.nextLine().equals(""))
                System.out.println(this.vue.afficher(i.next()));
        }
    }
}