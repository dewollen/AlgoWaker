package vue.gui;

import controleur.Controleur;
import vue.IVue;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

/**
 * Classe qui sert à ...
 *
 * @author thomasdigregorio
 * @version 05/01/2017
 */
public class PanelAlgo extends JScrollPane {
    private final Controleur controleur;

    private DocumentStyle doc;
    private JTextPane panelAffichage;

    private Style style;

    private final AttributeSet colorationMotCle;

    PanelAlgo(Controleur controleur) {
        this.controleur = controleur;

        this.doc = new DocumentStyle();

        this.style = doc.addStyle("Courante", null);
        StyleConstants.setBackground(this.style, new Color(102, 102, 102, 180));

        StyleContext contexte = StyleContext.getDefaultStyleContext();
        colorationMotCle = contexte.addAttribute(contexte.getEmptySet(), StyleConstants.Foreground, Color.BLUE);

        this.panelAffichage = new JTextPane(this.doc);
        this.panelAffichage.setEditable(false);
        this.panelAffichage.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        this.setViewportView(this.panelAffichage);
    }

    void majIHM() {

    }

    private void avancerLigneCourante() {
        int numLigne = 0;

        if (numLigne > (getViewport().getSize().getHeight() - 2) / 15)
            this.getVerticalScrollBar().setValue((int) (numLigne - getViewport().getSize().getHeight() / 15) * 15 + 7);
    }

    private void formaterCode(String[] tabLigneCode, Integer nLigne) {
        try {
            this.panelAffichage.setText(null);

            String sTemp;

            for (int i = 0; i < tabLigneCode.length; i++) {
                sTemp = tabLigneCode[i];
                sTemp = sTemp.replaceAll("\t", "   ");
                sTemp = sTemp.replaceAll("◄—", "<-");

                this.doc.colorerCode(this.doc.getLength(), nLigne, String.format("%2d %-80s\n", i, sTemp));
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(GUI.WIDTH * 4 / 5, GUI.HEIGHT * 4 / 5);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(GUI.WIDTH * 4 / 5, GUI.HEIGHT * 4 / 5);
    }

    private class DocumentStyle extends DefaultStyledDocument {
        private String regexMotsCles;

        DocumentStyle() {
            super();

            this.creerListeMotsCles();
        }

        void creerListeMotsCles() {
            String sTemp = "(";
            for (int i = 0; i < IVue.motsCles.length; i++)
                sTemp += IVue.motsCles[i][0] + "|";

            regexMotsCles = sTemp + ")";
        }

        void colorerCode(int numeroLigne, int nLigne, String ligne) throws BadLocationException {
            int posInit = doc.getLength();

            super.insertString(posInit, ligne, (numeroLigne == nLigne) ? style : null);

            String[] mots = ligne.split("\\W+");

            for (String mot : mots) {
                if (mot.matches(regexMotsCles))
                    setCharacterAttributes(posInit + ligne.indexOf(mot), mot.length(), colorationMotCle, false);
            }
        }
    }
}
