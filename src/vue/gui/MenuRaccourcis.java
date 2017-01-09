package vue.gui;

import controleur.Controleur;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Classe qui sert à ...
 *
 * @author thomasdigregorio
 * @version 09/01/2017
 */
class MenuRaccourcis extends JMenuBar {
    private Controleur controleur;

    //lol

    MenuRaccourcis(Controleur controleur) {
        this.controleur = controleur;

        this.initComposants();
    }

    private void initComposants() {
        // Menu déroulant qui gère les fichiers
        JMenu fichier = new JMenu("Fichier");

        JMenuItem ouvrir = new JMenuItem("Ouvrir");
        JMenuItem quitter = new JMenuItem("Quitter");

        fichier.add(ouvrir);
        fichier.addSeparator();
        fichier.add(quitter);

        ouvrir.addActionListener(new OuvrirListener());
        quitter.addActionListener(new QuitterListener());

        ouvrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
        quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK));

        this.add(fichier);

        // Menu déroulant qui gère les fichiers
        JMenu aPropos = new JMenu("À Propos");

        JMenuItem infos = new JMenuItem("Infos");

        aPropos.add(infos);

        infos.addActionListener(new InfosListener());

        infos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_MASK));

        this.add(aPropos);
    }

    private class OuvrirListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            controleur.ouvrirFichier();
        }
    }

    private class QuitterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            controleur.quitter();
        }
    }

    private class InfosListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            controleur.afficherInfos();
        }
    }
}
