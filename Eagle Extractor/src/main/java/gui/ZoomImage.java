package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * La classe ZoomImage permet d'afficher une image et de zoomer avant ou arrière en utilisant des boutons.
 * L'image est chargée à partir du chemin spécifié, et les boutons "+" et "-" permettent d'ajuster le niveau de zoom.
 *
 * Les boutons de zoom ajustent dynamiquement la taille de l'image affichée dans une fenêtre de type JFrame.
 * Un JScrollPane est utilisé pour rendre l'image défilable si elle dépasse la taille de la fenêtre.
 *
 * @author @Kenan Ammad @Gauthier Defrance
 * @version 1.1 [26/12/2024]
 */
public class ZoomImage extends JFrame {

    /**
     * Image à afficher et à zoomer
     */
    private BufferedImage image_zoom;
    /**
     *Facteur de zoom initial
     */
    private double zoomFactor = 1.0;
    /**
     *Label pour afficher l'image
     */
    private JLabel imgLabel;

    /**
     * Constructeur qui charge l'image depuis le chemin donné et crée la fenêtre d'affichage avec les boutons de zoom.
     *
     * @param ImagePath String contenant Le chemin de l'image à afficher.
     */
    public ZoomImage(String ImagePath) {
        try {
            // Création des boutons pour zoomer
            JButton zoomIn = new JButton("+");
            JButton zoomOut = new JButton("-");

            // Actions des boutons
            zoomIn.addActionListener(new zoom_In());
            zoomOut.addActionListener(new zoom_Out());

            // Création de la fenêtre popup
            JFrame popup = new JFrame("Image Eagle Reader");
            popup.setIconImage(new ImageIcon(getClass().getResource("/icons/icon.png")).getImage());
            JPanel rightPanelpopup = new JPanel();
            popup.setLayout(new BorderLayout());
            popup.add(BorderLayout.EAST, rightPanelpopup);
            rightPanelpopup.setLayout(new GridLayout(2, 1));
            rightPanelpopup.add(zoomIn);
            rightPanelpopup.add(zoomOut);

            // Chargement de l'image
            image_zoom = ImageIO.read(new File(ImagePath));
            imgLabel = new JLabel(new ImageIcon(image_zoom));
            JScrollPane scrollPane = new JScrollPane(imgLabel);
            popup.add(BorderLayout.CENTER, scrollPane);

            // Paramètres de la fenêtre
            popup.setSize(720, 480);
            scrollPane.updateUI();
            imgLabel.updateUI();
            popup.setLocationRelativeTo(null);
            popup.setVisible(true);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    /**
     * Classe interne qui gère l'action de zoom avant.
     */
    private class zoom_In implements ActionListener {
        /**
         * Applique un zoom avant lorsque le bouton "+" est cliqué.
         *
         * @param e L'événement du bouton cliqué.
         */
        public void actionPerformed(ActionEvent e) {
            applyZoom(0.1); // Appliquer un zoom avant
        }
    }

    /**
     * Classe interne qui gère l'action de zoom arrière.
     */
    private class zoom_Out implements ActionListener {
        /**
         * Applique un zoom arrière lorsque le bouton "-" est cliqué.
         *
         * @param e L'événement du bouton cliqué.
         */
        public void actionPerformed(ActionEvent e) {
            applyZoom(-0.1); // Appliquer un zoom arrière
        }
    }

    /**
     * Applique un changement de zoom à l'image.
     * Le facteur de zoom est ajusté et l'image redimensionnée en conséquence.
     *
     * @param zoomChange Le changement du facteur de zoom (positif pour zoom avant, négatif pour zoom arrière).
     */
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