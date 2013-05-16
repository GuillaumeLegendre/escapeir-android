package fr.umlv.escape.front;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Point;

import fr.umlv.escape.game.Game;
import fr.umlv.escape.weapon.Weapon;

/**
 * Contains tools functions who help to print the User Interface in game
 */
public class UserInterface {
	
	private UserInterface(){}
	
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
					p.y > highLeft.y && //up
					p.x < downRight.x &&//right
					p.y < downRight.y &&//bottom
					p.x > highLeft.x //left
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
		int height = (FrontApplication.HEIGHT - 100)/2;
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
