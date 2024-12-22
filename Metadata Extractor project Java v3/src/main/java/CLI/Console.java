package CLI;

import Core.SearchFolder;
import Core.Snapshot;
import com.drew.imaging.ImageProcessingException;
import Core.Folder;
import Core.Image;
import Core.order;

import org.apache.commons.cli.*;

import java.io.File;
import java.util.ArrayList;
import java.io.IOException;

/**
 * Classe Console
 *
 * Cette classe gère l'exécution des commandes en ligne de commande pour manipuler des fichiers et répertoires, effectuer des recherches,
 * sauvegarder des états de répertoires et comparer des états sauvegardés.
 *
 * @author @Gauthier Defrance @kenan ammad
 * @version 1.3 [20/12/2024]
 */
public class Console {

    // Variables représentant les différentes options de la ligne de commande
    private boolean help;
    private boolean info;
    private boolean stat;
    private boolean file;
    private boolean directory;
    private boolean snapshotsave;
    private boolean snapshotcompare;
    private boolean search;
    private boolean order;
    private boolean list;
    private boolean by;
    private boolean error;
    private boolean r;

    private String path;
    private String[] args;
    private Options options;
    private CommandLineParser parser;
    private CommandLine cmd;

    /**
     * Constructeur de la classe Console.
     *
     * Ce constructeur initialise les options de la ligne de commande, les analyse et stocke les résultats dans les variables appropriées.
     *
     * @param args Les arguments passés en ligne de commande.
     */
    public Console(String[] args) {
        this.args = args;
        this.options = new Options(); // Initialisation de l'objet options

        // Création des options
        options.addOption("h", "help", false, "Afficher l'aide");
        options.addOption("i", "info", false, "Afficher les informations");
        options.addOption("s", "stat", false, "Afficher les statistiques");
        options.addOption("f", "file", true, "Fichier");
        options.addOption("d", "directory", true, "Répertoire");
        options.addOption("ss", "snapshotsave", true, "Sauvegarder le snapshot");
        options.addOption("sc", "snapshotcompare", true, "Comparer les snapshots");
        options.addOption("w", "search", true, "Effectuer une recherche");
        options.addOption("o", "order", true, "Ordonner les résultats selon un paramètre.");
        options.addOption("r", "reversed", false, "Inverse l'ordre des fichiers.");
        options.addOption("l", "list", false, "Lister les éléments");
        options.addOption("b", "by", true, "Chercher les éléments remplissant une condition.");

        this.parser = new DefaultParser(); // Initialisation du parser
        try {
            this.cmd = this.parser.parse(this.options, this.args); // Parsing des arguments
            // Vérification des options
            this.help = cmd.hasOption("h");
            this.info = cmd.hasOption("i");
            this.stat = cmd.hasOption("s");
            this.file = cmd.hasOption("f");
            this.directory = cmd.hasOption("d");
            this.snapshotsave = cmd.hasOption("ss");
            this.snapshotcompare = cmd.hasOption("sc");
            this.search = cmd.hasOption("w");
            this.order = cmd.hasOption("o");
            this.list = cmd.hasOption("l");
            this.by = cmd.hasOption("b");
            this.r = cmd.hasOption("r");
            // Récupération des arguments restants (par exemple un chemin)
            String[] remainingArgs = cmd.getArgs();
            if (remainingArgs.length > 0) {
                this.path = remainingArgs[0]; // Premier argument non associé à une option
            }
        } catch (ParseException e) {
            System.err.println("Erreur lors du parsing des arguments : " + e.getMessage());
            this.error = true;
        }
    }

