package gui;

import Core.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

public class GUI extends JFrame {

	private int page = 0;
	private ArrayList<String> lstd = new ArrayList<String>();
	private ArrayList<String> lstf = new ArrayList<String>();
	private ArrayList<String> lst = new ArrayList<String>();
	private JButton help = new JButton("help ?");
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

		quitButton.addActionListener(new QuitAction(this));
	}

	private void init() {
		BorderLayout border = new BorderLayout();
		JPanel rightPanel = new JPanel();
		JPanel leftPanel = new JPanel();
		JMenuBar toptopPanel = new JMenuBar();
		JPanel topbottomPanel = new JPanel();
		JPanel topPanel = new JPanel();
		JPanel bottomPanel = new JPanel();


		centerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		centerPanel.setBackground(Color.GRAY);

		up.setBackground(Color.DARK_GRAY);
		up.setForeground(Color.WHITE);
		down.setBackground(Color.DARK_GRAY);
		down.setForeground(Color.WHITE);

		Container contentPane = getContentPane();
		contentPane.setLayout(border);

		topPanel.setLayout(new GridLayout(2, 1));
		topPanel.add(toptopPanel);
		topPanel.add(topbottomPanel);
		topbottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		toptopPanel.add(parametres);
		parametres.add(quitButton);

		toptopPanel.add(help);
		toptopPanel.add(search);
		toptopPanel.add(searchT);

		topbottomPanel.add(pathB2);
		topbottomPanel.add(pathB);
		topbottomPanel.add(pathT);

		rightPanel.setLayout(new GridLayout(2, 1));
		rightPanel.add(up);
		rightPanel.add(down);


		menu.add(option1);
		menu.add(option2);

		up.addActionListener(new upAction());
		down.addActionListener(new downAction());
		pathB.addActionListener(new pathupdate());

		String path= "C:\\Users\\defra\\Pictures\\Screenshots";

		File file = new File(path);
		if (file.exists()&&file.isDirectory()) {
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
		setSize(2000, 1000);
		setVisible(true);
	}

	private void updateTb() {
		centerPanel.removeAll();
		centerPanel.setLayout(new GridLayout(4, 8, 5, 5));
		int p = page * 32;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 8; j++) {
				if(lst.size()>p) {
				tb[i][j] = new JButton("Bouton " + (p + 1)); // Nom des boutons

				// Ajouter un MouseListener pour détecter le double-clic et le clic droit
				tb[i][j].addMouseListener(new ClicDroitListener(menu));
				tb[i][j].addMouseListener(new DoubleClicListener());
				tb[i][j].setBackground(Color.orange);
				/*tb[i][j].addActionListener(listener); // Ajouter le même ActionListener à chaque bouton*/
				centerPanel.add(tb[i][j]); // Ajouter chaque bouton au panel
				}
				p++;
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
	private class DoubleClicListener extends MouseAdapter {
		public DoubleClicListener() {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getClickCount() == 2) {  // Double-clic détecté
				JButton source = (JButton) e.getSource();
				System.out.println("Double-clic détecté sur " + source.getText());
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
			if(lst.size()>((page+1) * 32)){
				page++;
				updateTb();
				centerPanel.updateUI();
			}
		}

	}

	private class pathupdate implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String path = pathT.getText();

			//Création de l'objet folder à l'endroit indiqué
			File file = new File(path);
			if (file.exists()&&file.isDirectory()) {
				Folder folder = new Folder(path);

				for(File f : folder.getFolders()) {
					lstd.add(f.getAbsolutePath());
				}

				for(File f : folder.getImages()) {
					lstf.add(f.getAbsolutePath());
				}


			}
			updateTb();
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