package Test;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.GpsDirectory;

import java.io.File;
import java.io.IOException;

public class MetaDataExample {

    public static void main(String[] args) {
        try {
            // Charger le fichier image
            File imageFile = new File("C:/Users/defra/Downloads/Canon_40D.jpg");

            // Lire les métadonnées
            Metadata metadata = ImageMetadataReader.readMetadata(imageFile);

            // Parcourir et afficher les métadonnées
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    System.out.println(tag.getTagName() + " : " + tag.getDescription());
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
}
