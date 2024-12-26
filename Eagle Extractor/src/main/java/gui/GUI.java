package gui;

import Core.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Classe principale pour exécuter l'interface graphique.
 * Cette classe est utilisée pour initialiser et exécuter des opérations graphiques selon les cliques de l'utilisateur.
 * @author @Kenan Ammad @Gauthier Defrance
 * @version 1.1 [26/12/2024]
 */
public class GUI extends JFrame {

	/**
	 * Panneau utilisé pour afficher un autre composant ou une interface secondaire dans l'application.
	 */
	private JPanel rPanel = new JPanel();

	/**
	 * Page actuelle, utilisée pour la gestion de la pagination des fichiers affichés.
	 */
	private int page = 0;

	/**
	 * Sélecteur de recherche, utilisé pour déterminer le critère de recherche actif.
	 */
	private int searchSelector = 0;

	/**
	 * Sélecteur de tri, utilisé pour déterminer le critère de tri des fichiers (par défaut, trié par nom).
	 */
	private String orderSelector = "name";

	/**
	 * Booléen qui détermine l'ordre de tri (vrai pour un ordre inverse, faux pour l'ordre standard).
	 */
	private Boolean orderBool = false;

	/**
	 * Liste des chemins de dossiers à afficher.
	 */
	private ArrayList<String> lstd = new ArrayList<String>();

	/**
	 * Liste des chemins d'images à afficher.
	 */
	private ArrayList<String> lstf = new ArrayList<String>();

	/**
	 * Liste combinée des chemins des dossiers et des images à afficher.
	 */
	private ArrayList<String> lst = new ArrayList<String>();

	/**
	 * Tableau de boutons représentant les éléments dans la grille d'affichage.
	 */
	private JButton[][] tb = new JButton[4][8];

	/**
	 * Panneau central de l'interface utilisateur où les boutons sont ajoutés.
	 */
	private JPanel centerPanel = new JPanel();

	/**
	 * Champ de texte pour afficher et modifier le chemin du répertoire actuel.
	 */
	private JTextField pathT = new JTextField(50);

	/**
	 * Champ de texte pour entrer une requête de recherche.
	 */
	private JTextField searchT = new JTextField(50);

	/**
	 * Champ de texte pour entrer le chemin de sauvegarde du fichier de snapshot.
	 */
	private JTextField snapchotSaveT = new JTextField(50);

	/**
	 * Champ de texte pour entrer le chemin du fichier de comparaison de snapshot.
	 */
	private JTextField snapchotCompareT = new JTextField(50);


	/**
	 * Constructeur de la classe GUI qui initialise l'interface graphique avec le titre spécifié.
	 * Il charge également l'icône de l'application à partir du fichier ressources "/icons/icon.png".
	 *
	 * @param title Titre de la fenêtre de l'application.
	 */
	public GUI(String title) {
		super(title);
		init();
		// ImageIcon icon = new ImageIcon(getClass().getResource("/icons/icon.png"));
		ImageIcon icon = new ImageIcon(getClass().getResource("/icons/icon.png")); // Pour le JAR
		this.setIconImage(icon.getImage());
	}

