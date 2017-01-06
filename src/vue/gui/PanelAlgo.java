package vue.gui;

import javax.swing.*;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.HashMap;

/**
 * Classe qui sert à ...
 *
 * @author thomasdigregorio
 * @version 05/01/2017
 */
public class PanelAlgo extends JScrollPane {
    private StyledDocument texteAlgo;
    private HashMap<Integer, String> numLignes;

    public PanelAlgo(HashMap<Integer, String> numLignes) {
        this.numLignes = numLignes;

        /*this.numLignes = new HashMap<>();

        for (int i = 0; i < 100; i++)
            numLignes.put(i, "coucou");*/

        for (Integer cle : numLignes.keySet())
            System.out.println(numLignes.get(cle));

        this.texteAlgo = new DefaultStyledDocument();

        // Ajoute l'affichage de l'algo
        JTextPane panelAffichage = new JTextPane(this.texteAlgo);
        panelAffichage.setEditable(false);

        majIHM();

        this.setViewportView(panelAffichage);
    }

    public void majIHM() {
        for (Integer cle : numLignes.keySet())
            System.out.println(numLignes.get(cle));
        /*
        int i = 0;
        try {
            for (Integer cle : this.numLignes.keySet()) {
                this.texteAlgo.insertString(i += 50, String.format("%2d %s", cle, this.numLignes.get(0)), null);
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(GUI.WIDTH / 5, GUI.HEIGHT * 4 / 5);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(GUI.WIDTH / 5, GUI.HEIGHT / 5);
    }
}
