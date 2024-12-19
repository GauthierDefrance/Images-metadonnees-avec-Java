package Core;

import com.drew.imaging.ImageProcessingException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SearchFolder {
    private final Folder folder;
    public SearchFolder(Folder folder) {this.folder = folder;}

    public ArrayList<File> searchByName(String title) {
        ArrayList<File> tmp = folder.getAllImages();
        ArrayList<File> result = new ArrayList<>();
        for(File fichier : tmp){
            if(fichier.getName().contains(title)){
                result.add(fichier);
            };
        }
        return result;
    }

    public ArrayList<File> searchByDate(String targetDatePart) {
        ArrayList<File> tmp = folder.getAllImages(); // Récupérer toutes les images
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
        ArrayList<File> tmp = folder.getAllImages();
        ArrayList<File> result = new ArrayList<>();
        Image tmp2;
        for(File fichier : tmp){
            tmp2 = new Image(fichier.getAbsolutePath());
            tmp2.initMetadata();
            if(tmp2.getHeight().contains(Heigth)){
                result.add(fichier);
            };
        }
        return result;
    }

    public ArrayList<File> searchByWidth(String Width) throws ImageProcessingException, IOException {
        ArrayList<File> tmp = folder.getAllImages();
        ArrayList<File> result = new ArrayList<>();
        Image tmp2;
        for(File fichier : tmp){
            tmp2 = new Image(fichier.getAbsolutePath());
            tmp2.initMetadata();
            if(tmp2.getWidth().contains(Width)){
                result.add(fichier);
            };
        }
        return result;
    }

    public ArrayList<File> searchByMaxHeigth(String Heigth) throws ImageProcessingException, IOException {
        ArrayList<File> tmp = folder.getAllImages();
        ArrayList<File> result = new ArrayList<>();
        Image tmp2;
        for(File fichier : tmp) {
            tmp2 = new Image(fichier.getAbsolutePath());
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
        ArrayList<File> tmp = folder.getAllImages();
        ArrayList<File> result = new ArrayList<>();
        Image tmp2;
        for(File fichier : tmp) {
            tmp2 = new Image(fichier.getAbsolutePath());
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
        ArrayList<File> tmp = folder.getAllImages();
        ArrayList<File> result = new ArrayList<>();
        Image tmp2;
        for(File fichier : tmp){
            tmp2 = new Image(fichier.getAbsolutePath());
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
        ArrayList<File> tmp = folder.getAllImages();
        ArrayList<File> result = new ArrayList<>();
        Image tmp2;
        for(File fichier : tmp) {
            tmp2 = new Image(fichier.getAbsolutePath());
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
        ArrayList<File> tmp = folder.getAllImages();
        ArrayList<File> result = new ArrayList<>();
        Image tmp2;
        for(File fichier : tmp){
            tmp2 = new Image(fichier.getAbsolutePath());
            tmp2.initMetadata();
            if(tmp2.getDesc().contains(desc)){
                result.add(fichier);
            };
        }
        return result;
    }

    public ArrayList<File> searchByMaxSize(String MaxSize) {
        ArrayList<File> tmp = folder.getAllImages();
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
        ArrayList<File> tmp = folder.getAllImages();
        ArrayList<File> result = new ArrayList<File>();

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

}
