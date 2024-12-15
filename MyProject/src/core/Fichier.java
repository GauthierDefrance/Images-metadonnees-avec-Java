package core;
//Elements provenant de la librairie de dre, Metadata-Extractor
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;

import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

//Librairie présentent par défaut
import java.io.File;
import java.io.IOException;
import java.util.HashMap;


/**
 * Cette classe représente un fichier.
 * Elle permet de récupérer les métadonnées d'une image.
 * Attention pour les récuperer il faut initialiser l'objet avec la méthode : initMetadata()
 *
 * @author @Gauthier Defrance
 *
 *
 */
public class Fichier {

    private String path;
    private File imageFile;
    private Metadata metadata;

    private HashMap<String, String> metadataMap;

    private String mime; //Stock une donnée de type Mime
    private String extension; //Stock l'extension
    private String name; //Stock le nom du fichier
    private String date; //Stock la date de prise de la photo
    private String mdate; //Stock la date de modifications de la photo
    private String lattitude; //Stock une lattitude
    private String longitude; //Stock une longitude
    private String model; //Stock le model de l'appareil photo
    private String desc; // Stock la Description de l'image
    private String size; // Stock la taille en Ko de l'image
    private String height;// Stock la hauteur en px de l'image
    private String width; // Stock la largeur en px de l'image
    private String dpix;// Stock la resolution en largeur en inch
    private String dpiy;// Stock la resolution en hauteur en inch


    /**
     * Contructeur de la classe
     * @param path un string indiquant le chemin jusqu'au fichier image cherché
     */
    public Fichier(String path) throws ImageProcessingException, IOException {
            this.path = path;
            imageFile = new File(path);
            metadata = ImageMetadataReader.readMetadata(imageFile);
            metadataMap = new HashMap<String,String>();
    }

    /**
     * @return Le chemin vers le fichier
     */
    public String getPath(){ return path;}
    /**
     * @return le fichier Image
     */
    public File getImageFile(){ return imageFile;}
    /**
     * @return Les metadate sous format brut
     */
    public Metadata getMetadata(){ return metadata;}
    /**
     * @return Le Type Mime
     */
    public String getMime(){
        if(metadataMap.containsKey("Detected MIME Type")) return metadataMap.get("Detected MIME Type");
        return "";
    }
    /**
     * @return L'extenstion
     */
    public String getExtension(){
        if(metadataMap.containsKey("Detected File Type Long Name")) return metadataMap.get("Detected File Type Long Name");
        return "";
    }
    /**
     * @return Le nom du fichier
     */
    public String getName(){
        if(metadataMap.containsKey("File Name")) return metadataMap.get("File Name");
        return "";
    }
    /**
     * @return La date de création
     */
    public String getDate(){
        if(metadataMap.containsKey("Date/Time")) return metadataMap.get("Date/Time");
        return "";
    }
    /**
     * @return La date de modification
     */
    public String getMDate(){
        if(metadataMap.containsKey("File Modified Date")) return metadataMap.get("File Modified Date");
        return "";
    }
    /**
     * @return La lattitude
     */
    public String getLattitude(){
        if(metadataMap.containsKey("Latitude")) return metadataMap.get("Latitude");
        return "";
    }
    /**
     * @return La longitude
     */
    public String getLongitude(){
        if(metadataMap.containsKey("Longitude")) return metadataMap.get("Longitude");
        return "";
    }
    /**
     * @return Le modèle de l'appareil photo
     */
    public String getModel(){
        if(metadataMap.containsKey("Model")) return metadataMap.get("Model");
        return "";
    }
    /**
     * @return La description de l'image
     */
    public String getDesc(){
        if(metadataMap.containsKey("Image Description")) return metadataMap.get("Image Description");
        return "";
    }
    /**
     * @return Le poids de l'image en Ko
     */
    public String getSize(){
        if(metadataMap.containsKey("File Size")) return metadataMap.get("File Size");
        return "";
    }
    /**
     * @return La hauteur en px
     */
    public String getHeight(){
        if(metadataMap.containsKey("Image Height")) return metadataMap.get("Image Height");
        return "";
    }
    /**
     * @return La largeur en px
     */
    public String getWidth(){
        if(metadataMap.containsKey("Image Width")) return metadataMap.get("Image Width");
        return "";
    }
    /**
     * @return La résolution horizontal en inch
     */
    public String getDpix(){
        if(metadataMap.containsKey("X Resolution")) return metadataMap.get("X Resolution");
        return "";
    }
    /**
     * @return La résolution vertical en inch
     */
    public String getDpiy(){
        if(metadataMap.containsKey("Y Resolution")) return metadataMap.get("Y Resolution");
        return "";

    }



    /**
     *  Initialise les metadonnées
     */

    public void initMetadata() {
        try {
            // Parcourir et stocker les métadonnées
            for (Directory directory : metadata.getDirectories()) {
                System.out.println(directory);
                for (Tag tag : directory.getTags()) {
                    System.out.println(tag.getTagName());
                    switch (tag.getTagName()){
                        case "Image Height":
                        case "Image Width":
                        case "Detected MIME Type":
                        case "File Name":
                        case "Detected File Type Long Name":
                        case "File Size":
                        case "Latitude":
                        case "Longitude":
                        case "Date/Time":
                        case "File Modified Date":
                        case "X Resolution":
                        case "Y Resolution":
                        case "Model":
                        case "Image Description":
                            metadataMap.put(tag.getTagName(), tag.getDescription());
                    }
                }

                // Gérer les erreurs éventuelles
                if (directory.hasErrors()) {
                    for (String error : directory.getErrors()) {
                        System.err.println("ERREUR : " + error);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de l'extraction des métadonnées : " + e.getMessage());
            e.printStackTrace();
            }
    }
    /**
     * @return Un string contenant toutes les metadonnées du fichier
     */
    public String getAllMetadata(){
        try {
            String text="";
            // Parcourir toutes les directories (groupes de métadonnées)
            for (Directory directory : metadata.getDirectories()) {
                // Afficher le nom de la directory
                text+="\n### Directory : " + directory.getName()+" ###";
                // Parcourir tous les tags de la directory
                for (Tag tag : directory.getTags()) {
                    // Afficher le nom et la description du tag
                    text+="\n" + tag.getTagName() + " : " + tag.getDescription();
                }

            }
            return text;
        } catch (Exception e) {
            System.err.println("Erreur lors de l'extraction des métadonnées : " + e.getMessage());
        }
        return "";
    }

    public String getInfo() {
        return String.format("""
        === Image Info ===
        File Name        : %s
        Image Height     : %s
        Image Width      : %s
        Latitude         : %s
        Longitude        : %s
        X Resolution     : %s dpi
        Y Resolution     : %s dpi
        Model            : %s
        Image Description: %s
        """,
                getName(), getHeight(), getWidth(), getLattitude(), getLongitude(), getDpix(), getDpiy(), getModel(), getDesc());
    }
    public String getStat() {
        return String.format("""
        === File Statistics ===
        File Name                  : %s
        File Size                  : %s bytes
        Detected MIME Type         : %s
        Date/Time                  : %s
        File Modified Date         : %s
        Detected File Type Long Name: %s
        """,
                getName(), getSize(), getMime(), getDate(), getMDate(), getExtension());
    }
}
