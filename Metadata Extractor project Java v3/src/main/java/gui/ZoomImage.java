package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class ZoomImage extends JFrame {
    private BufferedImage image_zoom;
    private double zoomFactor=1.0;
    private JLabel imgLabel;

    public ZoomImage(String ImagePath) {
        try {
            JButton zoomIn = new JButton("+");
            JButton zoomOut = new JButton("-");

            zoomIn.addActionListener(new zoom_In());
            zoomOut.addActionListener(new zoom_Out());

            JFrame popup = new JFrame("popup");
            JPanel rightPanelpopup = new JPanel();
            popup.setLayout(new BorderLayout());
            popup.add(BorderLayout.EAST,rightPanelpopup);
            rightPanelpopup.setLayout(new GridLayout(2, 1));
            rightPanelpopup.add(zoomIn);
            rightPanelpopup.add(zoomOut);

            image_zoom = ImageIO.read(new File(ImagePath));
            imgLabel = new JLabel(new ImageIcon(image_zoom));

            popup.setSize(720, 480);
            popup.add(BorderLayout.CENTER, imgLabel);
            imgLabel.updateUI();
            popup.setLocationRelativeTo(null);
            popup.setVisible(true);
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    // Méthode pour effectuer un zoom avant
    private class zoom_In implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            applyZoom(0.1); // Appliquer un zoom avant
        }
    }

    // Méthode pour effectuer un zoom arrière
    private class zoom_Out implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            applyZoom(-0.1); // Appliquer un zoom arrière
        }
    }

    private void applyZoom(double zoomChange) {
        // Ajuster le facteur de zoom
        zoomFactor += zoomChange;

        // Limiter les valeurs du facteur de zoom
        if (zoomFactor > 3.0) {
            zoomFactor = 3.0; // Limiter le zoom maximum
        } else if (zoomFactor < 0.2) {
            zoomFactor = 0.2; // Limiter le zoom minimum
        }

        // Met à jour l'image en fonction du facteur de zoom
        int newWidth = (int) (image_zoom.getWidth() * zoomFactor);
        int newHeight = (int) (image_zoom.getHeight() * zoomFactor);

        // Redimensionner l'image selon le zoom
        Image resizedImage = image_zoom.getScaledInstance(newWidth, newHeight, image_zoom.SCALE_SMOOTH);

        // Mettre à jour l'ImageIcon du JLabel avec la nouvelle image redimensionnée
        imgLabel.setIcon(new ImageIcon(resizedImage));
        imgLabel.updateUI();
    }



}
