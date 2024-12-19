package Core;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;

public class FolderReserialized {
    ArrayList<String> folders;
    ArrayList<Image> fichiers;
    HashMap<String, String> data;

    public FolderReserialized(){
        folders = new ArrayList<String>();
        fichiers = new ArrayList<Image>();
        data = new HashMap<String, String>();
    }

    @JsonProperty("absolutePath")
    public void setAbsolutePath(String absolutePath){data.put("absolutePath", absolutePath);}
    public String getAbsolutePath(){return data.get("absolutePath");}

    @JsonProperty("Last Modification")
    public void setLastModified(String lastModified){data.put("Last Modification", lastModified);}
    public String getLastModified(){return data.get("Last Modification");}

    @JsonProperty("parent")
    public void setParent(String parent){data.put("parent", parent);}
    public String getParent(){return data.get("parent");}

    @JsonProperty("Number of elements")
    public void setNumberOfElements(String NumberOfElements){data.put("Number of elements", NumberOfElements);}
    public String getNumberOfElements(){return data.get("Number of elements");}

    @JsonProperty("folders")
    public void setFolders(ArrayList<String> folders){this.folders = folders;}
    public ArrayList<String> getFolders(){return folders;}

    @JsonProperty("files")
    public void setFiles(ArrayList<Image> tmp){this.fichiers = tmp;}
    public ArrayList<Image> getFiles(){return fichiers;}

    @JsonProperty("Number of folders")
    public void setNumberOfFolders(String NumberOfFolders){data.put("Number of folders", NumberOfFolders);}
    public String getNumberOfFolders(){return data.get("Number of folders");}

    @JsonProperty("Number of images")
    public void setNumberOfImages(String NumberOfImages){data.put("Number of images", NumberOfImages);}
    public String getNumberOfImages(){return data.get("Number of images");}


}
