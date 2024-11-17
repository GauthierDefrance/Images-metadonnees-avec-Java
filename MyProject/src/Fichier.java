
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.GpsDirectory;

import java.io.File;
import java.io.IOException;

public class Fichier {

    private String path;
    private File imageFile;
    private Metadata metadata;

    private String mime;
    private String extension;
    private String name;
    private String date;
    private String mdate;
    private String lattitude;
    private String longitude;
    private String model;
    private String desc;
    private String size;

    private int height;
    private int width;
    private int dpix;
    private int dpiy;


    public Fichier(String path) {
        if(new File(path).exists()) {
            this.path = path;
            imageFile = new File(path);
            metadata = ImageMetadataReader.readMetadata(imageFile);
        }
    }

    public String getPath(){ return path;}

    public void initMetadata() {
        try {
            // Parcourir et stocker les métadonnées
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    switch (tag.getTagName()){
                        case "Image Height":
                            this.height = Integer.parseInt(tag.getDescription());
                            break;
                        case "Image Width":
                            this.width = Integer.parseInt(tag.getDescription());
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
                            this.size = ""+Integer.parseInt(tag.getDescription())/1000+"Ko";
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
                            dpix = Integer.parseInt(tag.getDescription());
                            break;
                        case "Y Resolution":
                            dpiy = Integer.parseInt(tag.getDescription());
                            break;
                        case "Model":
                            model = tag.getDescription();
                            break;
                        case "Image Description":
                            desc = Integer.parseInt(tag.getDescription());
                            break;





                    }
                    tag.getDescription();
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
