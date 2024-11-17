package Test;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.text.SimpleDateFormat;

public class Bob
{	
	private File imageFile;
	private BufferedImage image;
	private Metadata metadata;
    public Bob(String arg) throws IOException {
        // Charger l'image
        this.imageFile = new File(arg);
        this.image = ImageIO.read(imageFile);

    }
    
    public String getMetaData() {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		if (image != null) {
			// Récupérer les données GPS
            }
			 return ("File name :"+imageFile.getName()+"\n"
					 +"File Height,Width :"+image.getWidth()+", "+image.getHeight()+"\n"
					 +"Type :"+""+imageFile.getName().substring(imageFile.getName().lastIndexOf('.') + 1)+"\n"+
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
    	if (mimeType != null) {
            return(mimeType);
        	} 
    	else {
            return null;
        	}	
    }
    
}