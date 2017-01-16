package vue.gui;

import metier.traducteur.Traducteur;
import scruter.Observeur;
import vue.IVue;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;

/**
 * Classe qui gère l'affichage en mode graphique (Graphical User Interface)
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class GUI extends JFrame implements IVue, Observeur {
    private PanelAlgo pAlgo;
    private PanelTrace pTrace;
    private PanelConsole pConsole;

    private MenuRaccourcis barreMenus;

    public static final int WIDTH = 960;
    public static final int HEIGHT = 540;

    private final String TITRE = "AlgoWaker – ";

    private Traducteur traducteur;


    public GUI(Traducteur traducteur) {
        this.traducteur = traducteur;

        this.barreMenus = new MenuRaccourcis();
        this.setJMenuBar(this.barreMenus);

        this.pAlgo = new PanelAlgo();
        this.pTrace = new PanelTrace();
        this.pConsole = new PanelConsole();

        JSplitPane pHorizontal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.pAlgo, this.pTrace);
        JSplitPane pVertical = new JSplitPane(JSplitPane.VERTICAL_SPLIT, pHorizontal, this.pConsole);

        this.pAlgo.setPreferredSize(this.pAlgo.getPreferredSize());
        this.pTrace.setPreferredSize(this.pTrace.getPreferredSize());
        this.pConsole.setPreferredSize(this.pConsole.getPreferredSize());

        this.add(pVertical);

        this.pack();
        this.setTitle(this.TITRE + this.traducteur.getNomFichier());
        this.setFocusable(true);
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon("images/icone.png").getImage());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void setActionsClavier(KeyAdapter clavier) {
        this.addKeyListener(clavier);
    }

    public void setActionsRaccourcis(ActionListener raccourcis) {
        this.barreMenus.ajouterActions(raccourcis);
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

        return null;
    }

    @Override
    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    @Override
    public String saisieUtilisateur(String message) {
        return JOptionPane.showInputDialog(message);
    }

    @Override
    public void lancer() {
        this.setVisible(true);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(1200, 600);
    }

    @Override
    public void majIHM(Integer ligne, String couleur) {
        this.pAlgo.formaterCode(this.traducteur.getPseudoCode(), ligne, couleur);
        this.pTrace.majIHM(this.traducteur.getVariablesATracer());
        this.pConsole.majIHM(this.traducteur.getConsole());
    }
}
