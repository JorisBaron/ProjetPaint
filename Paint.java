import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.event.MouseInputListener;


public class Paint extends JFrame {

	private static final long serialVersionUID = -679049976466349237L;
	
	private JComboBox<String> listeCouleurs;
	private JComboBox<String> listeFormes;
	private JTextField fieldTaille;
	
	private ArrayList<JToggleButton> listeToogleBut;
	
	private PanneauCentre panelDessin;
	
	/**
	 * Constructeur
	 */
	public Paint(){
		super("Projet Paint");
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//taille par defaut = moitie de l'ecran, centré
		//donc origne au quart de l'ecran
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(dim.width/4,dim.height/4,dim.width/2,dim.height/2);
		
		//initialise
		this.initialise();
		
		//par defaut en plein écran
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		
		//on affiche
		this.setVisible(true);
	}
	
	/**
	 * initialise la fenetre
	 */
	public void initialise(){
		//menu
		this.setJMenuBar(this.getMenu());
		
		//Border Layout
		this.setLayout(new BorderLayout());
		
		
		
		//on ajoute les 3 panneaux Nord, Sud et Centre
		this.add(this.getPanelNorth(),BorderLayout.NORTH);
		this.add(this.getPanelSouth(),BorderLayout.SOUTH);
		this.add(this.getPanelCenter(), BorderLayout.CENTER);
	}
	
	/**
	 * construit et retourne la barre de menu
	 * @return JMenuBar : la barre de menu constuite
	 */
	public JMenuBar getMenu(){
		JMenuBar menuBar = new JMenuBar();
		
		//menu "fichier" avec bouton "quitter"
		JMenu menuFichier = new JMenu("Fichier");
		JMenuItem butQuitter = new JMenuItem("Quitter");
		butQuitter.addActionListener(new ListButQuitter());
		menuFichier.add(butQuitter);
		menuBar.add(menuFichier);
		
		//menu a propos
		JMenu menuAPropos = new JMenu("A Propos");
		menuAPropos.addMouseListener(new ListButApropos());
		menuBar.add(menuAPropos);
		
		return menuBar;
	}
	
	/**
	 * Crée et renvoie le panel nord
	 * @return JPanel : panel nord de la fenetre
	 */
	public JPanel getPanelNorth(){
		//panel avec FlowLayout
		JPanel panNorth = new JPanel(new FlowLayout());
		
		//label et liste deroulante "couleurs"
		panNorth.add(new JLabel("Couleur :"));
		
		this.listeCouleurs = new JComboBox<String>(Donnees.couleurs);
		panNorth.add(this.listeCouleurs);
		
		//label et liste deroulante "forme"
		panNorth.add(new JLabel("  Forme :"));
		
		this.listeFormes = new JComboBox<String>(Donnees.formes);
		panNorth.add(this.listeFormes);
		
		//label et zone saisie "taille" 
		panNorth.add(new JLabel("  Taille :"));
		
		this.fieldTaille = new JTextField(10); //largeur du field = 10
		panNorth.add(this.fieldTaille);
		
		ListToggBut listCGD = new ListToggBut(); //listener pour les JToogleButton 
		Dimension tailleIcons = new Dimension(30,30); //taille de l'icone des bouton
		String[] tabsNomImg = {"Crayon","Gomme","Move","LayerUp","LayerDown"}; // noms des icons (pour les retrouver dasn les fichiers)
		this.listeToogleBut = new ArrayList<JToggleButton>(); // initialise la liste des boutons
		
		//crée les 5 boutons
		for(int i=0;i<5;i++) {
			
			//creation de l'icone
			ImageIcon icon = new ImageIcon(getClass().getResource("/res/logo"+tabsNomImg[i]+".png"));
			Image iconRedimensionne = icon.getImage().getScaledInstance(tailleIcons.width, tailleIcons.height, Image.SCALE_SMOOTH);
			icon = new ImageIcon(iconRedimensionne);
			
			//création du bouton
			JToggleButton but = new JToggleButton(icon);
			but.addActionListener(listCGD);
			this.listeToogleBut.add(but);
			panNorth.add(but);
		}
		
		return panNorth;
	}
	
