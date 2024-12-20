package Core;

// Importation des éléments provenant de la bibliothèque Metadata-Extractor
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;

import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

// Importation des bibliothèques standards
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// Importation des bibliothèques tierces
import com.drew.metadata.xmp.XmpDirectory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * La classe Image représente un fichier image et fournit des méthodes pour accéder et manipuler ses métadonnées.
 * Elle utilise la bibliothèque Metadata-Extractor pour extraire ces informations.
 *
 * Attention : la méthode {@code initMetadata()} doit être appelée pour initialiser les métadonnées avant leur utilisation.
 *
 * @author Gauthier Defrance, Kenan Ammad
 */
@JsonPropertyOrder({"Nom", "Date", "Path", "Extension","Mime", "Last modification Date", "heigth", "weigth", "size","model","dpix","dpiy","lattitude","longitude","desc"})
public class Image {

    private String path; // Chemin absolu du fichier image
    private File imageFile; // Objet représentant le fichier image
    private Metadata metadata; // Métadonnées brutes de l'image

    private HashMap<String, String> metadataMap; // Carte contenant les métadonnées extraites sous forme clé-valeur

    private String mime; // Type MIME de l'image
    private String extension; // Extension du fichier
    private String name; // Nom du fichier
    private String date; // Date de prise de l'image
    private String mdate; // Date de dernière modification
    private String lattitude; // Latitude de la localisation (si disponible)
    private String longitude; // Longitude de la localisation (si disponible)
    private String model; // Modèle de l'appareil photo
    private String desc; // Description de l'image
    private String size; // Taille du fichier en Ko
    private String height; // Hauteur de l'image en pixels
    private String width; // Largeur de l'image en pixels
    private String dpix; // Résolution horizontale en dpi
    private String dpiy; // Résolution verticale en dpi


    /**
     * Constructeur par défaut qui initialise la carte des métadonnées.
     */
    public Image(){ metadataMap = new HashMap<String,String>();}

    /**
     * Constructeur paramétré.
     * @param path Chemin absolu vers le fichier image
     * @throws ImageProcessingException Si une erreur survient lors de l'extraction des métadonnées
     * @throws IOException Si une erreur survient lors de la lecture du fichier
     */
    public Image(String path) throws ImageProcessingException, IOException {
        this.path = path;
        imageFile = new File(path);
        metadata = ImageMetadataReader.readMetadata(imageFile);
        metadataMap = new HashMap<String,String>();
    }

    /**
     * Retourne le chemin absolu de l'image.
     * @return Chemin sous forme de chaîne
     */
    public String getPath(){ return path;}

    /**
     * Retourne l'objet {@link File} associé au fichier image.
     * @return Objet File
     */
    @JsonIgnore
    public File getImageFile(){ return imageFile;}

    /**
     * Retourne les métadonnées brutes de l'image.
     * @return Objet Metadata contenant les informations brutes
     */
    @JsonIgnore
    public Metadata getMetadata(){ return metadata;}


    // Méthodes "setter" pour définir les valeurs des champs privés (Sert pour la déserialisation depuis le JSON)
    public void setMime(String mime){ this.mime = mime;}
    public void setExtension(String extension){ this.extension = extension;}
    public void setName(String name){ this.name = name;}
    public void setDate(String date){ this.date = date;}
    @JsonProperty("mdate")
    public void setMdate(String mdate){ this.mdate = mdate;}
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
     * Retourne le type MIME de l'image.
     *
     * @return Le type MIME si défini, sinon une tentative est effectuée pour le récupérer
     *         depuis la métadonnée "Detected MIME Type". Si aucun n'est disponible, retourne une chaîne vide.
     */
    public String getMime() {
        if (mime != null) return mime;
        if (metadataMap.containsKey("Detected MIME Type")) return metadataMap.get("Detected MIME Type");
        return "";
    }

    /**
     * Retourne l'extension du fichier.
     *
     * @return L'extension si définie, sinon une tentative est effectuée pour la récupérer
     *         depuis la métadonnée "Detected File Type Long Name". Si aucun n'est disponible, retourne une chaîne vide.
     */
    public String getExtension() {
        if (extension != null) return extension;
        if (metadataMap.containsKey("Detected File Type Long Name")) return metadataMap.get("Detected File Type Long Name");
        return "";
    }

