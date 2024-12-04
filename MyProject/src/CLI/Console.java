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
            case "--help": help=true;
                break;
            case "-h": help=true;
                break;
            case "-i": info=true;
                break;
            case "--info": info=true;
                break;
            case "-s": stat=true;
                break;
            case "--stat": stat=true;
                break;
            case "--file": file=true;
                break;
            case "-f": file=true;
                break;
            case "--directory": directory=true;
                break;
            case "-d": directory=true;
                break;
            case "--snapshotsave": snapshotsave=true;
                break;
            case "-ss": snapshotsave=true;
                break;
            case "-sc": snapshotcompare=true;
                break;
            case "--snapshotcompare": snapshotcompare=true;
                break;
            case "--search": search=true;
                break;
            case "-w": search=true;
                break;
            case "--order": order=true;
                break;
            case "-o": order=true;
                break;
            case "--list": list=true;
                break;
            case "-l": list=true;
                break;
            case "--by": by=true;
                break;
            case "-b": by=true;
                break;
            default: error=true;
        } //Checking what commands has been written.
        }

        // ---- gestion erreurs !
        if(error) //Afficher erreur;
        if(file && directory) //afficher erreur car les deux sont exclusif;
        // --- Fin traitement erreurs de base


        // --- Début traitement commandes
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
