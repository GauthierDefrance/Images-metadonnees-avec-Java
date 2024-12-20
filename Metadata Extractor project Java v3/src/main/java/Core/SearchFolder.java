package Core;

import com.drew.imaging.ImageProcessingException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * La classe SearchFolder permet d'effectuer diverses recherches dans un dossier,
 * telles que par nom, date, dimensions, description, ou taille des fichiers.
 */
public class SearchFolder {
    private final Folder folder; // Dossier cible pour les recherches

    /**
     * Constructeur de la classe SearchFolder.
     * @param folder Le dossier dans lequel les recherches seront effectuées.
     */
    public SearchFolder(Folder folder) {
        this.folder = folder;
    }

    /**
     * Recherche les fichiers par nom.
     * @param title La chaîne à rechercher dans les noms de fichiers.
     * @return Une liste des fichiers correspondant au critère.
     */
    public ArrayList<File> searchByName(String title) {
        ArrayList<File> tmp = folder.getAllImages();
        ArrayList<File> result = new ArrayList<>();
        for (File fichier : tmp) {
            if (fichier.getName().contains(title)) {
                result.add(fichier);
            }
        }
        return result;
    }

    /**
     * Recherche les fichiers par date de dernière modification.
     * @param targetDatePart Une chaîne représentant une partie de la date (format "yyyy-MM-dd").
     * @return Une liste des fichiers correspondant au critère.
     */
    public ArrayList<File> searchByDate(String targetDatePart) {
        ArrayList<File> tmp = folder.getAllImages();
        ArrayList<File> result = new ArrayList<>();

        for (File fichier : tmp) {
            long lastModifiedTimestamp = fichier.lastModified();
            Date lastModifiedDate = new Date(lastModifiedTimestamp);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            String fileDateString = sdf.format(lastModifiedDate);

            if (fileDateString.contains(targetDatePart)) {
                result.add(fichier);
            }
        }
        return result;
    }

    /**
     * Recherche les fichiers par hauteur exacte.
     * @param height La hauteur cible des images.
     * @return Une liste des fichiers correspondant au critère.
     */
    public ArrayList<File> searchByHeigth(String height) throws ImageProcessingException, IOException {
        ArrayList<File> tmp = folder.getAllImages();
        ArrayList<File> result = new ArrayList<>();
        for (File fichier : tmp) {
            Image tmp2 = new Image(fichier.getAbsolutePath());
            tmp2.initMetadata();
            if (tmp2.getHeight().contains(height)) {
                result.add(fichier);
            }
        }
        return result;
    }

    /**
     * Recherche les fichiers par largeur exacte.
     * @param width La largeur cible des images.
     * @return Une liste des fichiers correspondant au critère.
     */
    public ArrayList<File> searchByWidth(String width) throws ImageProcessingException, IOException {
        ArrayList<File> tmp = folder.getAllImages();
        ArrayList<File> result = new ArrayList<>();
        for (File fichier : tmp) {
            Image tmp2 = new Image(fichier.getAbsolutePath());
            tmp2.initMetadata();
            if (tmp2.getWidth().contains(width)) {
                result.add(fichier);
            }
        }
        return result;
    }

    /**
     * Recherche les fichiers par hauteur maximale.
     * @param height La hauteur maximale des images.
     * @return Une liste des fichiers correspondant au critère.
     */
    public ArrayList<File> searchByMaxHeigth(String height) throws ImageProcessingException, IOException {
        ArrayList<File> tmp = folder.getAllImages();
        ArrayList<File> result = new ArrayList<>();
        for (File fichier : tmp) {
            Image tmp2 = new Image(fichier.getAbsolutePath());
            tmp2.initMetadata();
            try {
                int tmpHeight = Integer.parseInt(tmp2.getHeight());
                int heightValue = Integer.parseInt(height);
                if (tmpHeight < heightValue) {
                    result.add(fichier);
                }
            } catch (Exception e) {
                System.out.println(e + "Erreur lors du traitement de :\n" + fichier.getAbsolutePath());
            }
        }
        return result;
    }

