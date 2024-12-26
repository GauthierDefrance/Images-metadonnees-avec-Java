package gui;

import javax.swing.*;
import java.awt.*;

/**
 * La classe MetaDataImage est utilisée pour afficher des informations textuelles
 * sous forme d'une fenêtre de dialogue avec une zone de texte déroulante.
 * Elle crée une fenêtre contenant un JTextArea pour afficher le texte spécifié.
 * Ce texte peut être long, et la zone de texte peut être défilée verticalement et horizontalement.
 *  @author @Kenan Ammad @Gauthier Defrance
 *  @version 1.1 [26/12/2024]
 */
public class MetaDataImage {

    /**
     * Texte à afficher dans la zone de texte
      */
    String text;

    /**
     * Constructeur de la classe MetaDataImage.
     * Crée une fenêtre contenant un JTextArea avec le texte spécifié par le paramètre.
     * Ce texte est affiché dans une zone déroulante qui permet à l'utilisateur de le faire défiler.
     *
     * @param TexteAfficher String contenant le texte à afficher dans la fenêtre.
     */
    public MetaDataImage(String TexteAfficher) {

        // Créer un JTextArea pour afficher le texte
        JTextArea textarea = new JTextArea();
        textarea.setWrapStyleWord(true);  // Word wrap pour éviter les lignes trop longues
        textarea.setLineWrap(true);       // Enabler le retour à la ligne automatique
        textarea.setText(TexteAfficher); // Définir le texte à afficher

        // Créer un JScrollPane pour rendre la zone de texte défilable
        JScrollPane paneScroll = new JScrollPane(textarea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        paneScroll.setBounds(10, 10, 700, 400); // Définir la taille et la position du panneau de défilement
        paneScroll.setPreferredSize(new Dimension(700, 400)); // Définir la taille préférée

        // Afficher le JScrollPane dans un message de type JOptionPane
        JOptionPane.showMessageDialog(null, paneScroll);
    }
}