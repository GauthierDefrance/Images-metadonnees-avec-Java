package Test;

import javax.imageio.ImageIO;



import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.Tag;
import com.drew.lang.GeoLocation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.text.SimpleDateFormat;

public class Bob
{	
	private File imageFile;
	private BufferedImage image;
	Metadata metadata;
    public Bob(String arg) throws IOException {
		try {
			// Charger l'image
			this.imageFile = new File(arg);
			this.image = ImageIO.read(imageFile);

			// Lire les métadonnées
			metadata = ImageMetadataReader.readMetadata(imageFile);
		} catch (Exception e) {System.out.println("Erreur");}

    }
    
    public String getMetaData() {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		if (image != null) {
            
			 return ("File name :"+imageFile.getName()+"\n"
					 +"File Height,Width :"+image.getWidth()+", "+image.getHeight()+"\n"
					 +"Type :"+imageFile.getName().substring(imageFile.getName().lastIndexOf('.') + 1)+"\n"+
					 "Size :"+" "+imageFile.length()/1000+"Ko\n"
					 +"Last Modified the : " +sdf.format(imageFile.lastModified())
					 
					 );
			}
			
		else {
			return(null);
			}
    	}
    public String getMime() {
    	String mimeType = URLConnection.guessContentTypeFromName(imageFile.getName());
    	if(mimeType != null) return(mimeType);
		return null;

    }
    
}