    /**
     * Recherche les fichiers par hauteur minimale.
     * @param height La hauteur minimale des images.
     * @return Une liste des fichiers correspondant au critère.
     */
    public ArrayList<File> searchByMinHeigth(String height) throws ImageProcessingException, IOException {
        ArrayList<File> tmp = folder.getAllImages();
        ArrayList<File> result = new ArrayList<>();
        for (File fichier : tmp) {
            Image tmp2 = new Image(fichier.getAbsolutePath());
            tmp2.initMetadata();
            try {
                int tmpHeight = Integer.parseInt(tmp2.getHeight());
                int heightValue = Integer.parseInt(height);
                if (tmpHeight > heightValue) {
                    result.add(fichier);
                }
            } catch (Exception e) {
                System.out.println(e + "Erreur lors du traitement de :\n" + fichier.getAbsolutePath());
            }
        }
        return result;
    }

    /**
     * Recherche les fichiers par largeur maximale.
     * @param width La largeur maximale des images.
     * @return Une liste des fichiers correspondant au critère.
     */
    public ArrayList<File> searchByMaxWidth(String width) throws ImageProcessingException, IOException {
        ArrayList<File> tmp = folder.getAllImages();
        ArrayList<File> result = new ArrayList<>();
        for (File fichier : tmp) {
            Image tmp2 = new Image(fichier.getAbsolutePath());
            tmp2.initMetadata();
            try {
                int tmpWidth = Integer.parseInt(tmp2.getWidth());
                int widthValue = Integer.parseInt(width);
                if (tmpWidth < widthValue) {
                    result.add(fichier);
                }
            } catch (Exception e) {
                System.out.println(e + "Erreur lors du traitement de :\n" + fichier.getAbsolutePath());
            }
        }
        return result;
    }

    /**
     * Recherche les fichiers par largeur minimale.
     * @param width La largeur minimale des images.
     * @return Une liste des fichiers correspondant au critère.
     */
    public ArrayList<File> searchByMinWidth(String width) throws ImageProcessingException, IOException {
        ArrayList<File> tmp = folder.getAllImages();
        ArrayList<File> result = new ArrayList<>();
        for (File fichier : tmp) {
            Image tmp2 = new Image(fichier.getAbsolutePath());
            tmp2.initMetadata();
            try {
                int tmpWidth = Integer.parseInt(tmp2.getWidth());
                int widthValue = Integer.parseInt(width);
                if (tmpWidth > widthValue) {
                    result.add(fichier);
                }
            } catch (Exception e) {
                System.out.println(e + "Erreur lors du traitement de :\n" + fichier.getAbsolutePath());
            }
        }
        return result;
    }

    /**
     * Recherche les fichiers par description.
     * @param desc La description cible des images.
     * @return Une liste des fichiers correspondant au critère.
     */
    public ArrayList<File> searchByDesc(String desc) throws ImageProcessingException, IOException {
        ArrayList<File> tmp = folder.getAllImages();
        ArrayList<File> result = new ArrayList<>();
        for (File fichier : tmp) {
            Image tmp2 = new Image(fichier.getAbsolutePath());
            tmp2.initMetadata();
            if (tmp2.getDesc().contains(desc)) {
                result.add(fichier);
            }
        }
        return result;
    }

    /**
     * Recherche les fichiers par taille maximale.
     * @param maxSize La taille maximale en octets.
     * @return Une liste des fichiers correspondant au critère.
     */
    public ArrayList<File> searchByMaxSize(String maxSize) {
        ArrayList<File> tmp = folder.getAllImages();
        ArrayList<File> result = new ArrayList<>();

        long maxSizeLong;
        try {
            maxSizeLong = Long.parseLong(maxSize);
        } catch (NumberFormatException e) {
            System.out.println("Erreur lors de la conversion de maxSize : " + e.getMessage());
            return result;
        }

        for (File fichier : tmp) {
            if (fichier.length() < maxSizeLong) {
                result.add(fichier);
            }
        }

        return result;
    }

    /**
     * Recherche les fichiers par taille minimale.
     * @param minSize La taille minimale en octets.
     * @return Une liste des fichiers correspondant au critère.
     */
    public ArrayList<File> searchByMinSize(String minSize) {
        ArrayList<File> tmp = folder.getAllImages();
        ArrayList<File> result = new ArrayList<>();

        long minSizeLong;
        try {
            minSizeLong = Long.parseLong(minSize);
        } catch (NumberFormatException e) {
            System.out.println("Erreur lors de la conversion de minSize : " + e.getMessage());
            return result;
        }

        for (File fichier : tmp) {
            if (fichier.length() > minSizeLong) {
                result.add(fichier);
            }
        }

        return result;
    }
}
