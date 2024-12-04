package CLI;

public class Console {

    private boolean help=false; // -h --help
    private boolean info=false; // -i --info
    private boolean stat=false; // -s --stat
    private boolean file=false; // -f --file
    private boolean directory=false; // -d --directory
    private boolean snapshotsave=false; // -ss --snapshotsave
    private boolean snapshotcompare=false; // -sc --snapshotcompare
    private boolean search=false; // -w --search
    private boolean order=false; // -o --order
    private boolean list=false; // -l --list
    private boolean by=false; // -b --by parameter for searchby
    private boolean error=false;

    public Console(String[] args) {
        for(String arg : args) {
        switch (arg) {
            case "--help": help=true; if(help) error=true;
                break;
            case "-h": help=true;if(help) error=true;
                break;
            case "-i": info=true;if(info) error=true;
                break;
            case "--info": info=true;if(info) error=true;
                break;
            case "-s": stat=true;if(stat) error=true;
                break;
            case "--stat": stat=true;if(stat) error=true;
                break;
            case "--file": file=true;if(file) error=true;
                break;
            case "-f": file=true;if(file) error=true;
                break;
            case "--directory": directory=true;if(directory) error=true;
                break;
            case "-d": directory=true;if(directory) error=true;
                break;
            case "--snapshotsave": snapshotsave=true;if(snapshotsave) error=true;
                break;
            case "-ss": snapshotsave=true;if(snapshotsave) error=true;
                break;
            case "-sc": snapshotcompare=true;if(snapshotcompare) error=true;
                break;
            case "--snapshotcompare": snapshotcompare=true;if(snapshotcompare) error=true;
                break;
            case "--search": search=true;if(search) error=true;
                break;
            case "-w": search=true;if(search) error=true;
                break;
            case "--order": order=true;if(order) error=true;
                break;
            case "-o": order=true;if(order) error=true;
                break;
            case "--list": list=true;if(list) error=true;
                break;
            case "-l": list=true;if(list) error=true;
                break;
            case "--by": by=true;if(by) error=true;
                break;
            case "-b": by=true;if(by) error=true;
                break;
            default:
                if(arg.contains("-"))error=true;

                // ATTENTION D'AUTRES CAS A GERER EN PLUS ICI !!!!



        } //Checking what commands has been written.
        }

        // ---- gestion erreurs !
        if(error) //Afficher erreur;
        if(file && directory) //afficher erreur car les deux sont exclusif;
        // --- Fin traitement erreurs de base


        // --- Début traitement commandes


        if(help){
            System.out.println("Usage: java -jar cli.jar [options]");
            System.out.println("\n");
            System.out.println("Desc:");
            System.out.println(
                    """
                    This scripts can :
                        Show you informations about a File or a Folder.
                        Show you what file and folder are inside a folder.
                        Search a specific file from where you are using that commands.
                        Make a save of a specific folder.
                        Compare an old save with a current folder.
                    """);
            System.out.println("Options:");
            System.out.println(
                    """
                        -h, --help : Show this message.
                        -f, --file : To specify the file you want to work with (can't be used with -d or --directory).
                        -d, --directory : To specify the directory you want to work with (can't be used with -f or --file).
                        -i, --info : Show info about the file or directory.
                        -s, --stat : Show statistics about the file or directory.
                        
                        !!!
                        -w, --search : Show a list of images that matchs the research (must be used with ). ????? BIG QUESTIONS HERE GUYS
                        -b, --by : Search a specific image that matches specific type (must be used with -w or --search).
                        !!!
                        
                        -l, --list : Show all the images inside the folder and the folder deeper (must be used with -d or --directory). 
                        -o, --order : Show an ordered list of images (must be used with -l or --list);
                        -ss, --snapshotsave : Make a save of the state of a directory.
                        -sc, --snapshotcompare : Compare a save of a directory with the current directory.
                    
                    """);
            System.out.println("Exemples:");
            System.out.println(
                    """
                    We have to add Exemple
                    """);
            System.out.println("Commons errors:");
            System.out.println(
                    """
                    We have to add Exemple
                    """);
            System.out.println("Autors: Ammad Kennan & Defrance Gauthier.");

        }

        if(search&&file&&(!(list||snapshotcompare||snapshotsave||order   )    )      ){//Lancer la commande de recherche d'un fichier
            if(by){} //Va afficher selon un mot clé la manière dont il va trier les fichiers
            else{} //par défaut
        }

        if(info&&directory){} //get les infos du directory
        if(stat&&directory){} //get les stat du directory

        if(info&&file){}//get les infos du file
        if(stat&&file){}//get les stat du file

        if(directory&&list){ //afficher
        if(order){} //va lister les fichiers selon un certains order
        else{} // par défaut
    }

        if(snapshotsave){}//sauvegarde l'état du fichier et l'identifie via son path a une addresse donné

        if(snapshotcompare){}//compare le fichier avec un path donné
        /* Attention, si le path donné a un / au début c'est un chemin absolue donc traitement classique
           Mais si il n'y a pas de / au début (c'est un chemin relatif),
           alors on ajoute au path donné le chemin absolue du fichier depuis lequel est lancé la console.
         * */

    }

    public String getHelp(){}

    public String getInfoDir(){}
    public String getInfoFile(){}
    public String getStatDir(){}
    public String getStatFile(){}

    public String search(){}
    public String searchby(){}

    public String getList(){}
    public String getOrderedList(){}

    public void doSnapshotSave(){}
    public String doSnapshotCompare(){}

}
