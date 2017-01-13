package controleur;

import iut.algo.Console;
import iut.algo.CouleurConsole;
import metier.Traducteur;
import org.fusesource.jansi.AnsiConsole;
import util.Lecteur;
import util.donnee.Donnee;
import vue.IVue;
import vue.cui.CUI;
import vue.gui.GUI;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;
import static org.fusesource.jansi.AnsiRenderer.render;

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
                Console.couleurFont(CouleurConsole.JAUNE);
                Console.println("[↲] Suivant - [Ln + ↲] Aller à la ligne n - [B + ↲] Précédent - [S + ↲] Tout exécuter - [Q  + ↲] Quitter");
                Console.normal();

                sc = new Scanner(System.in);
                String action = sc.nextLine().toLowerCase().trim();
                if(action.equals("q")) {
                    System.exit(0);
                }
                else if(action.equals("b")) {
                    this.traducteur.setNbAction();
                    reculer(this.traducteur.getNbAction());
                    this.vue.afficher(this.lecteur.getTabLigneCode(), this.ligneCourante, this.traducteur.getAlVariable(), this.traducteur.getAlConsole());
                }
                else if(action.matches("[0-9]+")) {
                    System.out.println("MATCHES NOMBRE");
                    avancerJusqua(Integer.parseInt(action));
                    this.vue.afficher(this.lecteur.getTabLigneCode(), this.ligneCourante, this.traducteur.getAlVariable(), this.traducteur.getAlConsole());
                }
                else {
                    System.out.println("AVANCER");
                    avancer();
                }
                /*switch(sc.nextLine().toLowerCase().trim()) {
                    case "q" : System.exit(0); break;
                    case "b" :
                        reculer(this.ligneCourante--);
                        this.vue.afficher(this.lecteur.getTabLigneCode(), this.ligneCourante, this.traducteur.getAlVariable(), this.traducteur.getAlConsole());
                        break;
                    default  : avancer();             break;
                }*/

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
        this.vue.afficher(this.lecteur.getTabLigneCode(), this.ligneCourante, this.traducteur.getAlVariable(), this.traducteur.getAlConsole());
    }

    public void reculer(int nbAction) {
        this.traducteur.reinitialiserTraducteur();
        int nbActionTemp = 0;
        this.ligneCourante = 0;
        while(nbActionTemp < nbAction-1) {
            this.ligneCourante++;
            this.traducteur.traiterLigne(this.lecteur.getTabLigneCode()[this.ligneCourante], this.ligneCourante);
            nbActionTemp++;
        }
    }

    public void avancerJusqua(int ligne) {
        while (this.ligneCourante < ligne) {
            this.ligneCourante++;
            this.traducteur.traiterLigne(this.lecteur.getTabLigneCode()[this.ligneCourante], this.ligneCourante);
        }
    }

    public void afficherInfos() {
        try {
            if (Desktop.isDesktopSupported())
                Desktop.getDesktop().browse(new File("").toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLigneCourante(int nouvelleLigne) {
        this.ligneCourante = nouvelleLigne;
        this.traducteur.traiterLigne(this.lecteur.getTabLigneCode()[this.ligneCourante], this.ligneCourante);
    }

    public void quitter() {
        System.exit(-1);
    }
}
//lol