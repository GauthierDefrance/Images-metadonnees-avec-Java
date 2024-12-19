package core;
//Elements provenant de la librairie de dre, Metadata-Extractor
import com.adobe.internal.xmp.XMPException;
import com.adobe.internal.xmp.impl.xpath.XMPPath;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.adobe.internal.xmp.XMPMeta;

import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

//Librairie présentent par défaut
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.drew.metadata.xmp.XmpDirectory;
import com.drew.metadata.xmp.XmpReader;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Cette classe représente un fichier.
 * Elle permet de récupérer les métadonnées d'une image.
 * Attention pour les récuperer il faut initialiser l'objet avec la méthode : initMetadata()
 *
 * @author @Gauthier Defrance @kenan ammad
 *
 *
 */
@JsonPropertyOrder({"Nom", "Date", "Path", "Extension","Mime", "Last modification Date", "heigth", "weigth", "size","model","dpix","dpiy","lattitude","longitude","desc"})
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
    public Fichier(){ metadataMap = new HashMap<String,String>();}
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
    @JsonIgnore
    public File getImageFile(){ return imageFile;}

    /**
     * @return Les metadate sous format brut
     */
    @JsonIgnore
    public Metadata getMetadata(){ return metadata;}


    public void setMime(String mime){ this.mime = mime;}

    public void setExtension(String extension){ this.extension = extension;}

    public void setName(String name){ this.name = name;}

    public void setDate(String date){ this.date = date;}

    public void setMdate(String mdate){ this.date = date;}
    @JsonProperty("model")
    public void setModel(String model){ this.model = model;}

    public void setDesc(String desc){ this.desc = desc;}
    @JsonProperty("size")
    public void setSize(String size){ this.size = size;}

    public void setHeight(String height){ this.height = height;}

    public void setWidth(String width){ this.width = width;}

    public void setDpix(String dpix){ this.dpix = dpix;}

    public void setDpiy(String dpiy){ this.dpiy = dpiy;}



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
    @JsonIgnore
    public void initMetadata() {
        try {
            // Parcourir et stocker les métadonnées
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
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

    @JsonIgnore
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

    @JsonIgnore
    public String getXmp(){
            String result ="";
            XmpDirectory dir = new XmpDirectory();
            for (Directory directory : metadata.getDirectories()) {
                if(directory instanceof XmpDirectory) {
                    Map<String,String> lastdir =  ((XmpDirectory) directory).getXmpProperties();
                    for(String key: lastdir.keySet()){
                        result += key + lastdir.get(key) + "\n";
                    }
                }
            }
            if (result!=""){return "=== Xmp ===\n"+result;}
            return "";
    }


    @JsonIgnore
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
                getName(), getHeight(), getWidth(), getLattitude(), getLongitude(), getDpix(), getDpiy(), getModel(), getDesc())+getXmp();
    }

    @JsonIgnore
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