    /**
     * Cette méthode valide les règles d'utilisation des options de la ligne de commande.
     * Elle vérifie les combinaisons d'options invalides et lance des exceptions si nécessaire.
     */
    private void validateRules() {
        try {
            if(help){return;}
            // Vérification XOR : -f ou -d mais pas les deux
            if (file && directory ) {
                throw new IllegalArgumentException("Erreur : Vous ne pouvez pas utiliser -f et -d en même temps.");
            } else if (!file && !directory && !info) {
                throw new IllegalArgumentException("Erreur : Vous devez choisir soit -f soit -d.");
            }

            // Règles pour -f
            if (file) {
                if (list || order || snapshotsave || snapshotcompare) {
                    throw new IllegalArgumentException("Erreur : Les options -list, -order, -snapshotsave et -snapshotcompare ne peuvent pas être utilisées avec -f.");
                }
            }

            // Règles pour -d
            if (file) {
                if (search) {
                    throw new IllegalArgumentException("Erreur : L'option -search ne peut pas être utilisée avec -f.");
                }
                if (order && !list) {
                    throw new IllegalArgumentException("Erreur : L'option -order nécessite l'option -l (--list).");
                }
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            this.error = true;
        }
    }

    /**
     * Exécute les commandes selon les options sélectionnées en ligne de commande.
     *
     * Cette méthode exécute l'action correspondant aux options passées par l'utilisateur.
     * Elle peut gérer le mode fichier ou répertoire et renvoie une chaîne de texte pour afficher les résultats ou les erreurs.
     *
     * @return Une chaîne de texte indiquant le résultat ou une erreur.
     * @throws ImageProcessingException En cas d'erreur de traitement d'image.
     * @throws IOException En cas d'erreur d'entrée/sortie.
     */
    public String execute() throws ImageProcessingException, IOException {
        if (args.length == 0) {return "Aucun arguments précisé\n"+getHelp();}
        validateRules();
        if (error) {
            return "Veuillez corriger les erreurs dans vos options.";
        }

        if (help) {
            return getHelp();
        }

        if (file) {
            return executeFileMode();
        } else if (directory) {
            return executeDirectoryMode();
        }

        return "Aucune commande valide détectée."+"\n"+getHelp();
    }

    /**
     * Exécute les commandes pour le mode fichier.
     *
     * Cette méthode gère les actions qui doivent être effectuées sur un fichier spécifié,
     * telles que l'affichage des informations ou des statistiques sur le fichier.
     *
     * @return Une chaîne de texte avec les résultats des actions effectuées sur le fichier.
     */
    private String executeFileMode() {
        StringBuffer output = new StringBuffer();
        try {
            Image fichier = new Image(cmd.getOptionValue("f"));

            fichier.initMetadata();

            if (info) {
                output.append(fichier.getInfo()).append("\n");
            }
            if (stat) {
                output.append(fichier.getStat()).append("\n");
            }

            return output.toString();
        } catch (IOException | ImageProcessingException e) {
            return "Erreur : n'a pas pu ouvrir le fichier : " + e.getMessage();
        }
    }

    /**
     * Exécute les commandes pour le mode répertoire.
     *
     * Cette méthode gère les actions à effectuer sur un répertoire spécifié,
     * telles que l'affichage des informations, des statistiques, ou la recherche d'images.
     *
     * @return Une chaîne de texte avec les résultats des actions effectuées sur le répertoire.
     * @throws ImageProcessingException En cas d'erreur de traitement d'image.
     * @throws IOException En cas d'erreur d'entrée/sortie.
     */
    private String executeDirectoryMode() throws ImageProcessingException, IOException {
        // Vérification si l'objet cmd n'est pas initialisé correctement
        if (cmd == null) {
            return "Erreur : les options n'ont pas été initialisées correctement."; // Message d'erreur si cmd est nul
        }
        StringBuffer output = new StringBuffer();
        // Création d'un objet Folder en utilisant l'option "d" passée en ligne de commande
        Folder folder = new Folder(cmd.getOptionValue("d"));


        // Si l'option 'info' est activée, on ajoute les informations du dossier à la sortie
        if (info) {
            output.append(folder.getInfo()).append("\n");
        }

        // Si l'option 'stat' est activée, on ajoute les statistiques du dossier à la sortie
        if (stat) {
            output.append(folder.getStat()).append("\n");
        }

        // Si l'option 'list' est activée, on effectue un listing des éléments du dossier
        if (list) {
            // Si l'option 'order' est activée, on indique que l'ordre est également activé
            if(order){
                order Order = new order(folder.getAllImages());
                for(File elem : Order.OrderFile(cmd.getOptionValue("o"),r)){
                    output.append(elem.getAbsolutePath()).append("\n");
                }
            }

            else {
                // Récupération de toutes les images dans le dossier
                ArrayList<File> images = folder.getAllImages();
                // Ajout de chaque image au résultat, en affichant son chemin absolu
                for (File image : images) {
                    output.append(image.getAbsolutePath()).append("\n");
                }
            }
        }


        // Si l'option 'search' est activée, on effectue une recherche dans le dossier
        if (search) {
            SearchFolder search = new SearchFolder(folder); // Création d'un objet de recherche pour le dossier

            // Si l'option 'by' est activée, on effectue la recherche en fonction des critères spécifiés
            if (by) {
                ArrayList<File> images;
                // On vérifie la valeur de l'option 'by' pour déterminer quel critère de recherche utiliser
                String searchBy = this.cmd.getOptionValue("by");
                String searchText = this.cmd.getOptionValue("w");

                switch (searchBy.toLowerCase()) {
                    case "name":
                        images = search.searchByName(searchText);
                        break;
                    case "height":
                        images = search.searchByHeigth(searchText);
                        break;
                    case "maxheigth":
                        images = search.searchByMaxHeigth(searchText);
                        break;
                    case "minheigth":
                        images = search.searchByMinHeigth(searchText);
                        break;
                    case "width":
                        images = search.searchByWidth(searchText);
                        break;
                    case "maxwidth":
                        images = search.searchByMaxWidth(searchText);
                        break;
                    case "minwidth":
                        images = search.searchByMinWidth(searchText);
                        break;
                    case "desc":
                        images = search.searchByDesc(searchText);
                        break;
                    case "date":
                        images = search.searchByDate(searchText);
                        break;
                    case "max":
                        images = search.searchByMaxSize(searchText);
                        break;
                    case "min":
                        images = search.searchByMinSize(searchText);
                        break;
                    default:
                        System.out.println("Paramètres :" + searchBy + " inconnu"); // Si l'option 'by' est invalide
                        images = new ArrayList<>();
                        break;
                }
                if(order){
                    order Order = new order(images);
                    for (File elem : Order.OrderFile(cmd.getOptionValue("o"),r)){
                        output.append(elem.getAbsolutePath()).append("\n");
                    }
                }
                else{
                    for (File image : images) {
                        output.append(image.getAbsolutePath()).append("\n");
                    }

                }

                } else {
                    // Si l'option 'by' n'est pas activée, on effectue une recherche par nom
                    ArrayList<File> images = search.searchByName(this.cmd.getOptionValue("w"));
                    for (File image : images) {
                        output.append(image.getAbsolutePath()).append("\n");
                    }
                }
            }

            // Création d'un objet Snapshot pour la gestion des snapshots dans le dossier
            Snapshot snap = new Snapshot(folder);

            // Si l'option 'snapshotsave' est activée, on effectue une sauvegarde du snapshot
            if (snapshotsave) {
                snap.snapshotSave(this.cmd.getOptionValue("ss"));
            }

            // Si l'option 'snapshotcompare' est activée, on effectue une comparaison des snapshots
            if (snapshotcompare) {
                output.append(snap.snapshotCompare(this.cmd.getOptionValue("sc")));
            }

            // Retourne le contenu accumulé dans le StringBuilder
            return output.toString();
    }
    /**
     * Affiche l'aide détaillée pour l'utilisation de la ligne de commande.
     *
     * Cette méthode retourne une chaîne de texte avec une description des options disponibles
     * et des exemples d'utilisation.
     *
     * @return Une chaîne de texte contenant l'aide détaillée.
     */
    public String getHelp() {
        return ("""
                    Usage: java -jar cli.jar [options]
                   ¤ Desc:
                    > This scripts can :
                        Show you informations about a File or a Folder.
                        Show you what file and folder are inside a folder.
                        Search a specific file from where you are using that commands.
                        Make a save of a specific folder.
                        Compare an old save with a current folder.
                   \s
                   ¤ Options:
                        -h, --help : Show this message.
                        -f, --file : To specify the file you want to work with (can't be used with -d or --directory).
                        -d, --directory : To specify the directory you want to work with (can't be used with -f or --file).
                        -i, --info : Show info about the file or directory.
                        -s, --stat : Show statistics about the file or directory.
                   \s
                        -w, --search : Show a list of images that have the parameter in their name (must be used with directory).
                        -b, --by : Search a specific image that matches specific type (must be used with -w or --search).
                             Valid parameters for by are : { name, heigth, maxheigth, minheigth, width, maxwidth, minwidth, desc, date, max, min }
                             date has to be used in this format yyyy-MM-DD.
                   \s
                        -l, --list : Show all the images inside the folder and the folder deeper (must be used with -d or --directory).\s
                        -o, --order : Show an ordered list of images (must be used with -l or --list).
                                      Take the following parameters: {name, height, width, size, date}
                    \s
                        -ss, --snapshotsave : Make a save of the state of a directory.
                        -sc, --snapshotcompare : Compare a save of a directory with the current directory.
                   ¤ Exemples:
                        -h : show this message. If used with anything below, only the message will show.
                       \s
                        -f path\\to\\my\\file.png -i : show infos about the file
                        -f path\\to\\my\\file.png -s : show stats about the file
                        -d path\\to\\my\\folder -i : show infos about the folder
                        -d path\\to\\my\\folder -s : show stats about the folder
                        -f path\\to\\my\\file.png -s -i : show stats & info about the file
                        -d path\\to\\my\\folder -i -s : show stats & info about the folder
                       \s
                        -d path\\to\\my\\folder -l : show all the images inside the folder and all the sub-folders.
                       \s
                        -d path\\to\\my\\folder -w NameOfTheFileISearch : show all the image that matches the search (by name)
                        -d path\\to\\my\\folder -w Name -b parameters : If it's a valid parameters (listed before) will show all\s
                                                                        the images that matches the search.
                       \s
                        -d path\\to\\my\\folder -o parameters : If it's a valid parameters (listed before) will show all\s
                                                                     the images that matches the search.\s
                        -d path\\to\\my\\folder -w Name -b parameters -o parameters : It can also be used with the search and search by.
                       \s
                        -d path\\to\\my\\folder -ss path\\to\\my\\save\\mySave.json : will make a snapshot at a specific place of the given -d folder.
                        -d path\\to\\my\\folder -sc path\\to\\my\\save\\mySave.json : will compare a saved snapshot with the given -d folder.
                       \s
                       \s
                   ¤ Commons errors:
                        -f path\\to\\my\\file.png -d path\\to\\my\\folder : Will crash, they can't be used together.
                        -d, -f, -w, -b, -ss, -sc : All take parameters. The programs won't start if you don't give any parameters to them.
                        -l, -w, -b -sc, -ss : Can only be used with -d.
                        -b : Need to be used with -w
                        -d . : fonctionne pour la majorité des fonctionnalités, mais il ne marche pas avec snapshot.
                   ¤ Autors: Ammad Kennan & Defrance Gauthier.
                   \s""");
    }

}
