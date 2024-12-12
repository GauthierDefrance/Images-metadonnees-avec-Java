package core;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 * Classe représentant un Fichier
 *
 * @author @Gauthier Defrance
 *
 */
public class Folder {
    private String path; //On garde le chemin utilisé pour créer le repertoire
    private Path Opath;
    private File folder; //On stocke le fichier de type File

    /**
     * Contructeur de la classe
     * @params path un string indiquant le chemin jusqu'au fichier image cherché
     */
    public Folder(String path) {
        this.path = path;
        this.Opath = Paths.get(path);
        this.folder = new File(path);
    }

    /**
     * getter renvoyant une ArrayList d'objet File
     * pouvant contenir à leurs tours des fichiers.
     */
    public ArrayList<File> getFolders(){
        ArrayList<File> result= new ArrayList<File>();
        if (folder.isDirectory()) {
            // Lister les fichiers et dossiers dans le répertoire
            File[] contenu = folder.listFiles();
            if (contenu != null) {
                for (File fichier : contenu) {
                    // Vérifier si c'est un dossier
                    if (fichier.isDirectory()) {
                        result.add(fichier);
                        }
                    }
                }
            }
        return result;
        }
    /**
     * getter renvoyant une ArrayList d'objet fichier File
     * qui ont un type Mime image, ils peuvent potentiellement être traité par le programme.
     */
    public ArrayList<File> getImages() {
        ArrayList<File> result = new ArrayList<File>();
        if (folder.isDirectory()) {
            // Lister les fichiers et dossiers dans le répertoire
            File[] contenu = folder.listFiles();
            if (contenu != null) {
                for (File fichier : contenu) {
                    // Vérifier si c'est un fichier
                    if (fichier.isFile()) {
                        try {
                        Path cheminFichier = fichier.toPath();
                        String typeMime = Files.probeContentType(cheminFichier); //Peut ne pas fonctionner
                        //Si le Mime indique que notre fichier est une image, alors on l'ajoute
                        if (typeMime != null && typeMime.startsWith("image/")) {
                                result.add(fichier);
                            }
                        } catch (IOException e) {System.out.println(e);}}
                    }
                }
            }
        return result;
        }

    public String getParent(){
        return this.Opath.getParent().toString();
    }

    public String getAbsolutePath(){
        return this.Opath.toAbsolutePath().toString();
    }


    public String getInfo() {
        return String.format("""
            === %s ===
            Last Modification : %s
            Absolut Path : %s
            """, Opath.getFileName(), convertTimestampToReadableDate(folder.lastModified()), Opath.toAbsolutePath().toString());


    }

    public String getStat(){
        int nbElemsTotaux = folder.listFiles().length;
        int nbSousDossier = this.getFolders().size();
        int nbImages = this.getImages().size();
        int pngCompteur=0, jpgCompteur=0, webpCompteur = 0;
        for(File image :this.getImages()){
            if(image.getName().toString().endsWith(".png"))pngCompteur++;
            if(image.getName().toString().endsWith(".jpg"))jpgCompteur++;
            if(image.getName().toString().endsWith(".webp")) webpCompteur++;
        }
        return String.format("""
            === %s ===
            Number of elements : %d
            Number of folders : %d
            Number of images : %d
            PNG: %d
            JPG: %d
            WEBP: %d
            """, Opath.getFileName(), nbElemsTotaux, nbSousDossier, nbImages, pngCompteur, jpgCompteur, webpCompteur);
    }

    /**
     * Convertit un timestamp (long) en une date lisible.
     *
     * @param timestamp Le timestamp en millisecondes sous forme de long.
     * @return La date formatée en chaîne lisible.
     */
    public static String convertTimestampToReadableDate(long timestamp) {
        // Créer un Instant à partir du timestamp
        Instant instant = Instant.ofEpochMilli(timestamp);

        // Définir le format de la date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneId.systemDefault()); // Utiliser la zone horaire du système

        // Retourner la date formatée
        return formatter.format(instant);
    }



}


