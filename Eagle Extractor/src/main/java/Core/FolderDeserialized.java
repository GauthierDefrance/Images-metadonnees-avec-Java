package Core;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * La classe FolderDeserialized représente un dossier désérialisé à partir de JSON.
 * Elle contient des informations sur les sous-dossiers, les fichiers, et les métadonnées du dossier.
 * @author @Gauthier Defrance @kenan ammad
 * @version 1.3 [20/12/2024]
 */
public class FolderDeserialized {
    private ArrayList<String> folders; // Liste des chemins des sous-dossiers
    private ArrayList<Image> fichiers; // Liste des fichiers sous forme d'objets Image
    private HashMap<String, String> data; // Métadonnées du dossier (chemin, parent, etc.)

    /**
     * Constructeur par défaut qui initialise les structures de données.
     */
    public FolderDeserialized() {
        folders = new ArrayList<>();
        fichiers = new ArrayList<>();
        data = new HashMap<>();
    }

    /**
     * Définit le chemin absolu du dossier.
     * @param absolutePath Le chemin absolu.
     */
    @JsonProperty("absolutePath")
    public void setAbsolutePath(String absolutePath) {
        data.put("absolutePath", absolutePath);
    }

    /**
     * Retourne le chemin absolu du dossier.
     * @return Le chemin absolu.
     */
    public String getAbsolutePath() {
        return data.get("absolutePath");
    }

    /**
     * Définit la date de dernière modification du dossier.
     * @param lastModified La date de dernière modification.
     */
    @JsonProperty("Last Modification")
    public void setLastModified(String lastModified) {
        data.put("Last Modification", lastModified);
    }

    /**
     * Retourne la date de dernière modification du dossier.
     * @return La date de dernière modification.
     */
    public String getLastModified() {
        return data.get("Last Modification");
    }

    /**
     * Définit le chemin du parent du dossier.
     * @param parent Le chemin du parent.
     */
    @JsonProperty("parent")
    public void setParent(String parent) {
        data.put("parent", parent);
    }

    /**
     * Retourne le chemin du parent du dossier.
     * @return Le chemin du parent.
     */
    public String getParent() {
        return data.get("parent");
    }

    /**
     * Définit le nombre total d'éléments dans le dossier.
     * @param numberOfElements Le nombre d'éléments.
     */
    @JsonProperty("Number of elements")
    public void setNumberOfElements(String numberOfElements) {
        data.put("Number of elements", numberOfElements);
    }

    /**
     * Retourne le nombre total d'éléments dans le dossier.
     * @return Le nombre d'éléments.
     */
    public String getNumberOfElements() {
        return data.get("Number of elements");
    }

    /**
     * Définit la liste des chemins des sous-dossiers.
     * @param folders Une liste de chemins de sous-dossiers.
     */
    @JsonProperty("folders")
    public void setFolders(ArrayList<String> folders) {
        this.folders = folders;
    }

    /**
     * Retourne la liste des chemins des sous-dossiers.
     * @return Une liste de chemins de sous-dossiers.
     */
    public ArrayList<String> getFolders() {
        return folders;
    }

    /**
     * Définit la liste des fichiers sous forme d'objets Image.
     * @param tmp Une liste d'objets Image représentant les fichiers.
     */
    @JsonProperty("files")
    public void setFiles(ArrayList<Image> tmp) {
        this.fichiers = tmp;
    }

    /**
     * Retourne la liste des fichiers sous forme d'objets Image.
     * @return Une liste d'objets Image représentant les fichiers.
     */
    public ArrayList<Image> getFiles() {
        return fichiers;
    }

    /**
     * Définit le nombre de sous-dossiers dans le dossier.
     * @param numberOfFolders Le nombre de sous-dossiers.
     */
    @JsonProperty("Number of folders")
    public void setNumberOfFolders(String numberOfFolders) {
        data.put("Number of folders", numberOfFolders);
    }

    /**
     * Retourne le nombre de sous-dossiers dans le dossier.
     * @return Le nombre de sous-dossiers.
     */
    public String getNumberOfFolders() {
        return data.get("Number of folders");
    }

    /**
     * Définit le nombre d'images dans le dossier.
     * @param numberOfImages Le nombre d'images.
     */
    @JsonProperty("Number of images")
    public void setNumberOfImages(String numberOfImages) {
        data.put("Number of images", numberOfImages);
    }

    /**
     * Retourne le nombre d'images dans le dossier.
     * @return Le nombre d'images.
     */
    public String getNumberOfImages() {
        return data.get("Number of images");
    }
}
