import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;


public class PanneauCentre extends JPanel {

	private static final long serialVersionUID = -8650966002969736383L;
	
	public PanneauCentre(){
		super();
		
        this.setBackground(Color.WHITE);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		//redessine chaque dessin enregistrer dans Donnees
		for(int i=0;i<Donnees.figures.size();i++){
			ArrayList<Dessin> listeFig = Donnees.figures.get(i);
			for(Dessin fig : listeFig) {
				g.setColor(fig.getCouleur());
				if(fig.getForme().equals("cercle")){
					g.fillOval( fig.getPos().x, fig.getPos().y, fig.getTaille(), fig.getTaille());
				} else {
					g.fillRect( fig.getPos().x, fig.getPos().y, fig.getTaille(), fig.getTaille());
				}
			}
		}
	}
}
