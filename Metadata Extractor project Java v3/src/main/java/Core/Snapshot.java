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

    public void snapshotCompare2(String givenPath) throws IOException {

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
        //
        if(!folder.getLastModified().equals(myfolder.getLastModification())){System.out.println("Date de mofidiciation changé :" + myfolder.getLastModification() +"->"+folder.getLastModified());}
        if(!folder.getParent().equals(myfolder.getParent())){System.out.println("parent changé");}
        if(!(folder.getNumberOfElements().equals(""+myfolder.getnbElemsTotaux()))){System.out.println("Le nombre d'élements a changé :" + myfolder.getnbElemsTotaux() +"->"+folder.getNumberOfElements() );}
        if(!(folder.getNumberOfImages().equals(""+myfolder.getnbImages()))){System.out.println("Nombre d'image changé :" + myfolder.getnbImages()+ "->" + folder.getNumberOfImages());}
        if(!(folder.getNumberOfFolders().equals(""+myfolder.getnbSousDossier()))){System.out.println("Nombre de sous dossiers changé :" + myfolder.getnbSousDossier() +"->"+ folder.getNumberOfFolders());}

        ArrayList<String> folders= new ArrayList<String>();
        for(File t :myfolder.getFolders()){folders.add(t.getAbsolutePath());}


        // Début vérification états des sous dossiers
        for(String tmp :folder.getFolders()){
            if(!folders.contains(tmp)){System.out.println(tmp + " a été déplacé ou supprimé");}
        }
        for(String tmp :folders){
            if(!folder.getFolders().contains(tmp)){System.out.println(tmp + " a été ajouté");}
        }
        // Fin vérification états des sous dossiers

        //Vérification des images dans le dossier

        for(Image myImage : folder.getFiles()){
            for(File otherImage : myfolder.getImages())
            if(!(myImage.getPath().equals(otherImage.getAbsolutePath()))){


            }
        }

    }


}
