package core;

import core.FolderReserialized;

import com.drew.imaging.ImageProcessingException;

import java.io.BufferedWriter;
import java.io.FileWriter;

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
import java.util.HashMap;
import java.util.Locale;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Classe représentant un Fichier
 *
 * @author @Gauthier Defrance @kenan ammad
 *
 */

@JsonPropertyOrder({"absolutePath", "Last Modification", "parent", "Number of elements", "Numbers of folders", "Numbers of images", "folders", "images"})
public class Folder {
    private String path; //On garde le chemin utilisé pour créer le repertoire
    private Path Opath;
    private File folder; //On stocke le fichier de type File
    /**
     * Contructeur de la classe
     * @params path un string indiquant le chemin jusqu'au fichier image cherché
     */
    public Folder(){}

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

    public ArrayList<Fichier> getFiles() throws ImageProcessingException, IOException {
        ArrayList<Fichier> result = new ArrayList<Fichier>();
        for (File fichier : getImages()) {
            Fichier tmp = new Fichier(fichier.getAbsolutePath());
            tmp.initMetadata();
            result.add(tmp);
        }
        return result;
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

    public String snapshotSaveJsonGetter() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<Folder> list = new ArrayList<Folder>();
        StringBuilder json = new StringBuilder();
        list.add(this);
        for(File myfile : getAllFolders()){
            list.add(new Folder(myfile.getAbsolutePath()));
        }
        json.append(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(list));
    return json.toString();
    }


    public void snapshotSave(String givenPath) throws JsonProcessingException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(givenPath))) {
            writer.write(snapshotSaveJsonGetter());  // Écrire tout le texte dans le fichier
            System.out.println("Fichier JSON sauvegardé avec succès !");
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'exception si quelque chose ne va pas
        }


    }

    public static String readFileToString(String filePath) {
        try {
            return Files.readString(Paths.get(filePath)); // Lit tout le contenu du fichier en une seule fois
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier : " + e.getMessage());
            e.printStackTrace();
            return ""; // Retourne une chaîne vide en cas d'erreur
        }
    }

    public boolean snapshotCompare(String givenPath) throws JsonProcessingException {
        return readFileToString(givenPath).equals(this.snapshotSaveJsonGetter());
    }

    public void snapshotCompare2(String givenPath) throws IOException {

        ArrayList<Folder> list = new ArrayList<Folder>();
        ObjectMapper objectMapper = new ObjectMapper();
        File dossier;

        for(File myfile : getAllFolders()){
            list.add(new Folder(myfile.getAbsolutePath()));
        }

        // Désérialisation du fichier JSON

        try {
            // Lire et désérialiser en une liste de Folder
            ArrayList<FolderReserialized> folders = objectMapper.readValue(new File(givenPath), objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, FolderReserialized.class));

            for (FolderReserialized folder : folders) {
                System.out.println("Chemin : " + folder.getAbsolutePath());
                dossier = new File(folder.getAbsolutePath());
                if (dossier.exists()&&dossier.isDirectory()){
                for(Folder myfolder : list){
                    if (myfolder.getAbsolutePath().equals(dossier.getAbsolutePath())){
                        this.comparaisonFolder(folder,myfolder);
                        list.remove(folder);
                    }

                }


                }
                else {System.out.println(dossier.getAbsolutePath()+" a été déplacé ou supprimé.");}

                //for(Fichier file : folder.getFiles()){//System.out.println(file.getPath());}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void comparaisonFolder(FolderReserialized folder, Folder myfolder){
        if(!folder.getLastModified().equals(myfolder.getLastModification())){System.out.println("Date de mofidiciation changé :" + myfolder.getLastModification() +"->"+folder.getLastModified());}
        if(!folder.getParent().equals(myfolder.getParent())){System.out.println("parent changé");}
        if(!(folder.getNumberOfElements().equals(""+myfolder.getnbElemsTotaux()))){System.out.println("Le nombre d'élements a changé :" + myfolder.getnbElemsTotaux() +"->"+folder.getNumberOfElements() );}
        if(!(folder.getNumberOfImages().equals(""+myfolder.getnbImages()))){System.out.println("Nombre d'image changé :" + myfolder.getnbImages()+ "->" + folder.getNumberOfImages());}
        if(!(folder.getNumberOfFolders().equals(""+myfolder.getnbSousDossier()))){System.out.println("Nombre de sous dossiers changé :" + myfolder.getnbSousDossier() +"->"+ folder.getNumberOfFolders());}

        ArrayList<String> folders= new ArrayList<String>();
        for(File t :myfolder.getFolders()){folders.add(t.getAbsolutePath());}

        for(String tmp :folder.getFolders()){
            if(!folders.contains(tmp)){System.out.println(tmp + " a été déplacé ou supprimé");}
        }
        for(String tmp :folders){
            if(!folder.getFolders().contains(tmp)){System.out.println(tmp + " a été ajouté");}
        }


        for(Fichier myfichier : folder.getFiles()){
            if(!)
        }


    }


}


