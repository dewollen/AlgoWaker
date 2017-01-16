package util;

/**
 * Created by rapha on 11/01/2017.
 */
public class ExcetionFormatPseudoCode extends Exception {
    public ExcetionFormatPseudoCode(int numLigne, String ligne) {
        System.out.println("Erreur format pseudo code ligne : " + numLigne + " --> " + ligne);
    }
}
//lol