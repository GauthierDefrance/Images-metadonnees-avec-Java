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

public class GUI extends JFrame {

	private JPanel rPanel = new JPanel();

	private int page = 0;
	private int searchSelector= 0;
	private String orderSelector = "name";
	private Boolean orderBool = false;

	private ArrayList<String> lstd = new ArrayList<String>();
	private ArrayList<String> lstf = new ArrayList<String>();
	private ArrayList<String> lst = new ArrayList<String>();

	private JButton[][] tb = new JButton[4][8];
	private JPanel centerPanel = new JPanel();

	private JTextField pathT = new JTextField(50);
	private JTextField searchT = new JTextField(50);
	private JTextField snapchotSaveT = new JTextField(50);
	private JTextField snapchotCompareT = new JTextField(50);


	public GUI(String title) {
		super(title);
		init();
		// ImageIcon icon = new ImageIcon(getClass().getResource("/icons/icon.png"));
		ImageIcon icon = new ImageIcon(getClass().getResource("/icons/icon.png")); // Pour le JAR
		this.setIconImage(icon.getImage());
	}

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
							Image scaledImg = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
							tb[i][j].setIcon(new ImageIcon(scaledImg));
							tb[i][j].addMouseListener(new DoubleClicListenerD(lst.get(p))); // Ajouter le même ActionListener à chaque
						} else {
							ImageIcon icon2;
							icon2 = new ImageIcon(ImageIO.read(new File(lst.get(p))));
							Image img = icon2.getImage();
							Image scaledImg = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
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

	private void pathupdate() {

		String path = pathT.getText();
		//On réinitialise les listes c
		lstd.clear();
		lstf.clear();
		lst.clear();
		//Création de l'objet folder à l'endroit indiqué
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
			} catch (Exception e) {System.out.println(e);}
		}
		centerPanel.updateUI();
	}

	private class ppathupdate implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			page = 0;
			pathupdate();
			centerPanel.updateUI();
		}

	}

	private class pathremonter implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String path = pathT.getText();//donne le path actuel
			File file = new File(path);
			if (file.exists()&&file.isDirectory()) {
				try {
					String parent = Paths.get(pathT.getText()).getParent().toString(); //Donne le parent
					pathT.setText(parent);
					page = 0;
					pathupdate();
				} catch (Exception ex) {}
			}
			centerPanel.updateUI();
		}
	}

	private class searchSelectorB implements ActionListener {
		private int selector;

		public searchSelectorB(int selector) {
			this.selector = selector;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			searchSelector = this.selector;
		}

	}

	private class searchf implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String path = pathT.getText();//donne le path actuel
			File file = new File(path);
			if (file.exists() && file.isDirectory()) {
				Folder folder = new Folder(path);
				SearchFolder chercheur = new SearchFolder(folder);
				ArrayList<File> images = new ArrayList<File>();
				try {
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
					lst.clear();
					lstf.clear();
					lstd.clear();
					page = 0;
					for (File f : images) {
						lst.add(f.getAbsolutePath());
					}
					lstf.addAll(lst);
					updateTb();
				} catch (Exception ex) {System.out.println(ex);}
				centerPanel.updateUI();
			}

		}
	}

	private class orderSelectorB implements ActionListener {
		private String order;

		public orderSelectorB(String order) {
			this.order = order;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			orderSelector = this.order;

		}

	}

	private class orderf implements ActionListener {

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
			} catch (Exception ex) {System.out.println(ex);}

		}
	}

	private class orderReverse implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (orderBool) {
				orderBool = false;
			}
			else {
				orderBool = true;
			}
		}

	}

	private class upAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (page > 0) {
				page--;
				try {
				updateTb();
				} catch (IOException ex) {System.out.println(ex);}
				centerPanel.updateUI();
			}
		}

	}

	private class downAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(lst.size()>(page+1) * 32){
				page++;
                try {
                    updateTb();
                } catch (IOException ex) {System.out.println(ex);}
                centerPanel.updateUI();
			}
		}

	}

	private class snapchotSaveD implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				String currentPath = pathT.getText();
				String givenPath = snapchotSaveT.getText();

				File file = new File(currentPath);
				File file2 = new File(givenPath);
				File parentFile2 = file2.getParentFile();

				if (givenPath.endsWith(".json") && file.exists() && file.isDirectory() && parentFile2.exists() && parentFile2.isDirectory() ) {
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

	private class snapchotCompareD implements ActionListener {

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

	private class stats implements ActionListener {
		private String path;

		public stats(String path) {
			this.path = path;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				String affiche = "";
				File file = new File(path);
				if (file.exists() && file.isDirectory()) {
					Folder folder = new Folder(path);
					affiche=folder.getStat();
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

	private class info implements ActionListener {
		private String path;

		public info(String path) {
			this.path = path;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				String affiche = "";
				File file = new File(path);
				if (file.exists() && file.isDirectory()) {
					Folder folder = new Folder(path);
					affiche=folder.getInfo();
				} else {
					Core.Image image = new Core.Image(path);
					image.initMetadata();
					affiche = image.getAllMetadata();
				}
				new MetaDataImage(affiche);
			} catch (Exception error) {
				System.out.println(error);
			}
		}
	}

	// Classe privée pour gérer le clic droit et afficher le JPopupMenu
	private class ClicDroitListener extends MouseAdapter {
		private String path;
		private JPopupMenu menu = new JPopupMenu();
		private JMenuItem option1 = new JMenuItem("stats");
		private	JMenuItem option2 = new JMenuItem("info");

		public ClicDroitListener(String path) {
			this.path = path;
			this.menu.add(option1);
			this.menu.add(option2);
			option1.addActionListener(new stats(path));
			option2.addActionListener(new info(path));

		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {  // Clic droit
				menu.show(e.getComponent(), e.getX(), e.getY());

			}
		}
	}

	// Classe privée pour gérer le double-clic
	private class DoubleClicListenerD extends MouseAdapter {
		private String path;

		public DoubleClicListenerD(String path) {
			this.path = path;
		}

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

	private class DoubleClicListenerF extends MouseAdapter {
		private String path;

		public DoubleClicListenerF(String path) {
			this.path = path;
		}

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

	private class helpPopup implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			new MetaDataImage("""
					¤ Help Menu
					
					Bienvenue sur l'Interface Graphique "Eagle Extractor",
					programme conçu pour la visualisation des images au format : {PNG, JPG, WEBP & GIF}
					
					Merci d'avoir lu l'aide
					Par Kennan & Gauthier
					"""
			);
		}

	}

	private class QuitAction implements ActionListener {
		//Window to be closed.
		private JFrame window;

		public QuitAction(JFrame window) {
			this.window = window;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			window.dispose();
		}

	}

	public static void main(String[] args) {
		new GUI("Eagle Extractor");

	}
}