package vue.cui;

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

    @Override
    public void setNumLignes(HashMap<Integer, String> numLignes) {
        this.numLignes = numLignes;
    }

    public String toString() {

        for(int i = 0; i < 10; i++)
            test[i] = Integer.toString(i);


        String tremas1 = new String(new char[10]).replace("\0", "¨");
        String tremas2 = new String(new char[87]).replace("\0", "¨");
        String tremas3 = new String(new char[48]).replace("\0", "¨");
        String sRet    = String.format(tremas1 + "%78s" + tremas1 + "¨\n", " ");
               sRet   += String.format("|  CODE  |%78s| DONNEES |\n"    , " ");
               sRet   += tremas2 + " " + tremas3 + "\n";


        ArrayList<String> alString = new ArrayList<String>();
        for(Integer cle : this.numLignes.keySet()) {
            alString.add(String.format("| %2d %-80s | ", cle.intValue(), this.numLignes.get(cle).replaceAll("\t", "   ").replaceAll("←", "<-")));
        }

        for(String s : alString) {
            sRet += s + "\n";
        }

        /*String mot = "lol";

        for(int i = 0; i < 40; i++){
            sRet += String.format("| %2d %-80s |", i, mot);
            if(i == 0)
                sRet += " |    NOM    |    TYPE    |   VALEUR            |";
            else{
                if(i < test.length){
                    sRet += String.format(" | %-10s| %-11s| %-20s|",test[i-1],test[i-1],test[i-1]);
                }
                if(i == test.length) {
                    sRet += " ";
                    for(int j = 0; j < 48; j++)
                        sRet += "¨";
                }
            }
            if(i % 2 == 0){
                mot = "eifjiefjief";
            }
            else{
                mot = "lol";
            }

            sRet += "\n";
        }

        for(int i = 0; i < 87; i++)
            sRet +="¨";


        //PARTIE CONSOLE !!
        sRet += "\n¨¨¨¨¨¨¨¨¨¨¨\n";
        sRet += "| CONSOLE |\n";
        for(int i = 0; i < 87; i++)
            sRet +="¨";

        sRet += "\n";

        for(int i = 0; i < 3; i++)
            sRet += String.format("|%-85s|\n",test[i]);

        sRet += String.format("|%-85s|\n"," ");
        for(int i = 0; i < 87; i++)
            sRet +="¨";


*/

        sRet.replaceAll("ecrire", "\u001B[34mDEBUT\u001B[0m");

        return sRet;
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
}