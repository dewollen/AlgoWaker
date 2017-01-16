package vue.gui;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Classe qui sert à ...
 *
 * @author thomasdigregorio
 * @version 10/01/2017
 */
public class MenuRaccourcis extends JMenuBar {
    private JMenuItem ouvrir;
    private JMenuItem quitter;

    private JMenuItem avancer;

    private JMenuItem infos;

    public MenuRaccourcis() {
        // Menu déroulant qui gère les fichiers
        JMenu fichier = new JMenu("Fichier");

        ouvrir = new JMenuItem("Ouvrir");
        quitter = new JMenuItem("Quitter");

        fichier.add(ouvrir);
        fichier.addSeparator();
        fichier.add(quitter);

        ouvrir.setActionCommand("ouvrir");
        quitter.setActionCommand("quitter");

        ouvrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
        quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK));

        this.add(fichier);

        // Menu déroulant qui regroupe les actions
        JMenu actions = new JMenu("Actions");

        avancer = new JMenuItem("Avancer");

        actions.add(avancer);

        this.add(actions);

        // Menu déroulant qui gère les fichiers
        JMenu aPropos = new JMenu("À Propos");

        infos = new JMenuItem("Infos");

        aPropos.add(infos);

        infos.setActionCommand("infos");

        infos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_MASK));

        this.add(aPropos);
    }

    public void ajouterActions(ActionListener actionListener) {
        ouvrir.addActionListener(actionListener);
        quitter.addActionListener(actionListener);
        avancer.addActionListener(actionListener);
        infos.addActionListener(actionListener);
    }
}
