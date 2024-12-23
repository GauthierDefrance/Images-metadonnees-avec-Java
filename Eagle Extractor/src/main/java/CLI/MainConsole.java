package CLI;

import com.drew.imaging.ImageProcessingException;

import java.io.IOException;

/**
 * Classe principale pour exécuter la console.
 * Cette classe est utilisée pour initialiser et exécuter les commandes en ligne de commande définies dans la classe Console.
 * @author @Gauthier Defrance @Kenan Ammad
 * @version 1.3 [20/12/2024]
 */
public class MainConsole {

    /**
     * Méthode principale pour exécuter les commandes en ligne de commande.
     * Cette méthode crée une instance de la classe `Console`, passe les arguments de la ligne de commande,
     * puis appelle la méthode `execute()` pour traiter les options et afficher le résultat.
     *
     * @param args Tableau de chaînes de caractères contenant les arguments passés en ligne de commande
     * @throws ImageProcessingException Exception liée au traitement des images, lancée si une erreur survient pendant le traitement
     * @throws IOException Exception liée aux entrées/sorties, lancée en cas d'erreur lors de la lecture de fichiers ou d'autres opérations I/O
     */
    public static void main(String[] args) throws ImageProcessingException, IOException {
        // Crée une instance de la classe Console avec les arguments passés en ligne de commande

        Console console = new Console(args);

        // Exécute les commandes de la console et affiche le résultat dans la console
        System.out.println(console.execute());

        // Termine l'exécution du programme avec un code de sortie de 0 (indiquant une exécution correcte)
        System.exit(0);
    }
}