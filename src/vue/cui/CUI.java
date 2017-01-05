package vue.cui;

import vue.IVue;

/**
 * Classe qui gère l'affichage en mode console (Console User Interface)
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class CUI implements IVue {

    String[] test = new String[40];

    public String toString() {

        for(int i = 0; i < 40; i++)
            test[i] = Integer.toString(i);

        String sRet = "";

        sRet += "¨¨¨¨¨¨¨¨¨¨";
        for (int i = 0; i < 75; i++)
            sRet += " ";
        sRet += "¨¨¨¨¨¨¨¨¨¨\n";
        sRet += "|  CODE  |";
        /*for (int i = 0; i < 75; i++)
            sRet += " ";*/
        sRet += String.format("%-75s", " ");
        sRet += "| DONNEES |\n";

        for(int i = 0; i < 136; i++){
            if(i == 84)
                sRet += " ";
            else
                sRet += "¨";
        }
        sRet += "\n";

        for(int i = 0; i < test.length; i++){
            sRet += "| " + String.format("%2s",Integer.toString(i)) + " " + String.format("%20s",test[i]) + "|\n";
        }


        return sRet;
    }

    @Override
    public void ouvrirFichier() {

    }

    @Override
    public void afficherMessage(String message) {

    }
}
