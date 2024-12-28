package Core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * La classe Snapshot permet de sauvegarder l’état d’un dossier et de ses sous-dossiers,
 * de comparer cet état avec un état précédent, et de fournir un rapport de ces différences.
 * @author @Gauthier Defrance @Kenan Ammad
 * @version 1.3 [20/12/2024]
 */
public class Snapshot {
    private Folder folder; // Le dossier associé à ce snapshot

    /**
     * Constructeur de la classe Snapshot.
     * @param folder Le dossier à surveiller.
     */
    public Snapshot(Folder folder) {
        this.folder = folder;
    }

    /**
     * Génère une représentation JSON de l’état actuel du dossier et de ses sous-dossiers.
     * @return Une chaîne JSON représentant l’état actuel.
     * @throws JsonProcessingException Si une erreur survient lors de la sérialisation JSON.
     */
    public String snapshotSaveJsonGetter() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<Folder> list = new ArrayList<>();
        StringBuilder json = new StringBuilder();
        list.add(folder);
        for (File myfile : folder.getAllFolders()) {
            list.add(new Folder(myfile.getAbsolutePath()));
        }
        json.append(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(list));
        return json.toString();
    }

    /**
     * Sauvegarde l’état actuel du dossier et de ses sous-dossiers dans un fichier JSON.
     * @param givenPath Le chemin du fichier de sauvegarde.
     * @throws JsonProcessingException Si une erreur survient lors de la génération du JSON.
     */
    public void snapshotSave(String givenPath) throws JsonProcessingException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(givenPath))) {
            writer.write(snapshotSaveJsonGetter());  // Écrire tout le texte dans le fichier
            System.out.println("Fichier JSON sauvegardé avec succès !");
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l’exception si quelque chose ne va pas
        }
    }

    /**
     * Lit un fichier texte et retourne son contenu sous forme de chaîne.
     * @param filePath Le chemin du fichier à lire.
     * @return Le contenu du fichier.
     */
    public static String readFileToString(String filePath) {
        try {
            return Files.readString(Paths.get(filePath)); // Lit tout le contenu du fichier en une seule fois
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier : " + e.getMessage());
            e.printStackTrace();
            return ""; // Retourne une chaîne vide en cas d’erreur
        }
    }

    /**
     * Compare l’état actuel avec une sauvegarde et retourne un booléen indiquant si les états sont identiques.
     * @param givenPath Le chemin vers le fichier de sauvegarde.
     * @return true si les deux états sont identiques, false sinon.
     * @throws JsonProcessingException Si une erreur survient lors de la génération ou de la lecture du JSON.
     */
    public boolean snapshotBasicCompare(String givenPath) throws JsonProcessingException {
        return readFileToString(givenPath).equals(this.snapshotSaveJsonGetter());
    }

    /**
     * Compare l’état actuel avec une sauvegarde et retourne un rapport des différences.
     * @param givenPath Le chemin vers le fichier de sauvegarde.
     * @return Un rapport des différences entre les états.
     * @throws IOException Si une erreur survient lors de la lecture du fichier JSON.
     */
    public StringBuffer snapshotCompare(String givenPath) throws IOException {
        StringBuffer result = new StringBuffer();
        ArrayList<Folder> list = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        File dossier;

        for (File myfile : folder.getAllFolders()) {
            list.add(new Folder(myfile.getAbsolutePath()));
        }

        // Désérialisation du fichier JSON
        try {
            ArrayList<FolderDeserialized> folders = objectMapper.readValue(
                    new File(givenPath),
                    objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, FolderDeserialized.class)
            );

            for (FolderDeserialized folder : folders) {
                StringBuffer resulttmp = new StringBuffer();

                resulttmp.append("| Chemin : " + folder.getAbsolutePath());
                dossier = new File(folder.getAbsolutePath());
                if (dossier.exists() && dossier.isDirectory()) {
                    for (Folder myfolder : list) {
                        if (myfolder.getAbsolutePath().equals(dossier.getAbsolutePath())) {
                            resulttmp.append("\n" + this.comparaisonFolder(folder, myfolder));
                            list.remove(folder);
                            if (!resulttmp.toString().equals("| Chemin : " + folder.getAbsolutePath() + "\n")) {
                                result.append(resulttmp.toString());
                            }
                        }
                    }
                } else {
                    result.append("  ¤ " + dossier.getAbsolutePath() + " a été déplacé ou supprimé.");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(result.toString().equals("")) { result.append("¤ Aucun modification apporté au fichier :"+folder.getAbsolutePath()+" et à ses sous fichiers." ); }
        return result;
    }

    /**
     * Compare un dossier actuel avec une sauvegarde et retourne un rapport des différences.
     * @param folder Le dossier sauvegardé.
     * @param myfolder Le dossier actuel.
     * @return Un rapport des différences entre les deux dossiers.
     */
    public StringBuffer comparaisonFolder(FolderDeserialized folder, Folder myfolder) {
        StringBuffer result = new StringBuffer();
        if (!folder.getLastModified().equals(myfolder.getLastModification())) {
            result.append("\n     ¤ Date de mofidiciation changée :" + myfolder.getLastModification() + "<-" + folder.getLastModified()+"\n");
        }
        if (!folder.getParent().equals(myfolder.getParent())) {
            result.append("\n     ¤ Parent modifié :" + folder.getParent() + "<-" + myfolder.getParent()+"\n");
        }
        if (!(folder.getNumberOfElements().equals("" + myfolder.getnbElemsTotaux()))) {
            result.append("\n     ¤ Le nombre d'éléments a changé :" + myfolder.getnbElemsTotaux() + "<-" + folder.getNumberOfElements()+"\n");
        }
        if (!(folder.getNumberOfImages().equals("" + myfolder.getnbImages()))) {
            result.append("\n     ¤ Nombre d'images changé :" + myfolder.getnbImages() + "<-" + folder.getNumberOfImages()+"\n");
        }
        if (!(folder.getNumberOfFolders().equals("" + myfolder.getnbSousDossier()))) {
            result.append("\n     ¤ Nombre de sous-dossiers changé :" + myfolder.getnbSousDossier() + "<-" + folder.getNumberOfFolders()+"\n");
        }

        ArrayList<String> folders = new ArrayList<>();
        for (File t : myfolder.getFolders()) {
            folders.add(t.getAbsolutePath());
        }

        // Début vérification états des sous-dossiers
        for (String tmp : folder.getFolders()) {
            if (!folders.contains(tmp)) {
                result.append("\n     " + tmp + " a été déplacé ou supprimé\n");
            }
        }
        for (String tmp : folders) {
            if (!folder.getFolders().contains(tmp)) {
                result.append("\n     " + tmp + " a été ajouté\n");
            }
        }
        // Fin vérification états des sous-dossiers

        // Comparaison des images
        ArrayList<String> currentImagePaths = new ArrayList<>();
        HashMap<String, Image> currentImageMap = new HashMap<>();
        for (File image : myfolder.getImages()) {
            currentImagePaths.add(image.getAbsolutePath());
            try {
                Image img = new Image(image.getAbsolutePath());
                img.initMetadata();
                currentImageMap.put(image.getAbsolutePath(), img);
            } catch (Exception e) {
                System.err.println("     Erreur lors de l'initialisation des métadonnées pour : " + image.getAbsolutePath());
            }
        }

        ArrayList<String> savedImagePaths = new ArrayList<>();
        HashMap<String, Image> savedImageMap = new HashMap<>();
        for (Image savedImage : folder.getFiles()) {
            savedImagePaths.add(savedImage.getPath());
            savedImageMap.put(savedImage.getPath(), savedImage);
        }

        for (String savedImagePath : savedImagePaths) {
            if (currentImagePaths.contains(savedImagePath)) {
                StringBuffer tmp = new StringBuffer();
                Image savedImage = savedImageMap.get(savedImagePath);
                Image currentImage = currentImageMap.get(savedImagePath);
                tmp.append("\n        | Chemin de l'image : " + savedImage.getPath());
                // Comparaison des métadonnées
                if (!savedImage.getName().equals(currentImage.getName())) {
                    tmp.append("\n         > Nom de l'image : " + savedImage.getName() + " -> " + currentImage.getName());
                }
                if (!savedImage.getSize().equals(currentImage.getSize())) {
                    tmp.append("\n         > Taille de l'image modifiée : " + savedImage.getSize() + " -> " + currentImage.getSize());
                }
                if (!savedImage.getDate().equals(currentImage.getDate())) {
                    tmp.append("\n         > Date de création modifiée : " + savedImage.getDate() + " -> " + currentImage.getDate());
                }
                if (!savedImage.getMDate().equals(currentImage.getMDate())) {
                    tmp.append("\n         > Date de dernière modification modifiée : " + savedImage.getMDate() + " -> " + currentImage.getMDate());
                }
                if (!savedImage.getExtension().equals(currentImage.getExtension())) {
                    tmp.append("\n         > Extension de l'image modifié : " + savedImage.getExtension() + " -> " + currentImage.getExtension());
                }
                if (!savedImage.getMime().equals(currentImage.getMime())) {
                    tmp.append("\n         > Mime de l'image modifiée : " + savedImage.getMime() + " -> " + currentImage.getMime());
                }
                if (!savedImage.getHeight().equals(currentImage.getHeight())) {
                    tmp.append("\n         > Hauteur de l'image modifiée : " + savedImage.getHeight() + " -> " + currentImage.getHeight());
                }
                if (!savedImage.getWidth().equals(currentImage.getWidth())) {
                    tmp.append("\n         > Largeur de l'image modifiée : " + savedImage.getWidth() + " -> " + currentImage.getWidth());
                }
                if (!savedImage.getModel().equals(currentImage.getModel())) {
                    tmp.append("\n         > Modèle de l'appareil photo modifié : " + savedImage.getModel() + " -> " + currentImage.getModel());
                }
                if (!savedImage.getLattitude().equals(currentImage.getLattitude())) {
                    tmp.append("\n         > Latitude modifiée : " + savedImage.getLattitude() + " -> " + currentImage.getLattitude());
                }
                if (!savedImage.getLongitude().equals(currentImage.getLongitude())) {
                    tmp.append("\n         > Longitude modifiée : " + savedImage.getLongitude() + " -> " + currentImage.getLongitude());
                }
                if (!savedImage.getDesc().equals(currentImage.getDesc())) {
                    tmp.append("\n         > Description modifiée : " + savedImage.getDesc() + " -> " + currentImage.getDesc());
                }
                if (!savedImage.getDpix().equals(currentImage.getDpix())) {
                    tmp.append("\n         > Dpix de l'image modifiée : " + savedImage.getDpix() + " -> " + currentImage.getDpix());
                }
                if (!savedImage.getDpiy().equals(currentImage.getDpiy())) {
                    tmp.append("\n         > Dpiy de l'image modifiée : " + savedImage.getDpiy() + " -> " + currentImage.getDpiy());
                }
                if (!("\n        | Chemin de l'image : " + savedImage.getPath()).equals(tmp.toString())) {
                    result.append(tmp.toString());
                }

            } else {
                result.append("\n    ¤ Image supprimée ou déplacée : " + savedImagePath+"\n");
            }
        }

        for (String currentImagePath : currentImagePaths) {
            if (!savedImagePaths.contains(currentImagePath)) {
                result.append("\n    ¤ Nouvelle image ajoutée : " + currentImagePath+"\n");
            }
        }

        return result;
    }
}