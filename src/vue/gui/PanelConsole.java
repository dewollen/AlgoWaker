package vue.gui;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Classe qui sert à ...
 *
 * @author thomasdigregorio
 * @version 10/01/2017
 */
public class PanelConsole extends JScrollPane {
    private StyledDocument doc;

    private StyleContext contexte = StyleContext.getDefaultStyleContext();

    private AttributeSet colorationLigne;

    private JTextPane panelAffichage;

    public PanelConsole() {
        this.doc = new DefaultStyledDocument();

        this.panelAffichage = new JTextPane(this.doc);
        this.panelAffichage.setEditable(false);
        this.panelAffichage.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        this.panelAffichage.setFocusable(false);

        this.colorationLigne = null;

        this.setViewportView(this.panelAffichage);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(GUI.WIDTH / 5, GUI.HEIGHT / 5);
    }

    @Override
    public Dimension getMinimumSize() {
        return this.getPreferredSize();
    }

    public void majIHM(ArrayList<String[]> console) {
        // Supprime l'affichage précédent
        this.panelAffichage.setText("");

        // Écrit le résultat de la console
        for (int i = 0; i < console.size(); i++) {
            String[] sTemp = console.get(i);
            ecrireLigne(sTemp[0], sTemp[1]);
        }
    }

    private void ecrireLigne(String ligne, String type) {
        // Récupère le nombre de caractères de la "console" pour écrire à la suite
        int posInit = this.doc.getLength();

        // Si la ligne est spéciale
        if (type != null) {
            if (type.equals("lire")) // Si c'est lire, on écrit d'une certaine couleur
                colorationLigne = contexte.addAttribute(contexte.getEmptySet(), StyleConstants.Foreground, Color.magenta);
            else                    // Si c'est ecrire, on écrit d'une autre couleur
                colorationLigne = contexte.addAttribute(contexte.getEmptySet(), StyleConstants.Foreground, Color.cyan);
        } else // Sinon on écrit en noir
            colorationLigne = contexte.addAttribute(contexte.getEmptySet(), StyleConstants.Foreground, Color.BLACK);

        // Écrit la ligne dans la "console" avec la couleur définie au dessus
        try {
            this.doc.insertString(posInit, ligne, null);
            this.doc.setCharacterAttributes(posInit, ligne.length(), this.colorationLigne, false);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}