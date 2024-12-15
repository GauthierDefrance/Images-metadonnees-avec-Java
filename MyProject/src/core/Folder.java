package core;

import com.drew.imaging.ImageProcessingException;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;



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
     * Méthode récursive pour récupérer toutes les images dans le répertoire
     * et ses sous-répertoires.
     */
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


    public ArrayList<File> searchByName(String title) {
        ArrayList<File> tmp = this.getAllImages();
        ArrayList<File> result = new ArrayList<>();
        for(File fichier : tmp){
            if(fichier.getName().contains(title)){
                result.add(fichier);
            };
        }
        return result;
    }

    public ArrayList<File> searchByDate(String targetDatePart) {
        ArrayList<File> tmp = this.getAllImages(); // Récupérer toutes les images
        ArrayList<File> result = new ArrayList<>();

        // Formater le targetDatePart pour une recherche flexible
        for (File fichier : tmp) {
            // Obtenir la date de dernière modification du fichier
            long lastModifiedTimestamp = fichier.lastModified();
            Date lastModifiedDate = new Date(lastModifiedTimestamp);

            // Formater la date du fichier en chaîne
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH); // Formater la date sous "yyyy-MM-dd"
            String fileDateString = sdf.format(lastModifiedDate);

            // Vérifier si la chaîne targetDatePart est contenue dans la date du fichier
            if (fileDateString.contains(targetDatePart)) {
                result.add(fichier);
            }
        }
        return result;
    }

    public ArrayList<File> searchByHeigth(String Heigth) throws ImageProcessingException, IOException {
        ArrayList<File> tmp = this.getAllImages();
        ArrayList<File> result = new ArrayList<>();
        Fichier tmp2;
        for(File fichier : tmp){
            tmp2 = new Fichier(fichier.getAbsolutePath());
            tmp2.initMetadata();
            if(tmp2.getHeight().contains(Heigth)){
                result.add(fichier);
            };
        }
        return result;
    }

    public ArrayList<File> searchByWidth(String Width) throws ImageProcessingException, IOException {
        ArrayList<File> tmp = this.getAllImages();
        ArrayList<File> result = new ArrayList<>();
        Fichier tmp2;
        for(File fichier : tmp){
            tmp2 = new Fichier(fichier.getAbsolutePath());
            tmp2.initMetadata();
            if(tmp2.getWidth().contains(Width)){
                result.add(fichier);
            };
        }
        return result;
    }

    public ArrayList<File> searchByMaxHeigth(String Heigth) throws ImageProcessingException, IOException {
        ArrayList<File> tmp = this.getAllImages();
        ArrayList<File> result = new ArrayList<>();
        Fichier tmp2;
        for(File fichier : tmp) {
            tmp2 = new Fichier(fichier.getAbsolutePath());
            tmp2.initMetadata();
            try {
                // Convertir les chaînes en entiers
                int tmp2Width = Integer.parseInt(tmp2.getHeight());
                int widthValue = Integer.parseInt(Heigth);
                if (tmp2Width < widthValue) {
                    result.add(fichier);
                }
            } catch (Exception e) {
                System.out.println(e + "Erreur lors du traitement de :\n" + fichier.getAbsolutePath());
            }
        }
        return result;
    }

    public ArrayList<File> searchByMinHeigth(String Heigth) throws ImageProcessingException, IOException {
        ArrayList<File> tmp = this.getAllImages();
        ArrayList<File> result = new ArrayList<>();
        Fichier tmp2;
        for(File fichier : tmp) {
            tmp2 = new Fichier(fichier.getAbsolutePath());
            tmp2.initMetadata();
            try {
                // Convertir les chaînes en entiers
                int tmp2Width = Integer.parseInt(tmp2.getHeight());
                int widthValue = Integer.parseInt(Heigth);
                if (tmp2Width > widthValue) {
                    result.add(fichier);
                }
                ;
            } catch (Exception e) {
                System.out.println(e + "Erreur lors du traitement de :\n" + fichier.getAbsolutePath());
            }
        }
        return result;
    }

    public ArrayList<File> searchByMaxWidth(String Width) throws ImageProcessingException, IOException {
        ArrayList<File> tmp = this.getAllImages();
        ArrayList<File> result = new ArrayList<>();
        Fichier tmp2;
        for(File fichier : tmp){
            tmp2 = new Fichier(fichier.getAbsolutePath());
            tmp2.initMetadata();
            // Convertir les chaînes en entiers
            try {
                int tmp2Width = Integer.parseInt(tmp2.getWidth());
                int widthValue = Integer.parseInt(Width);
                if (tmp2Width < widthValue) {
                    result.add(fichier);
                }

            }catch(Exception e){
                System.out.println(e + "Erreur lors du traitement de :\n" + fichier.getAbsolutePath());
            }
        }
        return result;
    }

    public ArrayList<File> searchByMinWidth(String Width) throws ImageProcessingException, IOException {
        ArrayList<File> tmp = this.getAllImages();
        ArrayList<File> result = new ArrayList<>();
        Fichier tmp2;
        for(File fichier : tmp) {
            tmp2 = new Fichier(fichier.getAbsolutePath());
            tmp2.initMetadata();
            try {
                // Convertir les chaînes en entiers
                int tmp2Width = Integer.parseInt(tmp2.getWidth());
                int widthValue = Integer.parseInt(Width);
                if (tmp2Width > widthValue) {
                    result.add(fichier);
                }
                ;
            } catch (Exception e) {
                System.out.println(e + "Erreur lors du traitement de :\n" + fichier.getAbsolutePath());
            }
        }
        return result;
    }


    public ArrayList<File> searchByDesc(String desc) throws ImageProcessingException, IOException {
        ArrayList<File> tmp = this.getAllImages();
        ArrayList<File> result = new ArrayList<>();
        Fichier tmp2;
        for(File fichier : tmp){
            tmp2 = new Fichier(fichier.getAbsolutePath());
            tmp2.initMetadata();
            if(tmp2.getDesc().contains(desc)){
                result.add(fichier);
            };
        }
        return result;
    }


    public ArrayList<File> searchByMaxSize(String MaxSize) {
        ArrayList<File> tmp = this.getAllImages();
        ArrayList<File> result = new ArrayList<>();

        // Convertir MaxSize en long
        long maxSizeLong = 0;
        try {
            maxSizeLong = Long.parseLong(MaxSize);  // Convertir la chaîne en long
        } catch (NumberFormatException e) {
            System.out.println("Erreur lors de la conversion de MaxSize : " + e.getMessage());
            return result; // Retourner une liste vide si la conversion échoue
        }

        System.out.println(maxSizeLong);
        for (File fichier : tmp) {
            // Comparer la taille du fichier avec MaxSize converti
            System.out.println(fichier.length());
            if (fichier.length() < maxSizeLong) {
                result.add(fichier);
            }
        }

        return result;
    }

    public ArrayList<File> searchByMinSize(String MinSize) {
        ArrayList<File> tmp = this.getAllImages();
        ArrayList<File> result = new ArrayList<>();

        // Convertir MaxSize en long
        long MinSizeLong = 0;
        try {
            MinSizeLong = Long.parseLong(MinSize);  // Convertir la chaîne en long
        } catch (NumberFormatException e) {
            System.out.println("Erreur lors de la conversion de MaxSize : " + e.getMessage());
            return result; // Retourner une liste vide si la conversion échoue
        }

        for (File fichier : tmp) {
            // Comparer la taille du fichier avec MaxSize converti
            if (fichier.length() < MinSizeLong) {
                result.add(fichier);
            }
        }

        return result;
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


