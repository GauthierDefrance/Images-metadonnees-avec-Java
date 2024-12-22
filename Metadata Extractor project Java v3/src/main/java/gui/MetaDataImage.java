package gui;

import javax.swing.*;
import java.awt.*;

public class MetaDataImage {
    String text;

    public MetaDataImage(String TexteAfficher) {
        JFrame popup = new JFrame("popup");
        popup.add(new JLabel(TexteAfficher));
    }

}
