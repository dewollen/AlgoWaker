package scruter;

/**
 * Classe qui sert à ...
 *
 * @author thomasdigregorio
 * @version 10/01/2017
 */
public interface Observeur {
    void majIHM(Integer ligne, String couleur);

    void   afficherMessage(String message);
    String saisieUtilisateur(String message);
    String saisieUtilisateur();
}
