import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class PanneauSud extends JPanel {

	private static final long serialVersionUID = 2383560942466773447L;

	public PanneauSud() {
		super();
		this.setPreferredSize(new Dimension(this.getPreferredSize().width,40)); // mets la hauter du panneau a 40
	}
	
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		
		//crée l'image et la place dans le coin supérieur gauche du panel
		Image logo = new ImageIcon(getClass().getResource("/res/logoPaint.png")).getImage();
		g.drawImage(logo, 0, 0,40,40,this);
	}
}
