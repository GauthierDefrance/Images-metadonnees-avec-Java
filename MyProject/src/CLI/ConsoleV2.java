package CLI;

import com.drew.imaging.ImageProcessingException;
import core.Folder;
import core.Fichier;
import org.apache.commons.cli.*;

import java.io.IOException;

public class ConsoleV2 {

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
    private String path;
    private String[] args;
    private Options options;
    private CommandLineParser parser;
    private CommandLine cmd;


    public ConsoleV2(String[] args) {
        // Création des options
        this.args = args;
        Options options = new Options();

        options.addOption("h", "help", false, "Afficher l'aide");
        options.addOption("i", "info", false, "Afficher les informations");
        options.addOption("s", "stat", false, "Afficher les statistiques");
        options.addOption("f", "file", true, "Fichier");
        options.addOption("d", "directory", true, "Répertoire");
        options.addOption("ss", "snapshotsave", true, "Sauvegarder le snapshot");
        options.addOption("sc", "snapshotcompare", true, "Comparer les snapshots");
        options.addOption("w", "search", true, "Effectuer une recherche");
        options.addOption("o", "order", true, "Ordonner les résultats selon un paramètre.");
        options.addOption("l", "list", false, "Lister les éléments");
        options.addOption("b", "by", true, "Cherche les élements remplissant une condition.");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);

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

    private void validateRules() {
        // Vérification XOR : -f ou -d mais pas les deux
        if (file && directory) {
            System.err.println("Erreur : Vous ne pouvez pas utiliser -f et -d en même temps.");
            error = true;
        } else if (!file && !directory) {
            System.err.println("Erreur : Vous devez choisir soit -f soit -d.");
            error = true;
        }

        // Règles pour -f
        if (file) {
            if (list || order || snapshotsave || snapshotcompare) {
                System.err.println("Erreur : Les options -list, -order, -snapshotsave et -snapshotcompare ne peuvent pas être utilisées avec -f.");
                error = true;
            }
        }

        // Règles pour -d
        if (directory) {
            if (search) {
                System.err.println("Erreur : L'option -search ne peut pas être utilisée avec -d.");
                error = true;
            }
            if (order && !list) {
                System.err.println("Erreur : L'option -order nécessite l'option -list.");
                error = true;
            }
        }
    }

    public String execute() {
        validateRules();
        if (error) {
            System.out.println("Veuillez corriger les erreurs dans vos options.");
            System.exit(1);
        }

        String output = "";

        if (help) {
            output += getHelp()+"\n";
        }

        if (file) {
            try {
           Fichier fichier = new Fichier(cmd.getOptionValue("f"));
           fichier.initMetadata();
           System.out.println("Mode fichier activé.");

            if (search) {
                System.out.println("Recherche activée.");
            }
            if (info) {

                output += fichier.getInfo() + "\n" ;
            }
            if (stat) {
                output += fichier.getStat() + "\n" ;
            }

            } catch (IOException | ImageProcessingException e) {System.out.println("Erreur n'a pas pu ouvrir le fichier : " + e.getMessage());System.exit(1);}}




        else if (directory) {

            Folder folder = new Folder(cmd.getOptionValue("d"));
            System.out.println("Mode répertoire activé.");
            if (info) {
                output+= folder.getInfo() + "\n" ;
            }
            if (stat) {
                output += folder.getStat() + "\n" ;
            }
            if (list) {
                System.out.println("Liste des éléments activée.");
                if (order) {
                    System.out.println("Ordre activé.");
                }
            }
            if (snapshotsave) {
                System.out.println("Sauvegarde du snapshot activée.");
            }
            if (snapshotcompare) {
                System.out.println("Comparaison des snapshots activée.");
            }
        }
        return output;
    }

    public String getHelp () {
        return ("""
                    Usage: java -jar cli.jar [options]
                    Desc:
                    This scripts can :
                        Show you informations about a File or a Folder.
                        Show you what file and folder are inside a folder.
                        Search a specific file from where you are using that commands.
                        Make a save of a specific folder.
                        Compare an old save with a current folder.
                   \s
                    Options:
                        -h, --help : Show this message.
                        -f, --file : To specify the file you want to work with (can't be used with -d or --directory).
                        -d, --directory : To specify the directory you want to work with (can't be used with -f or --file).
                        -i, --info : Show info about the file or directory.
                        -s, --stat : Show statistics about the file or directory.
                   \s
                        -w, --search : Show a list of images that matchs the research (must be used with ). ????? BIG QUESTIONS HERE GUYS
                        -wb, --searchby : Search a specific image that matches specific type (must be used with -w or --search).
                   \s
                        -l, --list : Show all the images inside the folder and the folder deeper (must be used with -d or --directory).\s
                        -o, --order : Show an ordered list of images (must be used with -l or --list);
                        -ss, --snapshotsave : Make a save of the state of a directory.
                        -sc, --snapshotcompare : Compare a save of a directory with the current directory.
                    Exemples:
                    We have to add Exemple
                    Commons errors:
                        We have to add Exemple
                    Autors: Ammad Kennan & Defrance Gauthier.
                   \s""");
    }










}
