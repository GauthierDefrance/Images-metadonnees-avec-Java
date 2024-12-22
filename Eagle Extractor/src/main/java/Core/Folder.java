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


/**
 * Classe représentant un répertoire sur le système de fichiers.
 * Cette classe permet de gérer un répertoire, d'obtenir ses sous-dossiers,
 * d'examiner ses fichiers (notamment les images) et d'extraire des informations détaillées.
 * @author @Gauthier Defrance @kenan ammad
 * @version 1.3 [20/12/2024]
 */
@JsonPropertyOrder({"absolutePath", "Last Modification", "parent", "Number of elements", "Numbers of folders", "Numbers of images", "folders", "images"})
public class Folder {
    private String path; //On garde le chemin utilisé pour créer le repertoire
    private Path Opath;
    private File folder; //On stocke le fichier de type File

    /**
     * Constructeur de la classe Folder.
     * Initialise le chemin du répertoire, l'objet Path et le fichier associé.
     *
     * @param path Le chemin du répertoire.
     */
    public Folder(String path) {
        this.path = path;
        this.Opath = Paths.get(path);
        this.folder = new File(path);
    }


    /**
     * Méthode qui renvoie une liste des sous-dossiers dans le répertoire actuel.
     * Utilise l'objet File pour lister les dossiers à l'intérieur du répertoire.
     *
     * @return Une ArrayList d'objets File représentant les sous-dossiers.
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

    /**
     * Cette méthode récupère tous les sous-dossiers du répertoire spécifié dans le constructeur,
     * ainsi que tous les sous-sous-dossiers de manière récursive, et les retourne sous forme d'une liste.
     * La collecte des dossiers est réalisée par la méthode privée récursive `collectFolders`.
     *
     * @return Une ArrayList contenant tous les sous-dossiers du répertoire courant, y compris les sous-dossiers
     *         de sous-dossiers, etc. Le résultat peut inclure plusieurs niveaux de sous-dossiers.
     */
    @JsonIgnore
    public ArrayList<File> getAllFolders() {
        ArrayList<File> result = new ArrayList<>();
        collectFolders(new File(this.path), result); // Récupérer les sous-dossiers
        return result;
    }

    /**
     * Méthode privée pour collecter tous les sous-dossiers du répertoire courant et ses sous-répertoires.
     * La méthode fonctionne de manière récursive.
     *
     * @param directory Le répertoire courant à examiner.
     * @param result La liste où les sous-dossiers seront ajoutés.
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
     * Méthode qui renvoie une liste de tous les fichiers d'image dans le répertoire et ses sous-répertoires.
     * La collecte est effectuée de manière récursive.
     *
     * @return Une ArrayList de fichiers d'images.
     */
    @JsonIgnore
    public ArrayList<File> getAllImages() {
        ArrayList<File> result = new ArrayList<File>();
        collectImages(folder, result); // Lancer la collecte à partir du répertoire de départ
        return result;
    }

    /**
     * Méthode privée pour collecter toutes les images dans le répertoire courant et ses sous-répertoires.
     *
     * @param directory Le répertoire à examiner.
     * @param result La liste des fichiers d'images à remplir.
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
                    } else if (fichier.isFile()&&(fichier.getName().endsWith(".jpg") || fichier.getName().endsWith(".png") || fichier.getName().endsWith(".webp") || fichier.getName().endsWith(".gif"))) {
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
     * Méthode qui renvoie une liste des fichiers d'image dans le répertoire actuel (pas récursif).
     *
     * @return Une ArrayList d'objets File représentant les images.
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
                    if (fichier.isFile()&&(fichier.getName().endsWith(".jpg") || fichier.getName().endsWith(".png") || fichier.getName().endsWith(".webp") || fichier.getName().endsWith(".gif"))) {
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

    /**
     * Getter pour le répertoire parent du répertoire actuel.
     *
     * @return Le chemin du répertoire parent sous forme de chaîne.
     */
    @JsonInclude
    public String getParent(){
        return this.Opath.getParent().toString();
    }

    /**
     * Getter pour le chemin absolu du répertoire.
     *
     * @return Le chemin absolu sous forme de chaîne.
     */
    @JsonInclude
    public String getAbsolutePath(){
        return this.Opath.toAbsolutePath().toString();
    }

    /**
     * Retourne une liste d'objets Image en initialisant les métadonnées pour chaque image du répertoire.
     *
     * @return Une ArrayList d'objets Image correspondant aux images dans le répertoire.
     * @throws ImageProcessingException Si une erreur survient lors du traitement des images.
     * @throws IOException Si une erreur survient lors de l'accès aux fichiers.
     */
    public ArrayList<Image> getFiles() throws ImageProcessingException, IOException {
        ArrayList<Image> result = new ArrayList<Image>();
        for (File fichier : getImages()) {
            Image tmp = new Image(fichier.getAbsolutePath());
            tmp.initMetadata();
            result.add(tmp);
        }
        return result;
    }

    /**
     * Getter pour la dernière modification du répertoire.
     *
     * @return La date de dernière modification formatée.
     */
    @JsonProperty("Last Modification")
    public String getLastModification(){return convertTimestampToReadableDate(folder.lastModified());}

    /**
     * Retourne des informations de base sur le répertoire sous forme d'une chaîne formatée.
     *
     * @return Une chaîne contenant des informations sur le répertoire (nom, dernière modification, chemin absolu).
     */
    @JsonIgnore
    public String getInfo() {
        return String.format("""
            Last Modification : %s
            Absolut Path : %s
            """, convertTimestampToReadableDate(folder.lastModified()), Opath.toAbsolutePath().toString());
    }

    /**
     * Getter pour le nombre total d'éléments dans le répertoire (fichiers et sous-dossiers).
     *
     * @return Le nombre d'éléments dans le répertoire.
     */
    @JsonProperty("Number of elements")
    public int getnbElemsTotaux(){ return folder.listFiles().length;}

    /**
     * Getter pour le nombre de sous-dossiers dans le répertoire.
     *
     * @return Le nombre de sous-dossiers dans le répertoire.
     */
    @JsonProperty("Number of folders")
    public int getnbSousDossier(){ return this.getFolders().size();}

    /**
     * Getter pour le nombre d'images dans le répertoire.
     *
     * @return Le nombre d'images dans le répertoire.
     */
    @JsonProperty("Number of images")
    public int getnbImages(){ return this.getImages().size();}

    /**
     * Retourne des statistiques sur le répertoire, y compris le nombre d'éléments, de dossiers et d'images,
     * ainsi que des comptes détaillés pour les formats PNG, JPG et WEBP.
     *
     * @return Une chaîne contenant les statistiques détaillées sur le répertoire.
     */
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
            Number of elements : %d
            Number of folders : %d
            Number of images : %d
            PNG: %d
            JPG: %d
            WEBP: %d
            """,  nbElemsTotaux, nbSousDossier, nbImages, pngCompteur, jpgCompteur, webpCompteur);
    }

    /**
     * Convertit un timestamp (long) en une date lisible au format "yyyy-MM-dd HH:mm:ss".
     *
     * @param timestamp Le timestamp à convertir.
     * @return La date formatée en chaîne.
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
