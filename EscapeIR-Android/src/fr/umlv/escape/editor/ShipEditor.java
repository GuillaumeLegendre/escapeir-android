package fr.umlv.escape.editor;

public class ShipEditor {
	public int health = 100;
	public String name = "default_ship";
	public int x = 50;
	public int y = -20;
	public String trajectory = "DownMove";
	
	@Override
	public String toString() {		
		return name;
	}
}