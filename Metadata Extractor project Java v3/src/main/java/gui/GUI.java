package gui;

import Core.*;
import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class GUI extends JFrame {

	private int page = 0;
	private int searchSelector= 0;
	private String orderSelector = "";
	private Boolean orderBool = Boolean.FALSE;
	private ArrayList<String> lstd = new ArrayList<String>();
	private ArrayList<String> lstf = new ArrayList<String>();
	private ArrayList<String> lst = new ArrayList<String>();
	private JButton help = new JButton("help ?");
	private JButton order = new JButton("Order:");
	private JButton search = new JButton("Search :");
	private JButton pathB = new JButton("Path :");
	private JButton pathB2 = new JButton("←");
	private JButton up = new JButton("↑");
	private JButton down = new JButton("↓");

	private JButton[][] tb = new JButton[4][8];
	private JPanel centerPanel = new JPanel();

	// Crée un JPopupMenu pour afficher le menu contextuel
	private JPopupMenu menu = new JPopupMenu();
	private JMenuItem option1 = new JMenuItem("Option 1");
	private JMenuItem option2 = new JMenuItem("Option 2");

	private JMenu parametres = new JMenu("Parametres");
	private JTextField pathT = new JTextField(50);
	private JTextField searchT = new JTextField(50);
	private JMenuItem quitButton = new JMenuItem("quit");


	public GUI(String title) {
		super(title);
		init();

		ImageIcon icon = new ImageIcon(".\\src\\main\\resources\\icon.png");
		this.setIconImage(icon.getImage());


		quitButton.addActionListener(new QuitAction(this));
	}

	private void init() {
		BorderLayout border = new BorderLayout();
		JPanel rightPanel = new JPanel();
		JPanel leftPanel = new JPanel();
		JMenuBar toptopPanel = new JMenuBar();
		JMenu menuParametres = new JMenu("Menu SearchBy ...");
		JMenu orderByParametres = new JMenu("Menu OrderBy ...");
		ButtonGroup buttonGroup = new ButtonGroup();
		ButtonGroup buttonGroup2 = new ButtonGroup();
		JPanel topbottomPanel = new JPanel();
		JPanel topPanel = new JPanel();
		JPanel bottomPanel = new JPanel();

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
		topPanel.add(toptopPanel);
		topPanel.add(topbottomPanel);
		topbottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		toptopPanel.add(parametres);
		parametres.add(menuParametres);
		parametres.add(orderByParametres);


		JRadioButtonMenuItem itemorBOOL = new JRadioButtonMenuItem("reverse");

		JRadioButtonMenuItem item1or = new JRadioButtonMenuItem("Name");
		JRadioButtonMenuItem item2or = new JRadioButtonMenuItem("Heigth");
		JRadioButtonMenuItem item3or = new JRadioButtonMenuItem("Width");
		JRadioButtonMenuItem item4or = new JRadioButtonMenuItem("Date");
		JRadioButtonMenuItem item5or = new JRadioButtonMenuItem("Size");

		JRadioButtonMenuItem item1 = new JRadioButtonMenuItem("Name");
		JRadioButtonMenuItem item2 = new JRadioButtonMenuItem("Heigth");
		JRadioButtonMenuItem item3 = new JRadioButtonMenuItem("MaxHeigth");
		JRadioButtonMenuItem item4 = new JRadioButtonMenuItem("MinHeigth");
		JRadioButtonMenuItem item5 = new JRadioButtonMenuItem("Width");
		JRadioButtonMenuItem item6 = new JRadioButtonMenuItem("MaxWidth");
		JRadioButtonMenuItem item7 = new JRadioButtonMenuItem("MinWidth");
		JRadioButtonMenuItem item8 = new JRadioButtonMenuItem("Desc");
		JRadioButtonMenuItem item9 = new JRadioButtonMenuItem("Date");
		JRadioButtonMenuItem item10 = new JRadioButtonMenuItem("MaxSize");
		JRadioButtonMenuItem item11 = new JRadioButtonMenuItem("MinSize");

		orderByParametres.add(order);
		orderByParametres.add(itemorBOOL);
		buttonGroup2.add(item1or);
		orderByParametres.add(item1or);
		buttonGroup2.add(item2or);
		orderByParametres.add(item2or);
		buttonGroup2.add(item3or);
		orderByParametres.add(item3or);
		buttonGroup2.add(item4or);
		orderByParametres.add(item4or);
		buttonGroup2.add(item5or);
		orderByParametres.add(item5or);


		buttonGroup.add(item1);
		menuParametres.add(item1);
		buttonGroup.add(item2);
		menuParametres.add(item2);
		buttonGroup.add(item3);
		menuParametres.add(item3);
		buttonGroup.add(item4);
		menuParametres.add(item4);
		buttonGroup.add(item5);
		menuParametres.add(item5);
		buttonGroup.add(item6);
		menuParametres.add(item6);
		buttonGroup.add(item7);
		menuParametres.add(item7);
		buttonGroup.add(item8);
		menuParametres.add(item8);
		buttonGroup.add(item9);
		menuParametres.add(item9);
		buttonGroup.add(item10);
		menuParametres.add(item10);
		buttonGroup.add(item11);
		menuParametres.add(item11);
		parametres.addSeparator();
		parametres.add(quitButton);
		toptopPanel.add(help);
		toptopPanel.add(search);
		toptopPanel.add(searchT);
		topbottomPanel.add(pathB2);
		topbottomPanel.add(pathB);
		topbottomPanel.add(pathT);
		menu.add(option1);
		menu.add(option2);
		rightPanel.setLayout(new GridLayout(2, 1));
		rightPanel.add(up);
		rightPanel.add(down);


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
		pathB.addActionListener(new ppathupdate());
		pathB2.addActionListener(new pathremonter());
		search.addActionListener(new searchf());
		order.addActionListener(new orderf());

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
			updateTb();
		}


		contentPane.add(BorderLayout.CENTER, centerPanel);
		contentPane.add(BorderLayout.NORTH, topPanel);
		contentPane.add(BorderLayout.SOUTH, bottomPanel);
		contentPane.add(BorderLayout.EAST, rightPanel);
		contentPane.add(BorderLayout.WEST, leftPanel);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setSize(720, 480);
		setVisible(true);
	}



	private void updateTb() {
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
						ImageIcon icon1 = new ImageIcon(".\\src\\main\\resources\\icon.png");
						Image img = icon1.getImage();
						Image scaledImg = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
						tb[i][j].setIcon(new ImageIcon(scaledImg));
						tb[i][j].addMouseListener(new DoubleClicListenerD(lst.get(p))); // Ajouter le même ActionListener à chaque
					} else {
						ImageIcon icon2 = new ImageIcon(lst.get(p));
						Image img = icon2.getImage();
						Image scaledImg = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
						tb[i][j].setIcon(new ImageIcon(scaledImg));
					}
					// Ajouter un MouseListener pour détecter le double-clic et le clic droit
					tb[i][j].addMouseListener(new ClicDroitListener(menu));

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
			updateTb();
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
				} catch (Exception ex) {
					System.out.println(ex);
				}
				updateTb();
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

		}

	}

	private class orderReverse implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (orderBool) {
				orderBool = Boolean.FALSE;
			}
			else {
				orderBool = Boolean.TRUE;
			}
		}

	}

	private class upAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (page > 0) {
				page--;
				updateTb();
				centerPanel.updateUI();
			}
		}

	}

	private class downAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(lst.size()>(page+1) * 32){
				page++;
				updateTb();
				centerPanel.updateUI();
			}
		}

	}

	// Classe privée pour gérer le clic droit et afficher le JPopupMenu
	private class ClicDroitListener extends MouseAdapter {
		private final JPopupMenu menu;

		public ClicDroitListener(JPopupMenu menu) {
			this.menu = menu;
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
		new GUI("GUI_C4");

	}
}