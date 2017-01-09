package vue.cui;

import util.donnee.Donnee;
import vue.IVue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Classe qui gère l'affichage en mode console (Console User Interface)
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class CUI implements IVue {

    /**
     * HashMap contenant les lignes du pseudo-code
     * @see CUI#setNumLignes(HashMap)
     * @see CUI#colorerCode(Integer, Integer, String)
     */
    private HashMap<Integer, String> numLignes;

    /**
     * ArrayList contenant les variables
     * @see CUI#setAlTraceVariables(ArrayList)
     */
    private ArrayList<Donnee>        alTraceVariables;

    /**
     * Constructeur de la classe CUI
     */
    public CUI() {
        this.alTraceVariables = new ArrayList<Donnee>();
    }

    /**
     * Permet d'initialiser la HashMap
     * @param numLignes HashMap contenant toutes les lignes du pseudo-code
     */
    @Override
    public void setNumLignes(HashMap<Integer, String> numLignes) {
        this.numLignes = numLignes;
    }

    /**
     * Appelle la méthode afficher(Integer)
     * @return La méthode afficher(Integer)
     */
    @Override
    public String toString() {
        return this.afficher(null);
    }

    /**
     * Permet d'initialiser les variables
     * @param alTraceVariables ArrayList contenant les variables
     */
    @Override
    public void setAlTraceVariables(ArrayList<Donnee> alTraceVariables) {
        this.alTraceVariables = alTraceVariables;
    }

    /**
     * Permet à l'utilisateur de choisir le fichier qu'il veut lire
     * @return La première ligne du fichier
     */
    @Override
    public String ouvrirFichier() {
        System.out.println("Quel fichier voulez-vous ouvrir ?");
        Scanner scClavier = new Scanner(System.in);

        return scClavier.nextLine();
    }

    /**
     *
     * @param message
     */
    @Override
    public void afficherMessage(String message) {
    }

    /**
     * Permet de créer l'affichage console
     * @param nLigne Numéro de la ligne actuel du pseudo-code
     * @return L'affichage créée
     */
    @Override
    public String afficher(Integer nLigne) {
        String tremas1 = new String(new char[10]).replace("\0", "¨");
        String tremas2 = new String(new char[87]).replace("\0", "¨");
        String tremas3 = new String(new char[48]).replace("\0", "¨");
        String sRet    = String.format(tremas1 + "%78s" + tremas1 + "¨\n", " ");
        sRet          += String.format("|  CODE  |%78s| DONNEES |\n"    , " ");
        sRet          += tremas2 + " " + tremas3 + "\n";

        ArrayList<String> alString = new ArrayList<String>();
        this.formaterCode(nLigne, alString);

        for(String s : alString)
            sRet += s + "\n";


        sRet += tremas2;

        return sRet;
    }

    /**
     * Permet de formater le code afin de mieux l'interpreter
     * @param nLigne Numéro de la ligne actuel
     * @param alString ArrayList de toutes les lignes du pseudo-code
     */
    private void formaterCode(Integer nLigne, ArrayList<String> alString) {
        String sTemp;
        for(Integer cle : this.numLignes.keySet()) {
            sTemp = this.numLignes.get(cle);
            sTemp = sTemp.replaceAll("\t", "   ");
            sTemp = sTemp.replaceAll("◄—", "<-");

            alString.add(String.format("| %2d %-80s | ", cle.intValue(),
                    colorerCode(cle, nLigne, sTemp)));
        }
    }

    /**
     * Permet de colorier la ligne où est l'utilisateur
     * @param cle Clé de l'HashMap
     * @param nLigne Numéro de la ligne actuel
     * @param ligne Ligne actuel
     * @return La ligne colorée
     */
    private String colorerCode(Integer cle, Integer nLigne, String ligne) {
            String sRet;

            if(ligne.equals(""))
                sRet = " ";
            else
                sRet = ligne;

            if (nLigne != null && nLigne.equals(cle))
                sRet = "\u001B[45m" + String.format("%-80s", sRet) + "\u001B[0m";
            else
                sRet = String.format("%-80s", sRet);


            for(String[] motCle : IVue.motsCles) {
                if (nLigne != null && nLigne.equals(cle))
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