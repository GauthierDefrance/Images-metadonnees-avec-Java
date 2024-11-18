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





/**
 * Cette classe représente un fichier.
 * Elle permet de récupérer les métadonnées d'une image.
 *
 * @author @Gauthier Defrance
 *
 * @param path  le chemin vers le fichier sous forme d'un String
 */
public class Fichier {

    private String path;
    private File imageFile;
    private Metadata metadata;

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
     * @params path un string indiquant le chemin jusqu'au fichier image cherché
     */
    public Fichier(String path) throws ImageProcessingException, IOException {
            this.path = path;
            imageFile = new File(path);
            metadata = ImageMetadataReader.readMetadata(imageFile);
            this.initMetadata();

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
    public String getMime(){return mime;}
    /**
     * @return L'extenstion
     */
    public String getExtension(){return extension;}
    /**
     * @return Le nom du fichier
     */
    public String getName(){return name;}
    /**
     * @return La date de création
     */
    public String getDate(){return date;}
    /**
     * @return La date de modification
     */
    public String getMDate(){return mdate;}
    /**
     * @return La lattitude
     */
    public String getLattitude(){return lattitude;}
    /**
     * @return La longitude
     */
    public String getLongitude(){return longitude;}
    /**
     * @return Le modèle de l'appareil photo
     */
    public String getModel(){return model;}
    /**
     * @return La description de l'image
     */
    public String getDesc(){return desc;}
    /**
     * @return Le poids de l'image en Ko
     */
    public String getSize(){return size;}
    /**
     * @return La hauteur en px
     */
    public String getHeight(){return height;}
    /**
     * @return La largeur en px
     */
    public String getWidth(){return width;}
    /**
     * @return La résolution horizontal en inch
     */
    public String getDpix(){return dpix;}
    /**
     * @return La résolution vertical en inch
     */
    public String getDpiy(){return dpiy;}

    /**
     *  Initialise les metadonnées
     */
    public void initMetadata() {
        try {
            // Parcourir et stocker les métadonnées
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    switch (tag.getTagName()){
                        case "Image Height":
                            this.height = tag.getDescription();
                            break;
                        case "Image Width":
                            this.width = tag.getDescription();
                            break;

                        case "Detected MIME Type":
                            this.mime = tag.getDescription();
                            break;

                        case "File Name":
                            this.name = tag.getDescription();
                            break;

                        case "Detected File Type Long Name":
                            this.extension = tag.getDescription();
                            break;

                        case "File Size":
                            this.size = tag.getDescription();
                            break;

                        case "Latitude":
                            this.lattitude = tag.getDescription();
                            break;

                        case "Longitude":
                            this.longitude = tag.getDescription();
                            break;
                        case "Date/Time":
                            this.date = tag.getDescription();
                            break;
                        case "File Modified Date":
                            this.mdate = tag.getDescription();
                            break;
                        case "X Resolution":
                            dpix = tag.getDescription();
                            break;
                        case "Y Resolution":
                            dpiy = tag.getDescription();
                            break;
                        case "Model":
                            model = tag.getDescription();
                            break;
                        case "Image Description":
                            desc = tag.getDescription();
                            break;
                        default:
                            break;
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

}
