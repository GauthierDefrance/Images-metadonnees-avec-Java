package gui;

import javax.swing.*;
import java.awt.*;

public class MetaDataImage {
    String text;

    public MetaDataImage(String TexteAfficher) {
        JFrame popup = new JFrame("popup");
        JPanel CenterPanelpopup = new JPanel();
        popup.setLayout(new BorderLayout());
        CenterPanelpopup.add(new JLabel(TexteAfficher), BorderLayout.CENTER);
    }

}