	/**
	 * Initialise l'interface graphique en configurant les différents panneaux, boutons,
	 * menus, et autres composants de l'application.
	 * Cette méthode crée et organise les éléments suivants :
	 *
	 *   Les panneaux de la fenêtre, y compris les panneaux gauche, droit, supérieur et inférieur.
	 *   La barre de menu avec des options pour la recherche, l'ordre des fichiers et les snapshots.
	 *   Les boutons d'interaction avec l'utilisateur tels que la recherche, la navigation, et la gestion des snapshots.
	 *   Les listeners d'événements pour les actions des boutons et des éléments de menu.
	 *   La gestion du répertoire de travail, en affichant les dossiers et fichiers dans le panneau central.
	 *
	 * Cette méthode est appelée lors de l'initialisation de la fenêtre principale.
	 */
	private void init() {
		BorderLayout border = new BorderLayout();
		JPanel rightPanel = new JPanel();
		JPanel leftPanel = new JPanel();
		JPanel topbottomPanel = new JPanel();
		JPanel topPanel = new JPanel();
		JPanel bottomPanel = new JPanel();

		JMenuBar toptopPanel = new JMenuBar();

		JMenu parametres = new JMenu("Paramètres");
		JMenu menuSearchByParametres = new JMenu("Menu SearchBy ...");
		JMenu menuOrderByParametres = new JMenu("Menu OrderBy ...");
		JMenu menuSnapchotParametres = new JMenu("Menu Snapchot ...");
		JMenuItem quitButton = new JMenuItem("Quit");

		JButton help = new JButton("Help ?");
		JButton order = new JButton("Order:");
		JButton search = new JButton("Search :");
		JButton pathButton = new JButton("Path :");
		JButton pathParent = new JButton("←");
		JButton up = new JButton("↑");
		JButton down = new JButton("↓");
		JButton snapchotSaveButton = new JButton("Snapchot Save:");
		JButton snapchotCompareButton = new JButton("Snapchot Compare:");
		ButtonGroup buttonGroup = new ButtonGroup();
		ButtonGroup buttonGroup2 = new ButtonGroup();

		centerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		centerPanel.setBackground(Color.ORANGE);
		up.setBackground(Color.DARK_GRAY);
		up.setForeground(Color.WHITE);
		down.setBackground(Color.DARK_GRAY);
		down.setForeground(Color.WHITE);
		leftPanel.setBackground(Color.DARK_GRAY);

		Container contentPane = getContentPane();
		contentPane.setLayout(border);
		topPanel.setLayout(new GridLayout(2, 1));
		rightPanel.setLayout(new GridLayout(2, 1));
		topbottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		topPanel.add(toptopPanel);
		topPanel.add(topbottomPanel);
		toptopPanel.add(parametres);
		toptopPanel.add(help);
		toptopPanel.add(search);
		toptopPanel.add(searchT);
		topbottomPanel.add(pathParent);
		topbottomPanel.add(pathButton);
		topbottomPanel.add(pathT);
		rightPanel.add(up);
		rightPanel.add(down);

		parametres.add(menuSearchByParametres);
		parametres.add(menuOrderByParametres);
		parametres.add(menuSnapchotParametres);

		menuSnapchotParametres.add(snapchotSaveButton);
		menuSnapchotParametres.add(snapchotSaveT);
		menuSnapchotParametres.add(snapchotCompareButton);
		menuSnapchotParametres.add(snapchotCompareT);
		parametres.addSeparator();
		parametres.add(quitButton);

		JRadioButtonMenuItem itemorBOOL = new JRadioButtonMenuItem("Reverse");
		JRadioButtonMenuItem item1or = new JRadioButtonMenuItem("Name");
		JRadioButtonMenuItem item2or = new JRadioButtonMenuItem("Height");
		JRadioButtonMenuItem item3or = new JRadioButtonMenuItem("Width");
		JRadioButtonMenuItem item4or = new JRadioButtonMenuItem("Date");
		JRadioButtonMenuItem item5or = new JRadioButtonMenuItem("Size");
		JRadioButtonMenuItem item1 = new JRadioButtonMenuItem("Name");
		JRadioButtonMenuItem item2 = new JRadioButtonMenuItem("Height");
		JRadioButtonMenuItem item3 = new JRadioButtonMenuItem("Max Height");
		JRadioButtonMenuItem item4 = new JRadioButtonMenuItem("Min Height");
		JRadioButtonMenuItem item5 = new JRadioButtonMenuItem("Width");
		JRadioButtonMenuItem item6 = new JRadioButtonMenuItem("Max Width");
		JRadioButtonMenuItem item7 = new JRadioButtonMenuItem("Min Width");
		JRadioButtonMenuItem item8 = new JRadioButtonMenuItem("Desc");
		JRadioButtonMenuItem item9 = new JRadioButtonMenuItem("Date");
		JRadioButtonMenuItem item10 = new JRadioButtonMenuItem("Max Size");
		JRadioButtonMenuItem item11 = new JRadioButtonMenuItem("Min Size");


		menuOrderByParametres.add(order);
		menuOrderByParametres.addSeparator();
		menuOrderByParametres.add(itemorBOOL);
		menuOrderByParametres.addSeparator();
		buttonGroup2.add(item1or);
		menuOrderByParametres.add(item1or);
		buttonGroup2.add(item2or);
		menuOrderByParametres.add(item2or);
		buttonGroup2.add(item3or);
		menuOrderByParametres.add(item3or);
		buttonGroup2.add(item4or);
		menuOrderByParametres.add(item4or);
		buttonGroup2.add(item5or);
		menuOrderByParametres.add(item5or);

		buttonGroup.add(item1);
		menuSearchByParametres.add(item1);
		buttonGroup.add(item2);
		menuSearchByParametres.add(item2);
		buttonGroup.add(item3);
		menuSearchByParametres.add(item3);
		buttonGroup.add(item4);
		menuSearchByParametres.add(item4);
		buttonGroup.add(item5);
		menuSearchByParametres.add(item5);
		buttonGroup.add(item6);
		menuSearchByParametres.add(item6);
		buttonGroup.add(item7);
		menuSearchByParametres.add(item7);
		buttonGroup.add(item8);
		menuSearchByParametres.add(item8);
		buttonGroup.add(item9);
		menuSearchByParametres.add(item9);
		buttonGroup.add(item10);
		menuSearchByParametres.add(item10);
		buttonGroup.add(item11);
		menuSearchByParametres.add(item11);


		itemorBOOL.addActionListener(new orderReverse());
		item1or.addActionListener(new orderSelectorB("name"));
		item2or.addActionListener(new orderSelectorB("height"));
		item3or.addActionListener(new orderSelectorB("width"));
		item4or.addActionListener(new orderSelectorB("date"));
		item5or.addActionListener(new orderSelectorB("size"));

		item1.addActionListener(new searchSelectorB(1));
		item2.addActionListener(new searchSelectorB(2));
		item3.addActionListener(new searchSelectorB(3));
		item4.addActionListener(new searchSelectorB(4));
		item5.addActionListener(new searchSelectorB(5));
		item6.addActionListener(new searchSelectorB(6));
		item7.addActionListener(new searchSelectorB(7));
		item8.addActionListener(new searchSelectorB(8));
		item9.addActionListener(new searchSelectorB(9));
		item10.addActionListener(new searchSelectorB(10));
		item11.addActionListener(new searchSelectorB(11));

		up.addActionListener(new upAction());
		down.addActionListener(new downAction());
		pathButton.addActionListener(new ppathupdate());
		pathParent.addActionListener(new pathremonter());
		search.addActionListener(new searchf());
		order.addActionListener(new orderf());
		snapchotSaveButton.addActionListener(new snapchotSaveD());
		snapchotCompareButton.addActionListener(new snapchotCompareD());
		help.addActionListener(new helpPopup());

		String path =System.getProperty("user.dir"); //Renvoit l'endroit depuis ou est lancé le répertoire
		pathT.setText(path);

		File file = new File(path);
		if (file.exists() && file.isDirectory()) {
			Folder folder = new Folder(path);

			for (File f : folder.getFolders()) {
				lstd.add(f.getAbsolutePath());
			}

			for (File f : folder.getImages()) {
				lstf.add(f.getAbsolutePath());
			}
			lst.addAll(lstd);
			lst.addAll(lstf);
			try {
				updateTb();
			}catch (Exception e) {System.out.println(e);}
		}


		contentPane.add(BorderLayout.CENTER, centerPanel);
		contentPane.add(BorderLayout.NORTH, topPanel);
		contentPane.add(BorderLayout.SOUTH, bottomPanel);
		contentPane.add(BorderLayout.EAST, rightPanel);
		contentPane.add(BorderLayout.WEST, leftPanel);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		quitButton.addActionListener(new QuitAction(this));
		pack();
		setLocationRelativeTo(null);
		setSize(720, 480);
		setVisible(true);
	}



