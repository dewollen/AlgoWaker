package vue.gui;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Classe qui sert à ...
 *
 * @author thomasdigregorio
 * @version 05/01/2017
 */
public class PanelConsole extends JPanel {
    private DocumentStyle doc;
    private JTextPane panelAffichage;
    private JTextField barreEntreeValeur;

    private ArrayList<String> alConsole;

    private final StyleContext contexte = StyleContext.getDefaultStyleContext();
    private AttributeSet couleurEcrire;

    PanelConsole() {
        this.alConsole = new ArrayList<>();

        this.doc = new DocumentStyle();

        this.panelAffichage = new JTextPane(this.doc);
        this.panelAffichage.setEditable(false);
        this.panelAffichage.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        this.panelAffichage.setFocusable(false);

        this.couleurEcrire = null;

        this.barreEntreeValeur = new JTextField();
        this.barreEntreeValeur.setEnabled(false);

        this.add(this.panelAffichage);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(GUI.WIDTH / 5, GUI.HEIGHT / 5);
    }

    @Override
    public Dimension getMinimumSize() {
        return this.getPreferredSize();
    }

    void ecrireLigne(String ligne) {
        try {
            this.doc.insertString(this.doc.getLength(), ligne, this.couleurEcrire);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public String lireLigne(String nomVariable) {
        this.barreEntreeValeur.setEnabled(true);

        return null;
    }

    public void setAlConsole(ArrayList<String> alConsole) {
        this.alConsole = alConsole;
    }

    private void colorerCode() {

    }

    private class DocumentStyle extends DefaultStyledDocument {
        public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
            super.insertString(offset, str, a);
            int posInit = doc.getLength();

            setCharacterAttributes(posInit, str.length(), a, false);
        }
    }

    private class ValeurEntree implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ecrireLigne(barreEntreeValeur.getText());
        }
    }
}
