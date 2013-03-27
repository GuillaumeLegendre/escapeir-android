package fr.umlv.escape.front;

import java.awt.Image;
import java.awt.Point;
import java.util.List;

import fr.umlv.escape.game.Game;
import fr.umlv.escape.weapon.Weapon;

/**
 * Contains tools functions who help to print the User Interface in game
 */
public class UserInterface {
	
	private UserInterface(){}
	
	private static void initLife(){
		Image img=ImagesFactory.getTheImagesFactory().createUIImage("Health");//CONSTANT
		FrontImages.addImages("Health", img);
		
		img=ImagesFactory.getTheImagesFactory().createUIImage("Hearth");//CONSTANT
		FrontImages.addImages("Hearth", img);
	}
	
	private static void initMenu(){
		Image img=ImagesFactory.getTheImagesFactory().createUIImage("Jupiter");//CONSTANT
		FrontImages.addImages("Jupiter", img);
		
		img=ImagesFactory.getTheImagesFactory().createUIImage("Moon");//CONSTANT
		FrontImages.addImages("Moon", img);
		
		img=ImagesFactory.getTheImagesFactory().createUIImage("Earth");//CONSTANT
		FrontImages.addImages("Earth", img);
	}
	
	private static void initWeapons(){
		List<Weapon> listWeapon = Game.getTheGame().getPlayer1().getShip().getListWeapon().getWeapons();
		Image img;
		for(Weapon w:listWeapon){
			String name = w.getName();
			img=ImagesFactory.getTheImagesFactory().createWeaponImage(name);
			FrontImages.addImages(name, img);
		}
	}
	
	/**
	 * Load all picture to draw it
	 */
	public static void initUserInterface(){
		initWeapons();
		initLife();
		initMenu();
	}
	
	/**
	 * return the {@link weapon} if click is on icon weapon
	 * @param p position of click user
	 * @return the weapon or null if click isn't on icon's weapon
	 */
	public static Weapon clickIsWeaponSelect(Point p){
		List<Weapon> weaponList = Game.getTheGame().getPlayer1().getShip().getListWeapon().getWeapons();
		for(Weapon w : weaponList){
			Point highLeft = w.getHighLeft();
			Point downRight = w.getDownRight();

			if(
					highLeft != null &&
					downRight != null &&
					p.y > highLeft.y && //haut
					p.x < downRight.x &&//droite
					p.y < downRight.y &&//bas
					p.x > highLeft.x //gauche
					){
				return w;
			}
		}
		return null;
	}
	
	/**
	 * Return the level to launch
	 * @param p position of click by player
	 * @return level number or -1 if don't click on icon 
	 */
	public static int getLevelToLaunch(Point p){
		int height = (Game.getTheGame().getHeight() - 100)/2;
		if(p.y > height && p.y < height + 100){
			if(p.x > 100 && p.x < 200){
				return 1;
			} else if(p.x > 260 && p.x < 360){
				return 2;
			} else if(p.x > 420 && p.x < 520){
				return 3;
			}
		}
		return -1;
	}
}
