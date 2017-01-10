package vue.cui;

import controleur.Controleur;
import util.donnee.Donnee;
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
        String tremas1 = new String(new char[10]).replace("\0", "¨");
        String tremas2 = new String(new char[87]).replace("\0", "¨");
        String tremas3 = new String(new char[48]).replace("\0", "¨");
        String sRet = String.format(tremas1 + "%78s" + tremas1 + "¨\n", " ");
        sRet += String.format("|  CODE  |%78s| DONNEES |\n", " ");
        sRet += tremas2 + " " + tremas3 + "\n";

        ArrayList<String> alStringCode    = this.formaterCode(tabLigneCode, nLigne);
        ArrayList<String> alStringDonnees = this.formaterDonnees(alDonnees);


        Iterator<String> itCode = alStringCode.iterator();
        Iterator<String> itDonnees = alStringDonnees.iterator();

        while (itCode.hasNext() || itDonnees.hasNext()) {
            if (itCode.hasNext())
                sRet += itCode.next();
            else
                sRet += new String(new char[87]).replace("\0", " ");

            if (itDonnees.hasNext())
                sRet += itDonnees.next() + "\n";
            else
                sRet += "\n";
        }

        sRet += tremas2 + "\n\n";
        sRet += tremas1 + "\n| CONSOLE |\n" + tremas2 + "\n";
        sRet += this.formaterConsole(alConsole);
        sRet += tremas2;

        System.out.println(sRet);
    }

    /**
     * Permet de formater le code afin de mieux l'interpreter
     *
     * @param tabLigneCode tableau contenant toutes les lignes du pseudo-code
     * @param nLigne   Numéro de la ligne actuel
     */
    private ArrayList<String> formaterCode(String[] tabLigneCode, Integer nLigne) {
        ArrayList<String> alString = new ArrayList<String>();

        String sTemp;
        for (int i = 0; i < tabLigneCode.length; i++) {
            sTemp = tabLigneCode[i];
            sTemp = sTemp.replaceAll("\t", "   ");
            sTemp = sTemp.replaceAll("◄—", "<-");

            alString.add(String.format("| %2d %-80s | ", i, colorerCode(i, nLigne, sTemp)));
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
    private String colorerCode(int numeroLigne, Integer nLigne, String ligne) {
        String sRet;

        if (ligne.equals(""))
            sRet = " ";
        else
            sRet = ligne;

        if (nLigne != null && nLigne.equals(numeroLigne))
            sRet = "\u001B[45m" + String.format("%-80s", sRet) + "\u001B[0m";
        else
            sRet = String.format("%-80s", sRet);


        for (String[] motCle : IVue.motsCles) {
            if (nLigne != null && nLigne.equals(numeroLigne))
                sRet = sRet.replace(motCle[0], "\u001B[45m" + motCle[1] + motCle[0] + "\u001B[0m\u001B[45m");
            else
                sRet = sRet.replace(motCle[0], motCle[1] + motCle[0] + "\u001B[0m");
        }

        return sRet;
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
        return new Scanner(System.in).nextLine();
    }
}