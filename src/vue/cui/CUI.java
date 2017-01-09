package vue.cui;

import controleur.Controleur;
import util.donnee.Donnee;
import vue.IVue;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe qui gère l'affichage en mode console (Console User Interface)
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class CUI implements IVue {
    /**
     * ArrayList contenant les variables
     *
     * @see CUI#setAlTraceVariables(ArrayList)
     */
    private ArrayList<Donnee> alTraceVariables;

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

        this.alTraceVariables = new ArrayList<>();
    }

    /**
     * Permet d'initialiser les variables
     *
     * @param alTraceVariables ArrayList contenant les variables
     */
    @Override
    public void setAlTraceVariables(ArrayList<Donnee> alTraceVariables) {
        this.alTraceVariables = alTraceVariables;
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
     * @return L'affichage créée
     */
    @Override
    public String afficher(String[] tabLigneCode, Integer nLigne) {
        String tremas1 = new String(new char[10]).replace("\0", "¨");
        String tremas2 = new String(new char[87]).replace("\0", "¨");
        String tremas3 = new String(new char[48]).replace("\0", "¨");
        String sRet = String.format(tremas1 + "%78s" + tremas1 + "¨\n", " ");
        sRet += String.format("|  CODE  |%78s| DONNEES |\n", " ");
        sRet += tremas2 + " " + tremas3 + "\n";

        ArrayList<String> alString = new ArrayList<>();
        this.formaterCode(tabLigneCode, nLigne, alString);

        for (String s : alString)
            sRet += s + "\n";


        sRet += tremas2;

        return sRet;
    }

    /**
     * Permet de formater le code afin de mieux l'interpreter
     *
     * @param nLigne   Numéro de la ligne actuel
     * @param alString ArrayList de toutes les lignes du pseudo-code
     */
    private void formaterCode(String[] tabLigneCode, Integer nLigne, ArrayList<String> alString) {
        String sTemp;
        for (int i = 0; i < tabLigneCode.length; i++) {
            sTemp = tabLigneCode[i];
            sTemp = sTemp.replaceAll("\t", "   ");
            sTemp = sTemp.replaceAll("◄—", "<-");

            alString.add(String.format("| %2d %-80s | ", i, colorerCode(i, nLigne, sTemp)));
        }

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
     *
     */
    @Override
    public void majIhm() {

    }
}