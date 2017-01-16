package vue.cui;

import bsh.EvalError;
import controleur.Controleur;
import exception.ConstantChangeException;
import iut.algo.Console;
import iut.algo.CouleurConsole;
import metier.Variable;
import metier.traducteur.Traducteur;
import scruter.Observeur;
import vue.IVue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


/**
 * Classe qui gère l'affichage en mode console (Console User Interface)
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class CUI implements IVue, Observeur {
    /**
     * Controleur qui fait la relation entre la vue et
     * le modèle du programme
     */
    private Controleur controleur;

    private Traducteur traducteur;

    /**
     * Constructeur de la classe CUI
     *
     * @param controleur qui gère la vue
     */
    public CUI(Controleur controleur, Traducteur traducteur) {
        this.controleur = controleur;
        this.traducteur = traducteur;
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

    @Override
    public String saisieUtilisateur(String message) {
        return null;
    }

    @Override
    public void lancer() throws ConstantChangeException, EvalError {
        this.majIHM(0, null);
        Scanner sc;

        while (true) {
            System.out.println("Entrez une action :");
            sc = new Scanner(System.in);

            controleur.controleurAction(sc.nextLine());
        }
    }

    /**
     * Permet de formater le code afin de mieux l'interpreter
     *
     * @param tabLigneCode tableau contenant toutes les lignes du pseudo-code
     * @param nLigne       Numéro de la ligne actuel
     */
    private ArrayList<String> formaterCode(String[] tabLigneCode, Integer nLigne, String couleurFond) {
        ArrayList<String> alString = new ArrayList<>();

        String sTemp;
        int debut = (nLigne >= 19 ? nLigne - 19 : 0);
        debut = (debut + 40 > tabLigneCode.length ? tabLigneCode.length - 40 : debut);
        for (int i = debut; i < 40 + debut && i < tabLigneCode.length; i++) {
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

        String ccFond = CouleurConsole.MAUVE.getFond();
        for(CouleurConsole c : CouleurConsole.values())
            if (couleurFond != null && couleurFond.equals(c.name()))
                ccFond = c.getFond();

            if (nLigne != null && nLigne.equals(numeroLigne))
                sRet = ccFond + sRet;



        for (String motCle : IVue.motsCles) {
            if (nLigne != null && nLigne.equals(numeroLigne)) {
                sRet = sRet.replaceAll("[\\W]" + motCle + "[\\W]", " " + CouleurConsole.BLEU.getFont() + ccFond + motCle + Console.getCodeNormal() + ccFond + " ");
            } else {
                sRet = sRet.replaceAll("[\\W]" + motCle + "[\\W]", " " + CouleurConsole.BLEU.getFont() + motCle + Console.getCodeNormal() + " ");
            }
        }

        sRet = sRet.replaceAll("//*", CouleurConsole.JAUNE.getFont() + "//");


        return String.format("| %2d " + sRet + Console.getCodeNormal() + " |", numeroLigne);
    }

    /**
     * Permet de formater l'affichage des données SUIVIES et de le stocker dans le paramètre alString
     */
    private ArrayList<String> formaterDonnees(ArrayList<Variable> alDonnees) {
        ArrayList<String> alString = new ArrayList<String>();

        alString.add(String.format("| %-8s | %-9s | %-21s |", "NOM", "TYPE", "VALEUR"));

        for (Variable v : alDonnees)
            alString.add(String.format("| %-8s | %-9s | %-21s |", v.getNom(), v.getType(), v.getValeur()));

        alString.add("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");

        return alString;
    }

    private String formaterConsole(ArrayList<String[]> alConsole) {
        String sRet = "";
        for (int i = 4; i > 0; i--) {
            if (alConsole.size() - i >= 0)
                sRet += String.format("| %-83s |\n", alConsole.get(alConsole.size() - i)[0]);
            else
                sRet += String.format("| %-83s |\n", " ");

        }

        return sRet;
    }

    @Override
    public void majIHM(Integer ligne, String couleur) {
        Console.effacerEcran();
        String tremas1 = new String(new char[10]).replace("\0", "¨");
        String tremas2 = new String(new char[87]).replace("\0", "¨");
        String tremas3 = new String(new char[48]).replace("\0", "¨");
        Console.println(String.format(tremas1 + "%78s" + tremas1 + "¨", " "));
        Console.println(String.format("|  CODE  |%78s| DONNEES |", " "));
        Console.println(tremas2 + " " + tremas3);

        ArrayList<String> alStringCode = this.formaterCode(this.traducteur.getPseudoCode(), ligne, couleur);
        ArrayList<String> alStringDonnees = this.formaterDonnees(this.traducteur.getVariablesATracer());


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
        Console.println(this.formaterConsole(this.traducteur.getConsole()));
        Console.println(tremas2);
    }

    @Override
    public String saisieUtilisateur() {
        String valeur = "";

        while(valeur.equals("")) {
            valeur = new Scanner(System.in).nextLine();
        }

        return valeur;
    }
}