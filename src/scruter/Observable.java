package scruter;

/**
 * Classe qui sert à ...
 *
 * @author thomasdigregorio
 * @version 10/01/2017
 */
public interface Observable {
    void ajouterObserveur(Observeur obs);

    void reinitialiserObserveur();

    void notifierObserveur();

    void notifierObserveur(String couleur);
}
