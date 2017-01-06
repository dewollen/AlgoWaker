package vue.gui;

import util.donnee.Donnee;
import vue.IVue;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe qui gère l'affichage en mode graphique (Graphical User Interface)
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class GUI extends JFrame implements IVue {
    private PanelAlgo pAlgo;
    private PanelTrace pTrace;
    private PanelConsole pConsole;

    static final int WIDTH = 960;
    static final int HEIGHT = 540;

    private final String TITRE = "AlgoWaker";

    private HashMap<Integer, String> numLignes;

    public GUI() {
        this.setTitle(this.TITRE);

        this.setMinimumSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon("images/icone.png").getImage());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void affichage() {
        this.pAlgo = new PanelAlgo(this.numLignes);
        this.pTrace = new PanelTrace();
        this.pConsole = new PanelConsole();

        JSplitPane pHorizontal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.pAlgo, this.pTrace);
        JSplitPane pVertical = new JSplitPane(JSplitPane.VERTICAL_SPLIT, pHorizontal, this.pConsole);

        this.pAlgo.setPreferredSize(this.pAlgo.getPreferredSize());
        this.pTrace.setPreferredSize(this.pTrace.getPreferredSize());
        this.pConsole.setPreferredSize(this.pConsole.getPreferredSize());

        this.add(pVertical);

        this.pack();
        this.setVisible(true);
    }

    public void majIHM() {
        this.pAlgo.majIHM();
        this.pTrace.majIHM();
    }


    @Override
    public String ouvrirFichier() {
        JFileChooser explorateur = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers .algo", "algo");
        explorateur.setFileFilter(filter);

        int valBtn = explorateur.showOpenDialog(this);
        if (valBtn == JFileChooser.APPROVE_OPTION) {
            this.setTitle(this.TITRE + " - " + explorateur.getSelectedFile().getName());
            return explorateur.getSelectedFile().getAbsolutePath();
        }

        return "testsAlgo/test.algo";
    }

    @Override
    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    @Override
    public String afficher(Integer nLigne) {
        return null;
    }

    @Override
    public void setNumLignes(HashMap<Integer, String> numLignes) {
        this.numLignes = numLignes;
    }

    @Override
    public void setAlTraceVariables(ArrayList<Donnee> alTraceVariables) {

    }

    @Override
    public void majIhm() {

    }

    @Override
    public void setMinimumSize(Dimension minimumSize) {
        super.setMinimumSize(minimumSize);
    }

    @Override
    public String toString() {
        this.affichage();
        return null;
    }
}
