package controleur;

import bsh.EvalError;
import exception.CodeFormatException;
import exception.ConstantChangeException;
import metier.traducteur.Traducteur;
import scruter.Observeur;
import vue.IVue;
import vue.cui.CUI;
import vue.gui.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * Classe qui contrôle et fait le lien entre le traducteur et la partie graphique
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class Controleur implements ActionListener {
    private IVue vue;
    private Traducteur traducteur;

    private Controleur(String fichier, boolean estGraphique) {
        try {
            this.traducteur = new Traducteur(fichier);
        } catch (ConstantChangeException e) {
            e.printStackTrace();
        }

        this.vue = estGraphique ? new GUI(this.traducteur) : new CUI(this, this.traducteur);

        this.traducteur.ajouterObserveur((Observeur) this.vue);

        if (estGraphique) {
            /*
            Ici on cast en GUI pour avoir accès aux méthodes spécifiques à la classe GUI
            (qui sont normalement masquées par l'interface IVue)
             */
            GUI graphique = (GUI) this.vue;
            graphique.setActionsRaccourcis(this);
            graphique.setActionsClavier(new GestionClavier());
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("\n\n\t\tVeuillez spécifier un unique fichier en paramètre\n\n");
        } else {
            Controleur controleur = new Controleur(args[0], Controleur.choisirModeInteration());
            try {
                controleur.vue.lancer();
            } catch (ConstantChangeException e) {
                e.printStackTrace();
            } catch (EvalError evalError) {
                evalError.printStackTrace();
            }
        }
    }

    private static boolean choisirModeInteration() {
        System.out.println("Voulez-vous lancer le programme en mode graphique ? (O/N)");

        String sReponse;
        Scanner sc;

        do {
            sc = new Scanner(System.in);
            sReponse = sc.next().toUpperCase();
        } while (!(sReponse.equals("O") || sReponse.equals("N")));

        return sReponse.equals("O");
    }

    private void changerFichier() {
        String nomNouveauFichier = this.vue.ouvrirFichier();

        File fichier = new File(nomNouveauFichier);

        if (fichier.exists())
            try {
                this.traducteur = new Traducteur(nomNouveauFichier);
            } catch (ConstantChangeException e) {
                e.printStackTrace();
            }
        else
            System.err.println("Le fichier spécifié n'existe pas : ouverture annulée.");
    }

    private void afficherInfos() {
        try {
            if (Desktop.isDesktopSupported())
                Desktop.getDesktop().browse(new File("").toURI());
        } catch (Exception e) {
            this.vue.afficherMessage("Ouverture navigateur non supportée : tentative annulée.");
        }
    }

    private void quitter() {
        System.exit(0);
    }

    public void avancerVersLigne(int ligne) {
        /*while (this.ligneCourante < ligne) {
            this.ligneCourante++;
            this.traducteur.traduire(this.lecteur.getTabLigneCode()[this.ligneCourante], this.ligneCourante);
        }*/
    }


    /**
     * Actions pour le mode CUI
     *
     * @param action
     * @return
     */
    public void controleurAction(String action) throws EvalError, ConstantChangeException {
        action = action.trim().toUpperCase();

        switch(action) {
            case ""  : this.traducteur.avancer(); break;
            case "B" : this.traducteur.reculer(); break;
            case "Q" : this.quitter();            break;
            case "O" : this.changerFichier();     break;
        }

        if(action.matches("L[0-9]+")) avancerVersLigne(Integer.parseInt(action.substring(1)));
    }

    /**
     * Actions pour le mode GUI
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "ouvrir":
                this.changerFichier();
                break;
            case "quitter":
                this.quitter();
                break;
            case "infos":
                afficherInfos();
                break;
        }
    }

    private class GestionClavier extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
                try {
                    traducteur.avancer();
                } catch (ConstantChangeException e1) {
                    System.err.println(e1.getMessage());
                } catch (EvalError evalError) {
                    try {
                        throw new CodeFormatException(traducteur.getNumLigneCourante(), traducteur.getLigneCourante());
                    } catch (CodeFormatException e1) {
                        System.err.println(e1.getMessage());
                    }
                }
        }
    }
}