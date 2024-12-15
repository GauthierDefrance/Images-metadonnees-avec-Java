package CLI;

import com.drew.imaging.ImageProcessingException;
import core.Folder;
import core.Fichier;
import org.apache.commons.cli.*;

import java.io.File;
import java.util.ArrayList;
import java.io.IOException;

public class Console {

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
        try {
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
                    throw new IllegalArgumentException("Erreur : L'option -order nécessite l'option -list.");
                }
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            this.error = true;
        }
    }

    public String execute() throws ImageProcessingException, IOException {
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

        return "Aucune commande valide détectée.";
    }

    private String executeFileMode() {
        try {
            Fichier fichier = new Fichier(cmd.getOptionValue("f"));

            fichier.initMetadata();

            StringBuilder output = new StringBuilder("Mode fichier activé.\n");

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

    private String executeDirectoryMode() throws ImageProcessingException, IOException {
        if (cmd == null) {
            return "Erreur : les options n'ont pas été initialisées correctement.";
        }
        Folder folder = new Folder(cmd.getOptionValue("d"));

        StringBuilder output = new StringBuilder("Mode répertoire activé.\n");

        if (info) {
            output.append(folder.getInfo()).append("\n");
        }
        if (stat) {
            output.append(folder.getStat()).append("\n");
        }
        if (list) {
            output.append("Liste des éléments activée.\n");
            if (order) {
                output.append("Ordre activé.\n");
            }
            ArrayList<File> images = folder.getAllImages();
            for (File image : images) {
                output.append(image.getAbsolutePath()).append("\n");
            }
        }
        if (search) {
            // s'utilise ainsi : "-w Le_Texte -by desc"    permet de vérifier si "Le_Texte" est présent dans la description.
            /* possibilités :
            / -w texte -by name/heigth/width/desc/date/max/min
            / Par défaut affiche du texte
            / date s'utilise ainsi : yyyy-MM-dd   il est possible de juste écrire MM-dd ou yyyy-MM
            / pour min et max, l'argument est en octet.
             */
            output.append("Recherche activée.\n");
            if (by){
                ArrayList<File> images;
                if (this.cmd.getOptionValue("by").equalsIgnoreCase("name")) {
                    images = folder.searchByName(this.cmd.getOptionValue("w"));
                    for (File image : images) {
                        output.append(image.getAbsolutePath()).append("\n");
                    }
                }
                else if (this.cmd.getOptionValue("by").equalsIgnoreCase("heigth")) {
                    images = folder.searchByHeigth(this.cmd.getOptionValue("w"));
                    for (File image : images) {
                        output.append(image.getAbsolutePath()).append("\n");
                    }

                }
                else if (this.cmd.getOptionValue("by").equalsIgnoreCase("width")) {
                    images = folder.searchByWidth(this.cmd.getOptionValue("w"));
                    for (File image : images) {
                        output.append(image.getAbsolutePath()).append("\n");
                    }

                }
                else if (this.cmd.getOptionValue("by").equalsIgnoreCase("desc")) {
                    images = folder.searchByDesc(this.cmd.getOptionValue("w"));
                    for (File image : images) {
                        output.append(image.getAbsolutePath()).append("\n");
                    }

                }
                else if (this.cmd.getOptionValue("by").equalsIgnoreCase("date")) {
                    images = folder.searchByDate(this.cmd.getOptionValue("w"));
                    for (File image : images) {
                        output.append(image.getAbsolutePath()).append("\n");
                    }

                }
                else if (this.cmd.getOptionValue("by").equalsIgnoreCase("max")) {
                    images = folder.searchByMaxSize(this.cmd.getOptionValue("w"));
                    for (File image : images) {
                        output.append(image.getAbsolutePath()).append("\n");
                    }

                }
                else if (this.cmd.getOptionValue("by").equalsIgnoreCase("min")) {
                    images = folder.searchByMinSize(this.cmd.getOptionValue("w"));
                    for (File image : images) {
                        output.append(image.getAbsolutePath()).append("\n");
                    }

                }
                else{ System.out.println("Paramètres :"+this.cmd.getOptionValue("by")+" inconnu");}

            }
            else{
                ArrayList<File> images;
                images = folder.searchByName(this.cmd.getOptionValue("w"));
                for (File image : images) {
                    output.append(image.getAbsolutePath()).append("\n");
                }
            }

        }

        if (snapshotsave) {
            output.append("Sauvegarde du snapshot activée.\n");
        }
        if (snapshotcompare) {
            output.append("Comparaison des snapshots activée.\n");
        }

        return output.toString();
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