    /**
     * Retourne le nom du fichier.
     *
     * @return Le nom du fichier si défini, sinon une tentative est effectuée pour le récupérer
     *         depuis la métadonnée "File Name". Si aucun n'est disponible, retourne une chaîne vide.
     */
    public String getName() {
        if (name != null) return name;
        if (metadataMap.containsKey("File Name")) return metadataMap.get("File Name");
        return "";
    }

    /**
     * Retourne la date de création de l'image.
     *
     * @return La date de création si définie, sinon une tentative est effectuée pour la récupérer
     *         depuis la métadonnée "Date/Time". Si aucun n'est disponible, retourne une chaîne vide.
     */
    public String getDate() {
        if (date != null) return date;
        if (metadataMap.containsKey("Date/Time")) return metadataMap.get("Date/Time");
        return "";
    }

    /**
     * Retourne la date de modification du fichier.
     *
     * @return La date de modification si définie, sinon une tentative est effectuée pour la récupérer
     *         depuis la métadonnée "File Modified Date". Si aucun n'est disponible, retourne une chaîne vide.
     */
    public String getMDate() {
        if (mdate != null) return mdate;
        if (metadataMap.containsKey("File Modified Date")) return metadataMap.get("File Modified Date");
        return "";
    }

    /**
     * Retourne la latitude où l'image a été prise.
     *
     * @return La latitude si définie, sinon une tentative est effectuée pour la récupérer
     *         depuis la métadonnée "Latitude". Si aucun n'est disponible, retourne une chaîne vide.
     */
    public String getLattitude() {
        if (lattitude != null) return lattitude;
        if (metadataMap.containsKey("Latitude")) return metadataMap.get("Latitude");
        return "";
    }

    /**
     * Retourne la longitude où l'image a été prise.
     *
     * @return La longitude si définie, sinon une tentative est effectuée pour la récupérer
     *         depuis la métadonnée "Longitude". Si aucun n'est disponible, retourne une chaîne vide.
     */
    public String getLongitude() {
        if (longitude != null) return longitude;
        if (metadataMap.containsKey("Longitude")) return metadataMap.get("Longitude");
        return "";
    }

    /**
     * Retourne le modèle de l'appareil photo ayant pris l'image.
     *
     * @return Le modèle de l'appareil photo si défini, sinon une tentative est effectuée pour le récupérer
     *         depuis la métadonnée "Model". Si aucun n'est disponible, retourne une chaîne vide.
     */
    public String getModel() {
        if (model != null) return model;
        if (metadataMap.containsKey("Model")) return metadataMap.get("Model");
        return "";
    }

    /**
     * Retourne la description de l'image.
     *
     * @return La description si définie, sinon une tentative est effectuée pour la récupérer
     *         depuis la métadonnée "Image Description". Si aucun n'est disponible, retourne une chaîne vide.
     */
    public String getDesc() {
        if (desc != null) return desc;
        if (metadataMap.containsKey("Image Description")) return metadataMap.get("Image Description");
        return "";
    }

    /**
     * Retourne le poids du fichier en Ko.
     *
     * @return Le poids du fichier si défini, sinon une tentative est effectuée pour le récupérer
     *         depuis la métadonnée "File Size". Si aucun n'est disponible, retourne une chaîne vide.
     */
    public String getSize() {
        if (size != null) return size;
        if (metadataMap.containsKey("File Size")) return metadataMap.get("File Size");
        return "";
    }

    /**
     * Retourne la hauteur de l'image en pixels.
     *
     * @return La hauteur en pixels si définie, sinon une tentative est effectuée pour la récupérer
     *         depuis la métadonnée "Image Height". Si aucun n'est disponible, retourne une chaîne vide.
     */
    public String getHeight() {
        if (height != null) return height;
        if (metadataMap.containsKey("Image Height")) return metadataMap.get("Image Height");
        return "";
    }

    /**
     * Retourne la largeur de l'image en pixels.
     *
     * @return La largeur en pixels si définie, sinon une tentative est effectuée pour la récupérer
     *         depuis la métadonnée "Image Width". Si aucun n'est disponible, retourne une chaîne vide.
     */
    public String getWidth() {
        if (width != null) return width;
        if (metadataMap.containsKey("Image Width")) return metadataMap.get("Image Width");
        return "";
    }

