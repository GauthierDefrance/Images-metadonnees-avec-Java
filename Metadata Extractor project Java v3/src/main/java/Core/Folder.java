package Core;


import com.drew.imaging.ImageProcessingException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@JsonPropertyOrder({"absolutePath", "Last Modification", "parent", "Number of elements", "Numbers of folders", "Numbers of images", "folders", "images"})
public class Folder {
    private String path; //On garde le chemin utilisé pour créer le repertoire
    private Path Opath;
    private File folder; //On stocke le fichier de type File

    public Folder(String path) {
        this.path = path;
        this.Opath = Paths.get(path);
        this.folder = new File(path);
    }

    /**
     * getter renvoyant une ArrayList d'objet File
     * pouvant contenir à leurs tours des fichiers.
     */
    @JsonProperty("folders")
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

    @JsonIgnore
    public ArrayList<File> getAllFolders() {
        ArrayList<File> result = new ArrayList<>();
        collectFolders(new File(this.path), result); // Récupérer les sous-dossiers
        return result;
    }

    /**
     * Collecte les dossiers à partir d'un répertoire donné et ajoute les dossiers
     * trouvés à la liste `result`.
     */
    private void collectFolders(File directory, ArrayList<File> result) {
        if (directory.isDirectory()) {
            // Ajouter le dossier courant à la liste
            result.add(directory);

            // Lister les fichiers et sous-dossiers dans le répertoire
            File[] contenu = directory.listFiles();
            if (contenu != null) {
                for (File fichier : contenu) {
                    if (fichier.isDirectory()) {
                        // Si c'est un sous-dossier, on appelle récursivement la méthode
                        collectFolders(fichier, result);
                    }
                }
            }
        }
    }

    /**
     * Méthode récursive pour récupérer toutes les images dans le répertoire
     * et ses sous-répertoires.
     */
    @JsonIgnore
    public ArrayList<File> getAllImages() {
        ArrayList<File> result = new ArrayList<File>();
        collectImages(folder, result); // Lancer la collecte à partir du répertoire de départ
        return result;
    }

    /**
     * Collecte les images à partir d'un répertoire donné et ajoute les images
     * trouvées à la liste `result`.
     */
    private void collectImages(File directory, ArrayList<File> result) {
        if (directory.isDirectory()) {
            // Lister les fichiers et sous-dossiers dans le répertoire
            File[] contenu = directory.listFiles();
            if (contenu != null) {
                for (File fichier : contenu) {
                    if (fichier.isDirectory()) {
                        // Si c'est un sous-dossier, on appelle récursivement la méthode
                        collectImages(fichier, result);
                    } else if (fichier.isFile()) {
                        try {
                            Path cheminFichier = fichier.toPath();
                            String typeMime = Files.probeContentType(cheminFichier);
                            // Si le fichier est une image, on l'ajoute à la liste
                            if (typeMime != null && typeMime.startsWith("image/")) {
                                result.add(fichier);
                            }
                        } catch (IOException e) {
                            System.out.println("Erreur lors de l'examen du fichier : " + fichier.getAbsolutePath());
                        }
                    }
                }
            }
        }
    }

    /**
     * getter renvoyant une ArrayList d'objet fichier File
     * qui ont un type Mime image, ils peuvent potentiellement être traité par le programme.
     */
    @JsonIgnore
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

    @JsonInclude
    public String getParent(){
        return this.Opath.getParent().toString();
    }

    @JsonInclude
    public String getAbsolutePath(){
        return this.Opath.toAbsolutePath().toString();
    }

    public ArrayList<Image> getFiles() throws ImageProcessingException, IOException {
        ArrayList<Image> result = new ArrayList<Image>();
        for (File fichier : getImages()) {
            Image tmp = new Image(fichier.getAbsolutePath());
            tmp.initMetadata();
            result.add(tmp);
        }
        return result;
    }

    @JsonProperty("Last Modification")
    public String getLastModification(){return convertTimestampToReadableDate(folder.lastModified());}

    @JsonIgnore
    public String getInfo() {
        return String.format("""
            === %s ===
            Last Modification : %s
            Absolut Path : %s
            """, Opath.getFileName(), convertTimestampToReadableDate(folder.lastModified()), Opath.toAbsolutePath().toString());
    }
    @JsonProperty("Number of elements")
    public int getnbElemsTotaux(){ return folder.listFiles().length;}

    @JsonProperty("Number of folders")
    public int getnbSousDossier(){ return this.getFolders().size();}

    @JsonProperty("Number of images")
    public int getnbImages(){ return this.getImages().size();}

    @JsonIgnore
    public String getStat(){
        int nbElemsTotaux = folder.listFiles().length;
        int nbSousDossier = this.getFolders().size();
        int nbImages = this.getImages().size();
        int pngCompteur=0, jpgCompteur=0, webpCompteur = 0;
        for(File image :this.getImages()){
            if(image.getName().endsWith(".png"))pngCompteur++;
            if(image.getName().endsWith(".jpg"))jpgCompteur++;
            if(image.getName().endsWith(".webp")) webpCompteur++;
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
