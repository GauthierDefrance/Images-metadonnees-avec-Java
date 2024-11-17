import core.Fichier;
import com.drew.imaging.ImageProcessingException;

import java.io.IOException;

/**
 * Classe de test pour la classe fichier.
 * A appeler avec le path d'une image en particulier.
 *
 * @author @Gauthier Defrance
 *
 */
public class FichierTester {
    public static void main(String[] args) throws ImageProcessingException, IOException {
        Fichier f = new Fichier(args[0]);
        System.out.println(f.getAllMetadata());
        System.out.println(f.getMime());
        System.out.println(f.getHeight());
        System.out.println(f.getExtension());
        System.out.println(f.getMDate());
    }
}