    /**
     * Retourne la résolution horizontale de l'image en pouces.
     *
     * @return La résolution horizontale si définie, sinon une tentative est effectuée pour la récupérer
     *         depuis la métadonnée "X Resolution". Si aucun n'est disponible, retourne une chaîne vide.
     */
    public String getDpix() {
        if (dpix != null) return dpix;
        if (metadataMap.containsKey("X Resolution")) return metadataMap.get("X Resolution");
        return "";
    }

    /**
     * Retourne la résolution verticale de l'image en pouces.
     *
     * @return La résolution verticale si définie, sinon une tentative est effectuée pour la récupérer
     *         depuis la métadonnée "Y Resolution". Si aucun n'est disponible, retourne une chaîne vide.
     */
    public String getDpiy() {
        if (dpiy != null) return dpiy;
        if (metadataMap.containsKey("Y Resolution")) return metadataMap.get("Y Resolution");
        return "";
    }



    /**
     * Méthode d'initialisation des métadonnées.
     * Elle parcourt les groupes de métadonnées et stocke les informations pertinentes dans la carte {@code metadataMap}.
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

    /**
     * Retourne toutes les métadonnées sous forme de chaîne formatée.
     * Cette méthode parcourt toutes les directories (groupes de métadonnées) et leurs tags
     * pour extraire et afficher les informations sur chaque tag.
     *
     * @return Une chaîne contenant toutes les métadonnées, formatée avec le nom de la directory et des tags.
     *         Si une exception survient, retourne une chaîne vide.
     */
    @JsonIgnore
    public String getAllMetadata(){
        try {
            String text = "";
            // Parcours de toutes les directories (groupes de métadonnées)
            for (Directory directory : metadata.getDirectories()) {
                // Ajout du nom de la directory au texte
                text += "\n### Directory : " + directory.getName() + " ###";
                // Parcours de tous les tags de la directory
                for (Tag tag : directory.getTags()) {
                    // Ajout du nom et de la description de chaque tag au texte
                    text += "\n" + tag.getTagName() + " : " + tag.getDescription();
                }
            }
            return text; // Retourne toutes les métadonnées extraites
        } catch (Exception e) {
            // Si une exception est lancée pendant l'extraction, l'erreur est affichée
            System.err.println("Erreur lors de l'extraction des métadonnées : " + e.getMessage());
        }
        return ""; // Retourne une chaîne vide en cas d'erreur
    }

    /**
     * Retourne les propriétés XMP sous forme de chaîne formatée.
     * Cette méthode parcourt les directories pour extraire les propriétés XMP spécifiques
     * et les affiche sous forme de clé/valeur.
     *
     * @return Une chaîne contenant les propriétés XMP, ou une chaîne vide si aucune propriété XMP n'est trouvée.
     */
    @JsonIgnore
    public String getXmp(){
        String result = "";
        XmpDirectory dir = new XmpDirectory();
        // Parcours de toutes les directories pour trouver celles qui sont de type XmpDirectory
        for (Directory directory : metadata.getDirectories()) {
            if (directory instanceof XmpDirectory) {
                // Extraction des propriétés XMP sous forme de clé/valeur
                Map<String, String> lastdir = ((XmpDirectory) directory).getXmpProperties();
                for (String key : lastdir.keySet()) {
                    result += key + lastdir.get(key) + "\n"; // Ajout de chaque propriété XMP au résultat
                }
            }
        }
        // Si des propriétés XMP ont été trouvées, on les retourne, sinon on retourne une chaîne vide
        if (!result.isEmpty()) {
            return "=== Xmp ===\n" + result;
        }
        return ""; // Retourne une chaîne vide si aucune propriété XMP n'est trouvée
    }

    /**
     * Retourne les informations de base sur l'image sous forme de chaîne formatée,
     * y compris des informations comme le nom du fichier, la taille, les dimensions, la latitude, la longitude, etc.
     * Ajoute également les informations XMP à la fin de l'affichage.
     *
     * @return Une chaîne contenant les informations sur l'image.
     */
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
                getName(), getHeight(), getWidth(), getLattitude(), getLongitude(), getDpix(), getDpiy(), getModel(), getDesc())
                + getXmp(); // Ajoute les informations XMP à la fin
    }

    /**
     * Retourne les statistiques du fichier sous forme de chaîne formatée,
     * incluant des informations sur le nom du fichier, sa taille, le type MIME, la date de création, etc.
     *
     * @return Une chaîne contenant les statistiques du fichier.
     */
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
                getName(), getSize(), getMime(), getDate(), getMDate(), getExtension()); // Affiche les statistiques du fichier
    }

}
