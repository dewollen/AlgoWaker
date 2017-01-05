package vue;

/**
 * Interface qui gère les deux modes de vue (CUI et GUI)
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public interface IVue {
    String ouvrirFichier();
    void afficherMessage(String message);
}
