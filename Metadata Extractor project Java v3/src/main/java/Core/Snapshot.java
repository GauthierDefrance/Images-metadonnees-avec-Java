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

public class Snapshot {
    private Folder folder;

    public Snapshot(Folder folder) {this.folder = folder;}

    public String snapshotSaveJsonGetter() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<Folder> list = new ArrayList<Folder>();
        StringBuilder json = new StringBuilder();
        list.add(folder);
        for(File myfile : folder.getAllFolders()){
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

    public boolean snapshotBasicCompare(String givenPath) throws JsonProcessingException {
        return readFileToString(givenPath).equals(this.snapshotSaveJsonGetter());
    }

    public StringBuffer snapshotCompare2(String givenPath) throws IOException {

        StringBuffer result = new StringBuffer();
        ArrayList<Folder> list = new ArrayList<Folder>();
        ObjectMapper objectMapper = new ObjectMapper();
        File dossier;

        for(File myfile : folder.getAllFolders()){
            list.add(new Folder(myfile.getAbsolutePath()));
        }

        // Désérialisation du fichier JSON

        try {
            // Lire et désérialiser en une liste de Folder
            ArrayList<FolderReserialized> folders = objectMapper.readValue(new File(givenPath), objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, FolderReserialized.class));

            for (FolderReserialized folder : folders) {
                StringBuffer resulttmp = new StringBuffer();

                resulttmp.append("Chemin : " + folder.getAbsolutePath());
                dossier = new File(folder.getAbsolutePath());
                if (dossier.exists()&&dossier.isDirectory()){
                    for(Folder myfolder : list){
                        if (myfolder.getAbsolutePath().equals(dossier.getAbsolutePath())){
                            resulttmp.append("\n"+this.comparaisonFolder(folder,myfolder));
                            list.remove(folder);
                            if(!resulttmp.toString().equals("Chemin : " + folder.getAbsolutePath()+"\n")){result.append(""+resulttmp.toString());}

                        }

                    }



                }
                else {result.append("  ¤ "+dossier.getAbsolutePath()+" a été déplacé ou supprimé.");}

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public StringBuffer comparaisonFolder(FolderReserialized folder, Folder myfolder){
        //
        StringBuffer result = new StringBuffer();
        if(!folder.getLastModified().equals(myfolder.getLastModification())){result.append("\n     ¤ Date de mofidiciation changé :" + myfolder.getLastModification() +"->"+folder.getLastModified());}
        if(!folder.getParent().equals(myfolder.getParent())){result.append("\n     ¤ Parent modifié :"+folder.getParent()+" -> "+ myfolder.getParent());}
        if(!(folder.getNumberOfElements().equals(""+myfolder.getnbElemsTotaux()))){result.append("\n     ¤ Le nombre d'élements a changé :" + myfolder.getnbElemsTotaux() +"->"+folder.getNumberOfElements() );}
        if(!(folder.getNumberOfImages().equals(""+myfolder.getnbImages()))){result.append("\n     ¤ Nombre d'image changé :" + myfolder.getnbImages()+ "->" + folder.getNumberOfImages());}
        if(!(folder.getNumberOfFolders().equals(""+myfolder.getnbSousDossier()))){result.append("\n     ¤ Nombre de sous dossiers changé :" + myfolder.getnbSousDossier() +"->"+ folder.getNumberOfFolders());}

        ArrayList<String> folders= new ArrayList<String>();
        for(File t :myfolder.getFolders()){folders.add(t.getAbsolutePath());}


        // Début vérification états des sous dossiers
        for(String tmp :folder.getFolders()){
            if(!folders.contains(tmp)){result.append("\n  "+tmp + " a été déplacé ou supprimé");}
        }
        for(String tmp :folders){
            if(!folder.getFolders().contains(tmp)){result.append("\n  "+tmp + " a été ajouté");}
        }
        // Fin vérification états des sous dossiers

        //Vérification des images dans le dossier

        // Vérification des images dans le dossier
        ArrayList<String> currentImagePaths = new ArrayList<>();
        HashMap<String, Image> currentImageMap = new HashMap<>();
        for (File image : myfolder.getImages()) {
            currentImagePaths.add(image.getAbsolutePath());
            try {
                // Charger les métadonnées des images actuelles
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


        // Vérifier les images présentes dans les deux états (comparaison des métadonnées)
        for (String savedImagePath : savedImagePaths) {
            if (currentImagePaths.contains(savedImagePath)) { // L'image existe dans les deux états
                StringBuffer tmp = new StringBuffer();
                Image savedImage = savedImageMap.get(savedImagePath);
                Image currentImage = currentImageMap.get(savedImagePath);
                tmp.append("\n         Chemin de l'image : " + savedImage.getPath());
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
                    tmp.append("\n         > Hauteur de l'image modifié : " + savedImage.getHeight() + " -> " + currentImage.getHeight());
                }
                if (!savedImage.getWidth().equals(currentImage.getWidth())) {
                    tmp.append("\n         > Largeur de l'image modifié : " + savedImage.getWidth() + " -> " + currentImage.getWidth());
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
                if(!(("\n         Chemin de l'image : "  + savedImage.getPath()).equals(tmp.toString()))){result.append(tmp.toString());}

                // Ajouter d'autres métadonnées à comparer si nécessaire
            } else {
                result.append("\n    ¤ Image supprimée ou déplacée : " + savedImagePath);
            }
        }


        // Vérifier les nouvelles images ajoutées
        for (String currentImagePath : currentImagePaths) {
            if (!savedImagePaths.contains(currentImagePath)) {
                result.append("    ¤ Nouvelle image ajoutée : " + currentImagePath);
            }
        }



        return result;
    }



}