	/**
	 * crée et renvoie le panneau Sud
	 * @return PanneauSud, le panel créé
	 */
	public PanneauSud getPanelSouth(){
		PanneauSud panSud = new PanneauSud();
		
		//button effacer
		JButton butEff = new JButton("Effacer tout");
		butEff.addActionListener(new ListButClear());
		panSud.add(butEff);
		
		//bouton revenir en arriere
		JButton butUndo = new JButton("Revenir en arrière");
		butUndo.addActionListener(new ListButUndo());
		panSud.add(butUndo);
		
		return panSud;
	}
	
	/**
	 * crée et renvoie le panneau Centre
	 * @return PanneauCentre, le panel créé
	 */
	public PanneauCentre getPanelCenter(){
		//panneau centre
		this.panelDessin = new PanneauCentre();
		ListDessin listPanel = new ListDessin();
		this.panelDessin.addMouseListener(listPanel); // listeneur de clic
		this.panelDessin.addMouseMotionListener(listPanel); // listener de drag
		return this.panelDessin;
	}
	
	/**
	 * Listener sur bouton quitter 
	 * @author 11704631
	 */
	class ListButQuitter implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			System.exit(0); //quitte le programme
		}
	}
	
	/**
	 * listener du menu A Propos
	 * @author 11704631
	 */
	class ListButApropos implements MouseListener{

		public void mouseClicked(MouseEvent arg0) {	}

		public void mouseEntered(MouseEvent arg0) {	}

		public void mouseExited(MouseEvent arg0) { }

		//a la pression du bouton, affiche un JOptionPane
		public void mousePressed(MouseEvent arg0) {
			JOptionPane.showMessageDialog(null,
				    "Créateur : Joris BARON\nCours d'IHM",
				    "A propos",
				    JOptionPane.PLAIN_MESSAGE);
		}

		public void mouseReleased(MouseEvent arg0) {}
	}
	
	/**
	 * listeneur pour tester si les JToggleButton sont enfoncés en meme temps
	 * @author 11704631
	 */
	class ListToggBut implements ActionListener	{
		
		/**
		 * teste si plusieurs boutons sont selectionné en meme temps 
		 * @return true si 2 boutons selectionnées, faux sinon
		 */
		private boolean selectionMultiple() {
			int i=0;
			int nbTogSelected = 0;
			while(nbTogSelected<2 && i<listeToogleBut.size()) {
				if(listeToogleBut.get(i).isSelected()) {
					nbTogSelected++;
				}
				i++;
			}
			
			if(nbTogSelected>1)
				return true;
			else
				return false;
		}
		
		/**
		 * Affiche une boite de dialogue informant que 2 boutons sont selectionné en meme temps
		 * et deselectionne le dernier bouton selectionné
		 */
		public void actionPerformed(ActionEvent e) {
			if(this.selectionMultiple()){
				JOptionPane.showMessageDialog(null,
					    "2 boutons ne peuvent pas être séléctionnés en meme temps !",
					    "Erreur",
					    JOptionPane.ERROR_MESSAGE);
				((JToggleButton)e.getSource()).setSelected(false);
			}
		}
	}

	/**
	 * listeneur pour dessiner sur le panel central
	 * @author 11704631
	 */
	class ListDessin implements MouseInputListener {
		private int indFigSelected;
		private Color couleurFig;
		private int tailleFig;
		
		//liste pour stocker les offests pour deplacer une liste de figures
		private ArrayList<Point> listePostionFigRelative;
		
		//explicite les boutons
		private JToggleButton crayon = listeToogleBut.get(0);
		private JToggleButton gomme = listeToogleBut.get(1);
		private JToggleButton deplace = listeToogleBut.get(2);
		private JToggleButton layerUp = listeToogleBut.get(3);
		private JToggleButton layerDown = listeToogleBut.get(4);
		
		/**
		 * Constructeur
		 * initiale la figure selectionée à -1 pour aucune
		 */
		public ListDessin() {
			super();
			this.indFigSelected = -1;
		}
		
		/**
		 * Recupere l'indice le la figure la plus haute dans Donnees.figures
		 * @param mouseLocation : la position de la souris ou on doit detecter une figure
		 * @return l'indice de la figure la plus haute dans la liste de figures, -1 si aucune ne se situe a cet endroit
		 */
		public int getUpperFigNoWhite(Point mouseLocation) {
			int indFig = -1; // aucune figure selectionné
			int i=Donnees.figures.size()-1; // on part de la derniere figure, la plus haute
			
			//parcours la liste a l'envers
			//tant qu'on est pas arrivé au bout et qu'aucune figure n'est selectionnée
			while(indFig < 0 && i>=0) {
				ArrayList<Dessin> listeFig = Donnees.figures.get(i);
				
				int j=0;
				
				while(j<listeFig.size() && indFig <0) {
					Dessin fig= listeFig.get(j);
					//si le clic est dans l'aire d'une figure non blanche
					if(mouseLocation.x>=fig.getPos().x && mouseLocation.x<=fig.getPos().x+fig.getTaille() &&
						mouseLocation.y>=fig.getPos().y && mouseLocation.y<=fig.getPos().y+fig.getTaille() &&
						!fig.getCouleur().equals(panelDessin.getBackground())) {
						indFig= i;
					}
					j++;
				}
				i--;
			}
			return indFig;
		}
		
		public void mouseClicked(MouseEvent ev) { }

		public void mouseEntered(MouseEvent ev) { }

		public void mouseExited(MouseEvent ev) { }

		//au clic 
		// (on utilise pas le "mousClicked()" car ne permet pas les clics répétés rapidement 
		public void mousePressed(MouseEvent ev) {
			
			//si on veut dessiner ou effacer
			if(crayon.isSelected() || gomme.isSelected()){
				
				//si c'est la gomme on "dessine" en blanc
				//sinon on recupere la couleur selectironné dans la ComboBox "couleur"
				if(gomme.isSelected()){
					couleurFig = panelDessin.getBackground();
				} else {
					String coulSelect = (String) listeCouleurs.getSelectedItem();
					if(coulSelect.equals("rouge"))
						couleurFig = Color.RED;
					else if(coulSelect.equals("jaune"))
						couleurFig = Color.YELLOW;
					else if(coulSelect.equals("vert"))
						couleurFig = Color.GREEN;
					else if(coulSelect.equals("cyan"))
						couleurFig = Color.CYAN;
					else if(coulSelect.equals("bleu"))
						couleurFig = Color.BLUE;
					else
						couleurFig = Color.MAGENTA;
				}
				
				//on recupere le Graphics
				Graphics g = panelDessin.getGraphics();
				
				//on definie la couleur
				g.setColor(couleurFig);
				
				//si pas de taille : 100 par defaut
				try {
					tailleFig = (fieldTaille.getText().equals(""))?100:Integer.parseInt(fieldTaille.getText());
				} catch (NumberFormatException ex) {
					tailleFig = 100;
				}
				
				//on dessine un cercle ou un carré plein, centré sur la position de la souris
				if(listeFormes.getSelectedItem().equals("cercle")){
					g.fillOval( ev.getX()-tailleFig/2, ev.getY()-tailleFig/2, tailleFig,tailleFig);
				} else {
					g.fillRect( ev.getX()-tailleFig/2, ev.getY()-tailleFig/2, tailleFig,tailleFig);
				}
				//on ajoute une nouvelle liste de dessin dans les Donnes et on met la 1ere figure dessines dans cette liste
				Donnees.figures.add(new ArrayList<Dessin>());
				Donnees.figures.get(Donnees.figures.size()-1).add(new Dessin(tailleFig,(String)listeFormes.getSelectedItem(),couleurFig, new Point(ev.getX()-tailleFig/2, ev.getY()-tailleFig/2) ));
				
			}
			//si on veut deplacer
			else if(deplace.isSelected()) {
				//si on trouve une figure sous la souris, on la selection pour l'utiliser dans le MouseDragged
				if(this.getUpperFigNoWhite(ev.getPoint())>=0){
					this.indFigSelected = this.getUpperFigNoWhite(ev.getPoint());
					
					//on stock les position relatives de chaque dessin de la figures par rapport a l'endroit ou on a cliquer
					this.listePostionFigRelative = new ArrayList<Point>();
					for(Dessin fig : Donnees.figures.get(this.indFigSelected)) {
						this.listePostionFigRelative.add(new Point(fig.getPos().x-ev.getX(), fig.getPos().y-ev.getY()));
					}
				}
			}
			//si on veut monter/descendre la figures
			else if(layerUp.isSelected() || layerDown.isSelected()) {
				int indFig = this.getUpperFigNoWhite(ev.getPoint()); //selection de la figure
				
				if(indFig>=0){
					if(layerUp.isSelected() && indFig!=Donnees.figures.size()-1) {
						//si on veut monter et que le figure n'est pas deja tout en haut, on la swap
						Collections.swap(Donnees.figures, indFig,indFig+1);
						panelDessin.repaint();
					} else if(layerDown.isSelected() && indFig!=0) {
						//si on veut descendre et que le figure n'est pas deja tout en bas, on la swap
						Collections.swap(Donnees.figures, indFig,indFig-1);
						panelDessin.repaint();
					}
				}
			}
		}

		/**
		 * au relachement du clic, on deselectionne la figure que l'on a déplacé
		 */
		public void mouseReleased(MouseEvent ev) {
			if(deplace.isSelected() && this.indFigSelected>=0) {
				this.indFigSelected = -1;
			}
		}

		/*
		 * quand la souris est deplacée, bouton enfoncé, et qu'on a choisit de deplacer une figure
		 * on deplace en meme temps la figure en gardant son centre a l'intérieur de l'emplacement de dessin
		 */
		public void mouseDragged(MouseEvent ev) {
			if(crayon.isSelected() || gomme.isSelected()) {
				Graphics g = panelDessin.getGraphics();
				g.setColor(couleurFig);
				//on dessine un cercle ou un carré plein, centré sur la position de la souris
				if(listeFormes.getSelectedItem().equals("cercle")){
					g.fillOval( ev.getX()-tailleFig/2, ev.getY()-tailleFig/2, tailleFig,tailleFig);
				} else {
					g.fillRect( ev.getX()-tailleFig/2, ev.getY()-tailleFig/2, tailleFig,tailleFig);
				}
				//on ajoute le dessin a la liste de la figure
				Donnees.figures.get(Donnees.figures.size()-1).add(new Dessin(tailleFig,(String)listeFormes.getSelectedItem(),couleurFig, new Point(ev.getX()-tailleFig/2, ev.getY()-tailleFig/2) ));
				
			}
			else if(deplace.isSelected() && this.indFigSelected>=0 &&
					ev.getX()>=0 && ev.getY()>=0 &&
					ev.getX()<=panelDessin.getSize().width && ev.getY()<=panelDessin.getSize().height) {
				//on a verifié que la souris etait a l'interieur de l'écran pour ne pas faire sortir toute la figure de l'ecran
				
				ArrayList<Dessin> listeFig = Donnees.figures.get(indFigSelected);
				Point posMouse = ev.getPoint();
				
				//on parcourt la liste de dessin de la figure pour tous les deplacer
				int i = 0;
				for(Dessin fig : listeFig) {
					//calcul du nouveau point d'origine du dessin
					Point newPosFig = new Point(
								posMouse.x+this.listePostionFigRelative.get(i).x,
								posMouse.y+this.listePostionFigRelative.get(i).y
							);
					fig.setPos(newPosFig);
					i++;
				}
				
				panelDessin.repaint();
			}
		}

		public void mouseMoved(MouseEvent arg0) { }
	}
	
	/**
	 * listener du bouton "Revenir en arrière"
	 * => efface la derniere figure
	 * @author 11704631
	 */
	class ListButUndo implements ActionListener {
		/**
		 * Efface la derniere figure, s'il y en a (fugiure "gomme" comprise)
		 */
		public void actionPerformed(ActionEvent ev) {
			if(!Donnees.figures.isEmpty() ) {
				Donnees.figures.remove(Donnees.figures.size()-1);
				panelDessin.repaint();
			}
		}
	}
	
	/**
	 * listener du bouton "Effacer tout"
	 * => efface toutes les figures
	 * @author 11704631
	 */
	class ListButClear implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			Donnees.figures.clear();
			panelDessin.repaint();
		}
	}
	
	/**
	 * main, ouvre la fenetre
	 * @param args
	 */
	public static void main(String[] args){
		new Paint();
	}
}
