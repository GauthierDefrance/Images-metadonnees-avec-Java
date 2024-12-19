import core.Fichier;
import core.Folder;

public class FolderTester {
    public static void main(String[] args){
        Folder d = new Folder(args[0]);
        System.out.println(d.getAbsolutePath());
        System.out.println(d.getParent());
        System.out.println(d.getImages());
        System.out.println(d.getFolders());

    }

}
