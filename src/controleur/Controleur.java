package controleur;

import metier.Traducteur;
import util.Lecteur;
import util.donnee.Donnee;
import vue.IVue;
import vue.cui.CUI;
import vue.gui.GUI;

import java.awt.*;
import java.io.File;
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
    private Lecteur lecteur;
    private Traducteur traducteur;

    private int ligneCourante;

    public Controleur(boolean modeGraphique) {
        this.vue = modeGraphique ? new GUI(this) : new CUI(this);
        this.lecteur = new Lecteur(this.vue.ouvrirFichier());
        this.traducteur = new Traducteur(this.vue);

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
        new Controleur(Controleur.demanderModeGraphique()).lancer();
    }

    private void lancer() {
        this.traducteur.traiterLigne(this.lecteur.getTabLigneCode()[this.ligneCourante], this.ligneCourante);

        this.vue.afficher(this.lecteur.getTabLigneCode(), this.ligneCourante, new ArrayList<Donnee>(), new ArrayList<String>());

        if (this.vue instanceof CUI) {
            Scanner sc;
            do {
                System.out.println("\u001B[33m[↲] Suivant - [Ln + ↲] Aller à la ligne n - [B + ↲] Précédent - [S + ↲] Tout exécuter - [Q  + ↲] Quitter\u001B[0m");
                sc = new Scanner(System.in);
                switch(sc.nextLine().toLowerCase().trim()) {
                    case "q" : System.exit(0); break;
                    case "b" : reculer();             break;
                    default  : avancer();             break;
                }

            } while (this.ligneCourante < this.lecteur.getTabLigneCode().length -1);
        }

        //avancer();
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
    public void avancer() {
        this.ligneCourante++;

        this.traducteur.traiterLigne(this.lecteur.getTabLigneCode()[this.ligneCourante], this.ligneCourante);
        this.vue.afficher(this.lecteur.getTabLigneCode(), this.ligneCourante, this.traducteur.getAlEtatVariable().get(this.ligneCourante), this.traducteur.getAlConsole());
    }

    public void reculer() {
        this.ligneCourante--;

        this.vue.afficher(this.lecteur.getTabLigneCode(), this.ligneCourante, this.traducteur.getAlEtatVariable().get(this.ligneCourante), this.traducteur.getAlConsole());

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