	/**
	 * Met à jour le tableau de boutons dans le panneau central, affichant les fichiers sous forme de boutons.
	 * Chaque bouton représente soit un dossier, soit une image, avec des icônes et des noms associés.
	 * La méthode gère le placement des boutons en fonction de la page actuelle et initialise les actions de double-clic
	 * et de clic droit pour chaque élément.
	 *
	 * @throws IOException Si une erreur survient lors du chargement des icônes ou des images.
	 */
	private void updateTb() throws IOException {
		centerPanel.removeAll();
		centerPanel.setLayout(new GridLayout(4, 8, 5, 5));
		int p = page * 32;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 8; j++) {
				if (lst.size() > p) {
					// Récupérer le nom du fichier depuis le chemin
					String fileName = Paths.get(lst.get(p)).getFileName().toString();
					tb[i][j] = new JButton(fileName); // Nom des boutons

					tb[i][j].setHorizontalTextPosition(SwingConstants.CENTER); // Texte centré horizontalement
					tb[i][j].setVerticalTextPosition(SwingConstants.BOTTOM);  // Texte en bas

					if (!(lstd.size() <= p)) {
						ImageIcon icon1 = new ImageIcon(getClass().getResource("/icons/icon.png"));
						Image img = icon1.getImage();
						Image scaledImg = img.getScaledInstance(150, 150, Image.SCALE_FAST);
						tb[i][j].setIcon(new ImageIcon(scaledImg));
						tb[i][j].addMouseListener(new DoubleClicListenerD(lst.get(p))); // Ajouter le même ActionListener à chaque
					} else {
						ImageIcon icon2;
						icon2 = new ImageIcon(ImageIO.read(new File(lst.get(p))));
						Image img = icon2.getImage();
						Image scaledImg = img.getScaledInstance(150, 150, Image.SCALE_FAST);
						tb[i][j].setIcon(new ImageIcon(scaledImg));
						tb[i][j].addMouseListener(new DoubleClicListenerF(lst.get(p)));
					}
					// Ajouter un MouseListener pour détecter le double-clic et le clic droit
					tb[i][j].addMouseListener(new ClicDroitListener(lst.get(p)));

					tb[i][j].setBackground(new Color(128, 128, 128));

					centerPanel.add(tb[i][j]); // Ajouter chaque bouton au panel
				}
				p++;
			}
		}
	}

	/**
	 * Met à jour les listes de fichiers et de dossiers en fonction du chemin spécifié.
	 * Cette méthode réinitialise les listes existantes, puis parcourt le dossier indiqué
	 * pour ajouter les sous-dossiers et les images à leurs listes respectives.
	 * Elle met ensuite à jour l'affichage du contenu
	 * avec lance la méthode updateTb().
	 */
	private void pathupdate() {

		String path = pathT.getText();
		// Réinitialise les listes
		lstd.clear();
		lstf.clear();
		lst.clear();

		// Création de l'objet folder à l'endroit indiqué
		File file = new File(path);
		if (file.exists() && file.isDirectory()) {
			Folder folder = new Folder(path);

			// Ajoute les sous-dossiers à la liste lstd
			for (File f : folder.getFolders()) {
				lstd.add(f.getAbsolutePath());
			}

			// Ajoute les images à la liste lstf
			for (File f : folder.getImages()) {
				lstf.add(f.getAbsolutePath());
			}

			// Combine les listes de dossiers et d'images
			lst.addAll(lstd);
			lst.addAll(lstf);

			try {
				updateTb();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		centerPanel.updateUI();
	}

	/**
	 * Classe qui réinitialise la page à 0 et met à jour l'interface selon le contenu du dossier au chemin spécifié
	 * avec lance la méthode pathupdate().
	 */
	private class ppathupdate implements ActionListener {

		/**
		 * Réinitialise la page à 0 et met à jour le contenu du chemin.
		 * L'affichage est ensuite mis à jour
		 * avec lance la méthode pathupdate().
		 *
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			page = 0;
			pathupdate();
			centerPanel.updateUI();
		}
	}
	/**
	 * Classe qui permet de remonter d'un niveau dans l'arborescence des répertoires en affichant le chemin du répertoire parent.
	 */
	private class pathremonter implements ActionListener {

		/**
		 * Remonte d'un niveau dans l'arborescence des répertoires en mettant à jour le chemin affiché.
		 * La page est réinitialisée et l'affichage est mis à jour après la modification du chemin
		 * avec lance la méthode pathupdate().
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String path = pathT.getText(); // Donne le path actuel
			File file = new File(path);
			if (file.exists() && file.isDirectory()) {
				try {
					// Donne le chemin du répertoire parent
					String parent = Paths.get(pathT.getText()).getParent().toString();
					pathT.setText(parent);
					page = 0;
					pathupdate();
				} catch (Exception ex) {
					// Gère l'exception
				}
			}
			centerPanel.updateUI();
		}
	}
	/**
	 * Classe qui définit le critère de sélection de la recherche lorsqu'une action est déclenchée.
	 */
	private class searchSelectorB implements ActionListener {
		private int selector;

		/**
		 * Constructeur qui initialise le critère de sélection de la recherche.
		 *
		 * @param selector int contenant Le critère de sélection de la recherche.
		 */
		public searchSelectorB(int selector) {
			this.selector = selector;
		}

		/**
		 * Définit le critère de sélection de la recherche lorsqu'une action est déclenchée.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			searchSelector = this.selector;
		}
	}

	/**
	 * Classe qui effectue une recherche dans un dossier en fonction du critère sélectionné et met à jour l'affichage avec les résultats
	 * avec lance la méthode updateTb().
	 */
	private class searchf implements ActionListener {

		/**
		 * Effectue une recherche dans le dossier spécifié en fonction du critère de recherche sélectionné.
		 * Les résultats de la recherche sont ajoutés à la liste lst et l'affichage est mis à jour
		 * avec la méthode updateTb().
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String path = pathT.getText(); // Donne le path actuel
			File file = new File(path);
			if (file.exists() && file.isDirectory()) {
				Folder folder = new Folder(path);
				SearchFolder chercheur = new SearchFolder(folder);
				ArrayList<File> images = new ArrayList<File>();
				try {
					// Effectue la recherche en fonction du critère sélectionné
					switch (searchSelector) {
						case 1:
							images = chercheur.searchByName(searchT.getText());
							break;
						case 2:
							images = chercheur.searchByHeigth(searchT.getText());
							break;
						case 3:
							images = chercheur.searchByMaxHeigth(searchT.getText());
							break;
						case 4:
							images = chercheur.searchByMinHeigth(searchT.getText());
							break;
						case 5:
							images = chercheur.searchByWidth(searchT.getText());
							break;
						case 6:
							images = chercheur.searchByMaxWidth(searchT.getText());
							break;
						case 7:
							images = chercheur.searchByMinWidth(searchT.getText());
							break;
						case 8:
							images = chercheur.searchByDesc(searchT.getText());
							break;
						case 9:
							images = chercheur.searchByDate(searchT.getText());
							break;
						case 10:
							images = chercheur.searchByMaxSize(searchT.getText());
							break;
						case 11:
							images = chercheur.searchByMinSize(searchT.getText());
							break;
						default:
							images = chercheur.searchByName(searchT.getText());
							break;
					}
					// Mise à jour de la liste des fichiers
					lst.clear();
					lstf.clear();
					lstd.clear();
					page = 0;
					for (File f : images) {
						lst.add(f.getAbsolutePath());
					}
					lstf.addAll(lst);
					updateTb();
				} catch (Exception ex) {
					System.out.println(ex);
				}
				centerPanel.updateUI();
			}
		}
	}

	/**
	 * Classe qui définit le critère de sélection de l'ordre lorsque l'action est déclenchée.
	 */
	private class orderSelectorB implements ActionListener {
		private String order;

		/**
		 * Constructeur qui initialise le critère de sélection de l'ordre.
		 *
		 * @param order String contenant Le critère de sélection de l'ordre.
		 */
		public orderSelectorB(String order) {
			this.order = order;
		}

		/**
		 * Définit le critère de sélection de l'ordre lorsque l'action est déclenchée.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			orderSelector = this.order;
		}
	}

	/**
	 * Classe qui trie une liste de fichiers en fonction d'un ordre spécifié et met à jour l'affichage
	 * avec lance la méthode updateTb().
	 */
	private class orderf implements ActionListener {

		/**
		 * Trie les fichiers en fonction du critère de sélection d'ordre et de l'état de l'ordre.
		 * La liste de fichiers est mise à jour et l'affichage est rafraîchi après le tri
		 * avec la méthode updateTb().
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				ArrayList<File> tmp = new ArrayList<File>();
				for (String elem : lstf) {
					tmp.add(new File(elem));
				}
				order OrderObj = new order(tmp);
				ArrayList<File> result = OrderObj.OrderFile(orderSelector, orderBool);
				lst.clear();
				lstd.clear();
				for(File f : result) {
					lst.add(f.getAbsolutePath());
				}
				updateTb();
				centerPanel.updateUI();
			} catch (Exception ex) {
				System.out.println(ex);
			}
		}
	}

	/**
	 * Classe qui inverse l'état de la variable orderBool lorsqu'une action est déclenchée.
	 */
	private class orderReverse implements ActionListener {

		/**
		 * Inverse la valeur de orderBool à chaque fois que l'action est déclenchée.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (orderBool) {
				orderBool = false;
			} else {
				orderBool = true;
			}
		}
	}

	/**
	 * Classe qui gère l'action de navigation vers la page précédente dans une liste.
	 */
	private class upAction implements ActionListener {

		/**
		 * Change de page vers la précédente si la page actuelle est supérieure à 0,
		 * et lance la méthode updateTb().
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (page > 0) {
				page--;
				try {
					updateTb();
				} catch (IOException ex) {
					System.out.println(ex);
				}
				centerPanel.updateUI();
			}
		}
	}

	/**
	 * Classe qui gère l'action de navigation vers la page suivante dans une liste.
	 */
	private class downAction implements ActionListener {

		/**
		 * Change de page si des éléments supplémentaires sont disponibles,
		 * et lance la méthode updateTb().
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if(lst.size() > (page + 1) * 32) {
				page++;
				try {
					updateTb();
				} catch (IOException ex) {
					System.out.println(ex);
				}
				centerPanel.updateUI();
			}
		}
	}

	/**
	 * Classe interne enregistre une snapshot du répertoire actuel dans un fichier JSON.
	 */
	private class snapchotSaveD implements ActionListener {

		/**
		 * Sauvegarde une snapshot du répertoire actuel dans le fichier spécifié
		 * lorsque l'action est déclenchée. Si les conditions sont remplies,
		 * la snapshot est enregistrée et un message de confirmation est affiché.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				String currentPath = pathT.getText();
				String givenPath = snapchotSaveT.getText();

				File file = new File(currentPath);
				File file2 = new File(givenPath);
				File parentFile2 = file2.getParentFile();

				if (givenPath.endsWith(".json") && file.exists() && file.isDirectory() && parentFile2.exists() && parentFile2.isDirectory()) {
					Folder folder = new Folder(currentPath);
					Snapshot snap = new Snapshot(folder);
					snap.snapshotSave(givenPath);
					JOptionPane.showMessageDialog(null,"Le fichier json contenant la snapshot de:"+currentPath+"\nA été sauvegardé à l'endroit :"+givenPath);
				}
			} catch (Exception ex) {
				System.out.println(ex);
				JOptionPane.showMessageDialog(null,"Erreur lors de la sauvegarde du fichier.");
			}
		}
	}

	/**
	 * Classe interne qui compare un répertoire actuel avec un fichier snapshot et affiche le résultat.
	 */
	private class snapchotCompareD implements ActionListener {

		/**
		 * Effectue la comparaison entre le répertoire actuel et le fichier snapshot
		 * lorsque l'action est déclenchée. Si le fichier snapshot est valide,
		 * les différences sont affichées dans une fenêtre.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				String currentPath = pathT.getText();
				String givenPath = snapchotCompareT.getText();

				File file = new File(currentPath);
				File file2 = new File(givenPath);

				if (givenPath.endsWith(".json") && file.exists() && file.isDirectory() && file2.exists()) {
					Folder folder = new Folder(currentPath);
					Snapshot snap = new Snapshot(folder);
					new MetaDataImage(snap.snapshotCompare(givenPath).toString());
				}
			} catch (Exception ex) {
				System.out.println(ex);
				JOptionPane.showMessageDialog(null,"Erreur lors de la comparaison du fichier.");
			}
		}
	}

	/**
	 * Classe qui sert à afficher les statistiques d'un fichier ou d'un dossier donné.
	 */
	private class stats implements ActionListener {

		/**
		 * Chemin du fichier ou du dossier.
		 */
		private String path;

		/**
		 * Initialise la classe avec le chemin spécifié.
		 * @param path String contenant Chemin du fichier ou du dossier.
		 */
		public stats(String path) {
			this.path = path;
		}

		/**
		 * Affiche les statistiques du fichier ou du dossier dans une boîte de dialogue.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				String affiche = "";
				File file = new File(path);
				if (file.exists() && file.isDirectory()) {
					affiche = new Folder(path).getStat();
				} else {
					Core.Image image = new Core.Image(path);
					image.initMetadata();
					affiche = image.getStat();
				}
				JOptionPane.showMessageDialog(null, affiche);
			} catch (Exception error) {
				System.out.println(error);
			}
		}
	}

	/**
	 * Classe interne représentant un gestionnaire d'événements pour afficher les métadonnées
	 * d'un fichier ou d'un dossier en fonction du chemin fourni.
	 */
	private class info implements ActionListener {

		private String path;

		/**
		 * Constructeur de la classe info.
		 * @param path String contenant le chemin du fichier ou du dossier pour lequel les métadonnées doivent être affichées.
		 */
		public info(String path) {
			this.path = path;
		}

		/**
		 * Méthode appelée lorsqu'une action est déclenchée.
		 * Elle traite le chemin donné pour afficher les informations ou métadonnées.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				String affiche = "";
				File file = new File(path);

				// Vérifie si le chemin correspond à un dossier existant.
				if (file.exists() && file.isDirectory()) {
					Folder folder = new Folder(path);
					affiche = folder.getInfo();
				} else {
					// Sinon, traite le fichier en tant qu'image.
					Core.Image image = new Core.Image(path);
					image.initMetadata();
					affiche = image.getAllMetadata();
				}

				// Affiche les métadonnées dans une nouvelle fenêtre.
				new MetaDataImage(affiche);
			} catch (Exception error) {
				// Gère les exceptions en les affichant dans la console.
				System.out.println(error);
			}
		}
	}

	/**
	 * Classe qui gère l'affichage d'un menu contextuel
	 * lors d'un clic droit de la souris. Le menu propose deux options : "stats" et "info".
	 */
	private class ClicDroitListener extends MouseAdapter {
		private JPopupMenu menu = new JPopupMenu();
		private JMenuItem option1 = new JMenuItem("stats");
		private JMenuItem option2 = new JMenuItem("info");
		/**
		 * Constructeur qui initialise le menu contextuel avec ses options.
		 * @param path String contenant Chemin d'une image ou d'un dossier, utilisé par les actions des options.
		 */
		public ClicDroitListener(String path) {
			this.menu.add(option1);
			this.menu.add(option2);
			option1.addActionListener(new stats(path));
			option2.addActionListener(new info(path));
		}

		/**
		 * Affiche le menu contextuel lorsqu'un clic droit est détecté.
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {  // Clic droit
				menu.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}

	/**
	 * Classe interne qui gère les événements de double-clic
	 * sur un composant pour mettre à jour le chemin et réinitialiser l'affichage.
	 */
	private class DoubleClicListenerD extends MouseAdapter {
		private String path;
		/**
		 * Constructeur qui initialise le chemin associé à ce listener.
		 *
		 * @param path String contenant Chemin du répertoire.
		 */
		public DoubleClicListenerD(String path) {
			this.path = path;
		}

		/**
		 * Détecte un double-clic de la souris et met à jour le chemin, la page
		 * et lance la méthode pathupdate().
		 */
		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getClickCount() == 2) {  // Double-clic détecté
				pathT.setText(path);
				page = 0;
				pathupdate();
			}
			centerPanel.updateUI();
		}
	}

	/**
	 * Classe qui gère les événements de double-clic
	 * sur une image pour afficher celle-ci avec la classe ZoomImage.
	 */
	private class DoubleClicListenerF extends MouseAdapter {
		private String path;
		/**
		 * Constructeur qui initialise le chemin de l'image associée à ce listener.
		 *
		 * @param path String contenant Chemin de l'image.
		 */
		public DoubleClicListenerF(String path) {
			this.path = path;
		}

		/**
		 * Détecte un double-clic de la souris sur une image et ouvre la vue zoomée
		 * en appelant ZoomImage.
		 */
		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getClickCount() == 2) {  // Double-clic détecté
				try {
					new ZoomImage(path);
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		}
	}

	/**
	 * Classe actionneur qui affiche un popup avec un message d'aide en cas d'activation.
	 */
	private class helpPopup implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			new MetaDataImage("""
			------¤ Help Menu ¤------
					
					
			> Description du projet <
					
					
	Bienvenue sur l'Interface Graphique "Eagle Extractor", programme conçu pour
	la visualisation des images au format : {PNG, JPG, WEBP & GIF} et l'accès à leurs
	métadonnées.
	Le programme permet également la surveillance de données, via snapshot, qui permet
	la comparaison d'une sauvegarde de l'état d'un dossiers, ses sous dossiers et images
	avec un dossier actuel. Cela permet donc de vérifier si des fichiers ont été modifiés
	déplacé ou supprimé <<définitivement>>.
	On peut également accéder aux métadonnées personnel des images, afin d'avoir des
	renseignements à leur sujet tel que : la position GPS, la date de prise de la photo
	ou encore l'appareil utilisé, et bien plus encore.
	Il y a également une fonctionnalité permettant de chercher par différents
	paramètres les images respectant ce paramètre.
	On peut aussi trier les données selon des critères données tel que l'ordre alphabétique
	la taille, les dates.
					
			> Utilisation du projet <
					
					
			¤ Fonctionnement du Path :
					
	Dans votre interface graphique, vous remarquerez la présence de deux bars de recherches.
	La deuxième correspond à l'endroit ou vous vous trouvez sur votre machine. Il est possible
	de renseigner l'addresse que vous voulez observer (si elle existe). Il faut pour cela
	renseigner un Path valide et ensuite cliquer sur le bouton "Path:".
					
					
			¤ Fonctionnement de l'interface :
					
	> Vous avez dû remarquer lors du démarage de votre application, que des dossiers 
	était présent, il vous est possible d'ouvrir ses dossiers en double cliquant (clique gauche)
	sur le dossier voulu. Il est possible également de remonter l'arborescence avec la petite
	flèche (←) à gauche du bouton "Path:" vu précedemment.
	Certains élements sur votre écran, ne sont pas des dossiers. Mais des images avec un nom.
	En effectuant un double clique gauche sur l'image vous pourrez observer l'image avec grande
	précision, grâce aux fonctionnalités de Zoom intuitive implémentés.
	Sur vos dossiers et images vous pouvez faire un clique droit, cela vous ouvrira alors
	une petite interface vous permettant de faire un choix entre "Stats" et "Info".
	Stats vous ouvrira une petite fênetre avec les statistique de votre image et celle
	de votre dossier. Tandis que Info vous fournira les métadonnées sur une image et des 
	informations pertinentes pour un dossiers.
	Le bouton "Help ?" affiche ce menu.
					
	Dans votre interface graphique, vous remarquerez également la présence d'une case
	"Paramètres". Dans cette case vous pouvez y trouver 3 menus et un bouton.
	Le bouton "Quit" arrête le programme Eagle Extractor.
					
	> Le menu "SearchBy" ouvre un autre menu d'options. Parmis elles se trouvent :
	 {Name, Height, Max Height, Min Height, Width, Max Width, Min Width, Desc, Date, 
	Max Size, Min Size}. Une fois une des cases cochés, vous pourrez renseigner
	l'information que vous cherchez dans la barre de recherche du haut, à droite
	du bouton "Search:". Afin de lancer la recherche il vous faudra appuyer sur
	le bouton "Search:".
	Attention, Date doit être utilisé avec le format yyyy-MM-DD (format ISO-8601).
	 Il est possible d'écrire uniquement yyyy-MM ou MM-DD également. De plus, 
	toutes recherches en rapport à des nombres : width, height... ne doit pas contenir
	de caractères dans la barre de recherche mais uniquement des nombres.
					
	> Le menu "OrderBy" ouvre un autre menu d'options. Parmis elles se trouvent :
	 {Reverse, Name, Height, Width, Date, Size}. On remarque également la présence d'un bouton.
	De plus, Reverse est séparé des autres options. OrderBy peut être utilisé seul, mais 
	également avec SearchBy afin de faciliter la recherche. Pour utiliser OrderBy,
	il vous faudra selectionner l'option que vous desirez parmis celles en desous de reverse.
	Reverse permet d'inverser le résultat pour trier dans le sens inverse.
	Appuyer sur le bouton Order démarre le tri et actualise l'interface.
					
	> Le menu "Snapshot" ouvre un autre menu d'options. On y voit deux zones de texte et
	deux boutons. Le bouton "Snapshot Save:" permet de sauvegarder l'état du dossier et
	ses sous dossiers ou vous vous trouvez actuellement à un endroit précis si il existe.
	(Un message vous avertira si l'opération c'est déroulé normalement). Il vous faudra
	écrire votre fichier sous la forme : path\\to\\myfile\\name.json (le format DOIT
	être un ".json"). Vous écrirez donc ce chemin dans la barre de recherche en dessous
	du bouton. Puis appuyerez sur le bouton pour éxécuter l'opération.
	Un autre bouton nommé "Snapshot Compare:" est présent, il permet donc d'effectuer une
	comparaison entre le dossier où on se trouve actuellement et ses sous dossiers avec
	une sauvegarde compatible au format ".json". Il vous faudra donc renseigner le chemin
	vers votre fichier ".json" comme précédemment.
					
					
	Le programme est conçu pour un nombre de format limité, mais le nombre de format peut être
	facilement augmenter grâce à la flexibilité du code.
					
					
.					
	Merci d'avoir lu l'aide,
	Par Kenan & Gauthier | Groupe C4 | L2 Informatique CY Université.
				"""
			);
		}

	}

	/**
	 * Classe actionneur qui arrête le programme en cas d'activation.
	 */
	private class QuitAction implements ActionListener {
		//Window to be closed.
		private JFrame window;
		/**
		 * @param window Objet JFrame
		 */
		public QuitAction(JFrame window) {
			this.window = window;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			window.dispose();
		}

	}


	/**
	 * Méthode principale pour exécuter le GUI
	 */
	public static void main() {
		new GUI("Eagle Extractor");

	}
}