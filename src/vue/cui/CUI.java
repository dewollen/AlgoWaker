package vue.cui;

import controleur.Controleur;
import iut.algo.Console;
import iut.algo.CouleurConsole;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import org.fusesource.jansi.AnsiRenderWriter;
import util.donnee.Donnee;
import vue.IVue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.AnsiRenderer.render;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe qui gère l'affichage en mode console (Console User Interface)
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class CUI implements IVue {
    /**
     * Controleur qui fait la relation entre la vue et
     * le modèle du programme
     */
    private Controleur controleur;

    /**
     * Constructeur de la classe CUI
     *
     * @param controleur qui gère la vue
     */
    public CUI(Controleur controleur) {
        this.controleur = controleur;
    }

    /**
     * Permet à l'utilisateur de choisir le fichier qu'il veut lire
     *
     * @return La première ligne du fichier
     */
    @Override
    public String ouvrirFichier() {
        System.out.println("Quel fichier voulez-vous ouvrir ?");
        Scanner scClavier = new Scanner(System.in);

        return scClavier.nextLine();
    }

    /**
     * @param message affiche un message important je lspr
     */
    @Override
    public void afficherMessage(String message) {
        System.out.println(">>>>>>> " + message + " <<<<<<<");
    }

    /**
     * Permet de créer l'affichage console
     *
     * @param tabLigneCode le pseudo-code à lire
     * @param nLigne       Numéro de la ligne actuel du pseudo-code
     */
    @Override
    public void afficher(String[] tabLigneCode, Integer nLigne, ArrayList<Donnee> alDonnees, ArrayList<String> alConsole) {
        this.afficher(tabLigneCode, nLigne, alDonnees, alConsole, "magenta");
    }

    @Override
    public void afficher(String[] tabLigneCode, Integer nLigne, ArrayList<Donnee> alDonnees, ArrayList<String> alConsole, String couleurFond) {
        Console.effacerEcran();
        String tremas1 = new String(new char[10]).replace("\0", "¨");
        String tremas2 = new String(new char[87]).replace("\0", "¨");
        String tremas3 = new String(new char[48]).replace("\0", "¨");
        Console.println(String.format(tremas1 + "%78s" + tremas1 + "¨", " "));
        Console.println(String.format("|  CODE  |%78s| DONNEES |", " "));
        Console.println(tremas2 + " " + tremas3);

        ArrayList<String> alStringCode    = this.formaterCode(tabLigneCode, nLigne, couleurFond);
        ArrayList<String> alStringDonnees = this.formaterDonnees(alDonnees);


        Iterator<String> itCode = alStringCode.iterator();
        Iterator<String> itDonnees = alStringDonnees.iterator();

        while (itCode.hasNext() || itDonnees.hasNext()) {
            if (itCode.hasNext())
                Console.print(itCode.next());
            else
                Console.print(new String(new char[87]).replace("\0", " "));

            if (itDonnees.hasNext())
                Console.println(" " + itDonnees.next());
            else
                Console.println();
        }

        Console.println(tremas2 + "\n\n");
        Console.println(tremas1 + "\n| CONSOLE |\n" + tremas2);
        Console.println(this.formaterConsole(alConsole));
        Console.println(tremas2);
    }

    @Override
    public Controleur getControleur() {
        return this.controleur;
    }

    /**
     * Permet de formater le code afin de mieux l'interpreter
     *
     * @param tabLigneCode tableau contenant toutes les lignes du pseudo-code
     * @param nLigne   Numéro de la ligne actuel
     */
    private ArrayList<String> formaterCode(String[] tabLigneCode, Integer nLigne, String couleurFond) {
        ArrayList<String> alString = new ArrayList<String>();

        String sTemp;
        int debut = (nLigne >= 19 ? nLigne - 19 : 0);
            debut = (debut + 40 > tabLigneCode.length ? tabLigneCode.length - 40 : debut);
        for(int i = debut; i < 40 + debut && i < tabLigneCode.length; i++) {
            sTemp = tabLigneCode[i];
            sTemp = sTemp.replaceAll("\t", "   ");
            sTemp = sTemp.replaceAll("◄—", "<-");

            alString.add(colorerCode(i, nLigne, sTemp, couleurFond));
        }

        return alString;
    }

    /**
     * Permet de colorier la ligne où est l'utilisateur
     *
     * @param numeroLigne Numéro de la ligne
     * @param nLigne      Numéro de la ligne actuelle
     * @param ligne       Ligne actuel
     * @return La ligne colorée
     */
    private String colorerCode(int numeroLigne, Integer nLigne, String ligne, String couleurFond) {
        String sRet = String.format("%-80s", ligne);

        if(nLigne != null && nLigne.equals(numeroLigne))
            sRet = CouleurConsole.MAUVE.getFond() + sRet;


        for(String motCle : IVue.motsCles) {
            if (nLigne != null && nLigne.equals(numeroLigne)) {
                sRet = sRet.replaceAll("[\\W]" + motCle + "[\\W]", " " + CouleurConsole.BLEU.getFont() + motCle + Console.getCodeNormal() + " " + CouleurConsole.MAUVE.getFond());
            }
            else {
                sRet = sRet.replaceAll("[\\W]" + motCle + "[\\W]", " " + CouleurConsole.BLEU.getFont() + motCle + Console.getCodeNormal() + " ");
            }
        }

        sRet = sRet.replaceAll("//*", CouleurConsole.JAUNE.getFont() +  "//");


        return String.format("| %2d " + sRet + Console.getCodeNormal() + " |", numeroLigne);
    }

    /**
     * Permet de formater l'affichage des données SUIVIES et de le stocker dans le paramètre alString
     */
    private ArrayList<String> formaterDonnees(ArrayList<Donnee> alDonnees) {
        ArrayList<String> alString = new ArrayList<String>();
        ArrayList<Donnee> alSuivis = new ArrayList<Donnee>();

        for (Donnee d : alDonnees)
            if (d.getSuivi())
                alSuivis.add(d);

        alString.add(String.format("| %-8s | %-9s | %-21s |", "NOM", "TYPE", "VALEUR"));
        for (Donnee d : alSuivis)
            alString.add(String.format("| %-8s | %-9s | %-21s |", d.getNom(), d.getType(), d.getValeur()));

        alString.add("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");

        return alString;
    }

    private String formaterConsole(ArrayList<String> alConsole) {
        String sRet = "";
        for(int i = 4; i > 0; i--) {
            if(alConsole.size() - i >= 0)
                sRet += String.format("| %-83s |\n", alConsole.get(alConsole.size() - i));
            else
                sRet += String.format("| %-83s |\n", " ");

        }

        return sRet;
    }

    @Override
    public String lire() {
        String sRet = "";
        Scanner sc = new Scanner(System.in);
        sRet = sc.nextLine();

        while(sRet.equals("")){
            sc = new Scanner(System.in);
            sRet = sc.nextLine();
        }

        return sRet;
    }
}