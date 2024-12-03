package CLI;

public class Console {

    public static void main(String[] args) {

        boolean help=false; // -h --help
        boolean info=false; // -i --info
        boolean stat=false; // -s --stat
        boolean file=false; // -f --file
        boolean directory=false; // -d --directory
        boolean snapshotsave=false; // -ss --snapshotsave
        boolean snapshotcompare=false; // -sc --snapshotcompare
        boolean search=false; // -w --search
        boolean order=false; // -o --order
        boolean list=false; // -l --list
        boolean error=false;

        for (String arg : args) {
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
                default: error=true;
            } //Checking what commands has been written.
            }

        // ---- gestion erreurs !
        if(error) //Afficher erreur;
        if(file && directory) //afficher erreur car les deux sont exclusif;

        // --- Fin traitement erreurs de base


        // --- Début traitement commandes
        if(search&&file&&(!(   )    )      ){}




    }




}
