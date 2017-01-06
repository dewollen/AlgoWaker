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

    String[] test = new String[10];
    private HashMap<Integer, String> numLignes;

    private ArrayList<Donnee>        alTraceVariables;

    public CUI() {
        this.alTraceVariables = new ArrayList<Donnee>();
    }

    @Override
    public void setNumLignes(HashMap<Integer, String> numLignes) {
        this.numLignes = numLignes;
    }

    @Override
    public String toString() {
        return this.afficher(null);
    }

    @Override
    public void setAlTraceVariables(ArrayList<Donnee> alTraceVariables) {
        this.alTraceVariables = alTraceVariables;
    }

    @Override
    public String ouvrirFichier() {
        System.out.println("Quel fichier voulez-vous ouvrir ?");
        Scanner scClavier = new Scanner(System.in);

        return scClavier.nextLine();
    }

    @Override
    public void afficherMessage(String message) {
    }

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


            for(String motCle : IVue.motsCles) {
                if (nLigne != null && nLigne.equals(cle))
                    sRet = sRet.replace(motCle, "\u001B[45m\u001B[34m" + motCle + "\u001B[0m\u001B[45m");
                else
                    sRet = sRet.replace(motCle, "\u001B[34m" + motCle + "\u001B[0m");
            }

            return sRet;
    }

    @Override
    public void majIhm() {

    }
}