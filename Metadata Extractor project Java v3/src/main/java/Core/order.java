package Core;

import com.drew.imaging.ImageProcessingException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * La classe {@code order} gère une collection d'objets {@code Image} et permet de trier ces objets
 * selon différents critères comme le nom, la taille, la date, la hauteur ou la largeur de l'image.
 * Elle fournit aussi une méthode pour afficher le classement des images sous forme de chaîne de caractères,
 * avec la possibilité d'afficher l'ordre inversé.
 * @author @Gauthier Defrance @kenan ammad
 * @version 1.4 [21/12/2024]
 */
public class order {

    /**
     * Liste contenant les objets {@code Image} à trier.
     */
    private ArrayList<Image> ImageList;

    /**
     * Constructeur de la classe {@code order}.
     *
     * @param images Liste de fichiers d'images à traiter.
     * @throws ImageProcessingException Si une erreur survient lors du traitement des métadonnées des images.
     * @throws IOException Si une erreur d'entrée/sortie survient lors de la lecture des fichiers.
     */
    public order(ArrayList<File> images) throws ImageProcessingException, IOException {
        ImageList = new ArrayList<Image>();
        for (File f : images) {
            // Crée un objet Image à partir du fichier et initialise ses métadonnées.
            Image tmp = new Image(f.getAbsolutePath());
            tmp.initMetadata();
            ImageList.add(tmp);
        }
    }

    /**
     * Trie la liste des images par nom.
     *
     * @return La liste des images triée par nom.
     */
    public ArrayList<Image> orderByName() {
        Collections.sort(ImageList, Comparator.comparing(Image::getName));
        return ImageList;
    }

    /**
     * Trie la liste des images par taille.
     *
     * @return La liste des images triée par taille.
     */
    public ArrayList<Image> orderBySize() {
        Collections.sort(ImageList, Comparator.comparingInt(image -> Integer.parseInt(image.getSize())));
        return ImageList;
    }

    /**
     * Trie la liste des images par date de modification.
     *
     * @return La liste des images triée par date de modification.
     */
    public ArrayList<Image> orderByDate() {
        Collections.sort(ImageList, Comparator.comparing(Image::getMdateOtherFormat));
        return ImageList;
    }

    /**
     * Trie la liste des images par hauteur.
     *
     * @return La liste des images triée par hauteur.
     */
    public ArrayList<Image> orderByHeight() {
        Collections.sort(ImageList, Comparator.comparingInt(image -> Integer.parseInt(image.getHeight())));
        return ImageList;
    }

    /**
     * Trie la liste des images par largeur.
     *
     * @return La liste des images triée par largeur.
     */
    public ArrayList<Image> orderByWidth() {
        Collections.sort(ImageList, Comparator.comparingInt(image -> Integer.parseInt(image.getWidth())));
        return ImageList;
    }

    /**
     * Trie les fichiers d'images selon le critère spécifié et retourne une chaîne de caractères représentant l'ordre des images.
     * La liste peut être retournée dans l'ordre normal ou inversé selon le paramètre {@code r}.
     *
     * @param order Le critère de tri. Les valeurs possibles sont "name", "height", "width", "date", "size".
     * @param r Si {@code true}, la liste des images sera retournée dans l'ordre inversé.
     * @return Une chaîne de caractères représentant l'ordre des images selon le critère spécifié.
     */
    public ArrayList<File> OrderFile(String order, Boolean r) {
        ArrayList<File> tmporder = new ArrayList<File>();
        // Tri des images selon le critère spécifié
        switch (order.toLowerCase()) {
            case "name":
                for (Image tmp : orderByName()) {
                    tmporder.add(new File(tmp.getPath()));
                }
                break;
            case "height":
                for (Image tmp : orderByHeight()) {
                    tmporder.add(new File(tmp.getPath()));
                }
                break;
            case "width":
                for (Image tmp : orderByWidth()) {
                    tmporder.add(new File(tmp.getPath()));
                }
                break;
            case "date":
                for (Image tmp : orderByDate()) {
                    tmporder.add(new File(tmp.getPath()));
                }
                break;
            case "size":
                for (Image tmp : orderBySize()) {
                    tmporder.add(new File(tmp.getPath()));
                }
                break;
            default:
                System.out.println("Paramètres :" + order + " inconnu"); // Si l'option 'by' est invalide
                break;
        }

        // Si l'option 'r' est activée, inverser l'ordre
        if (r) {
            // Inverser la liste
            Collections.reverse(tmporder); // Utilisation de Collections.reverse pour inverser la liste
        }

        // Retourner l'ordre sous forme de chaîne de caractères
        return tmporder;
    }
}