package vue.gui;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Classe qui sert à ...
 *
 * @author thomasdigregorio
 * @version 05/01/2017
 */
public class PanelAlgo extends JScrollPane {
    private DocumentStyle doc;
    private JTextPane panelAffichage;

    private HashMap<Integer, String> numLignes;

    private int ligneCourante;

    private Style style;

    private final StyleContext contexte = StyleContext.getDefaultStyleContext();
    private final AttributeSet colorationBleue = contexte.addAttribute(contexte.getEmptySet(), StyleConstants.Foreground, Color.BLUE);

    PanelAlgo(HashMap<Integer, String> numLignes) {
        this.numLignes = numLignes;

        this.ligneCourante = 0;

        this.doc = new DocumentStyle();

        this.style = doc.addStyle("Courante", null);
        StyleConstants.setBackground(this.style, new Color(102, 102, 102, 180));

        this.panelAffichage = new JTextPane(this.doc);
        this.panelAffichage.setEditable(false);
        this.panelAffichage.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        DefaultCaret caret = (DefaultCaret) this.panelAffichage.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);

        this.setViewportView(this.panelAffichage);
    }

    void majIHM() {
        this.formaterCode();
        this.avancerLigneCourante();
    }

    private void avancerLigneCourante() {
        this.ligneCourante++;

        if (this.ligneCourante > (getViewport().getSize().getHeight() - 2) / 15)
            this.getVerticalScrollBar().setValue((int) (this.ligneCourante - getViewport().getSize().getHeight() / 15) * 15 + 7);
    }

    private void formaterCode() {
        try {
            this.panelAffichage.setText(null);

            String sTemp;

            for (int i = 0; i < this.numLignes.size(); i++) {
                Style a = (i == this.ligneCourante) ? style : null;

                sTemp = this.numLignes.get(i);
                sTemp = sTemp.replaceAll("\t", "   ");
                sTemp = sTemp.replaceAll("◄—", "<-");

                this.doc.insertString(this.doc.getLength(), String.format("%2d %-80s\n", i, sTemp), a);
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
        public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
            int posInit = doc.getLength();

            super.insertString(offset, str, a);

            String[] mots = str.split("\\W+");

            for (String mot : mots) {
                if (mot.matches("(ecrire|lire|si|fsi|sinon|alors)"))
                    setCharacterAttributes(posInit + str.indexOf(mot), mot.length(), colorationBleue, false);
            }
        }
    }
}
