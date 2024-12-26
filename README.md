# Projet de L2-Informatique Java Kenan et Gauthier A la faculté de Cergy-Pontoise

Explorateur de fichiers & métadonnées

Nom projet : Eagle Extractor

Format supporté : Jpg, Png, Webp, Gif

Ceci est un projet fait par des L2 de la faculté d'informatique de Cergy-Pointoise (CY),
ayant pour objectif la création d'un CLI et d'un GUI permettant de répondre au cahier des charges données par les professeurs.
Le CLI et le GUI sont indépendant mais ont en communs un package qu'on appelle le Core.

*CLI : Command-line interface* ; *GUI : Graphical User Interface*

## Utilisation

Après avoir téléchargé les librairies dans releases,
vous pouvez taper les commandes :

java -jar cli.jar [vos paramètres]
afin de lancer la console.


java -jar gui.jar 
afin de lancer le gui.

## Description CLI & GUI

Le CLI a de nombreuses fonctions :
* --help qui permet de lister les commandes existentes et leur utilisations.
* --list qui permet de lister toutes les images aux bons formats sur les sous dossiers.
* --order qui permet de trier les éléments selon certaines conditions
* --search qui permet de chercher des éléments selon certaines conditions.
* --snapshotsave & --snapshotcompare permettent la sauvegarde de l'état d'un dossier et sa comparaison via une sérialiasion dans un JSON


Le GUI a également de nombreuses fonctions :
* Globablement les mêmes possibilités que le CLI mais avec une interface graphique
* Bouton Help visible au démarage qui explique comment utiliser l'application
* la possibilités de visualier les images et dé/zoomer dessus
* Une interface original et intuitive

## Logo
Nous avons également fait notre propre logo pour l'application, inspiré de "Paper please".

![Image du logo](https://github.com/GauthierDefrance/Images-metadonnees-avec-Java/blob/main/sources/icon.png)

## Prérequis
Version de Java utilisé :
Java 21.0.3
*Nous ne garantissons pas le fonctionnement dans des versions antérieurs*


## Librairies
Librairies utilisés:
- twelvemonkeys
- jackson
- drewnoakes
- commons-cli
