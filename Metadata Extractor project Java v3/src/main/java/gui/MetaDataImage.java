package gui;

import javax.swing.*;
import java.awt.*;

public class MetaDataImage {
    String text;

    public MetaDataImage(String TexteAfficher) {

        JTextArea textarea = new JTextArea();
        textarea.setWrapStyleWord(true);
        textarea.setLineWrap(true);
        textarea.setText(TexteAfficher);
        JScrollPane paneScroll = new JScrollPane(textarea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        paneScroll.setBounds(10, 10, 700, 400);
        paneScroll.setPreferredSize(new Dimension(700, 400));
        JOptionPane.showMessageDialog(null, paneScroll);

    }

}
