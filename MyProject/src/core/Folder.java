package core;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;


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
                        String typeMime = Files.probeContentType(cheminFichier); //A CHANGER SE BASE SUR L'EXTENSION PAS BIEN !!!
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


    public String getInfo() {}
    public String getStat(){}
}


