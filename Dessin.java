import java.awt.Color;
import java.awt.Point;


public class Dessin {
	private int taille;
	private String forme;
	private Color couleur;
	private Point pos;
	
	public Dessin(int taille, String forme, Color couleur, Point pos) {
		this.taille = taille;
		this.forme = forme;
		this.couleur = couleur;
		this.pos = pos;
	}
	
	public int getTaille() {
		return taille;
	}
	public void setTaille(int taille) {
		this.taille = taille;
	}
	public String getForme() {
		return forme;
	}
	public void setForme(String forme) {
		this.forme = forme;
	}
	public Color getCouleur() {
		return couleur;
	}
	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}
	public Point getPos() {
		return pos;
	}
	public void setPos(Point pos) {
		this.pos = pos;
	}
	
}
