package controleur;

import metier.Traducteur;
import util.Lecteur;
import vue.IVue;
import vue.cui.CUI;
import vue.gui.GUI;

import java.awt.*;
import java.io.File;
import java.util.Scanner;

/**
 * Classe qui contrôle et fait le lien entre le traducteur et la partie graphique
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class Controleur {
    private IVue vue;
    private Lecteur lecteur;
    private Traducteur traducteur;

    private int ligneCourante;

    public Controleur(boolean modeGraphique) {
        this.vue = modeGraphique ? new GUI(this) : new CUI(this);
        this.lecteur = new Lecteur(this.vue.ouvrirFichier());
        this.traducteur = new Traducteur();

        this.ligneCourante = 0;

        this.initVue();
    }

    private void initVue() { // la vue n'a pas besoin d'alpseudo code, on la lui passe dans afficher()
        // Paramètres de la vue
        // this.vue.setAlTraceVariables(this.traducteur.getAlVariable());
        //this.vue.setAlPseudoCode(this.lecteur.getAlPseudoCode());
        // this.vue.setAlTraceVariables(this.donnees);
    }

    public static void main(String[] args) {
        new Controleur(Controleur.demanderModeGraphique()).avancer();
    }

    private static boolean demanderModeGraphique() {
        System.out.println("Voulez-vous lancer le programme en mode Graphique ? (O/N)");
        Scanner scClavier = new Scanner(System.in);

        return scClavier.hasNext() && scClavier.next().toUpperCase().equals("O");
    }

    public void ouvrirFichier() {
        this.lecteur = new Lecteur(this.vue.ouvrirFichier());
    }

    /**
     * Avance dans le fichier
     */
    private void avancer() {
        String ligne = this.lecteur.getTabLigneCode()[this.ligneCourante];

        this.ligneCourante++;
    }

    private void reculer() {

    }

    public void afficherInfos() {
        try {
            if (Desktop.isDesktopSupported())
                Desktop.getDesktop().browse(new File("").toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void quitter() {
        System.exit(-1);
    }
}