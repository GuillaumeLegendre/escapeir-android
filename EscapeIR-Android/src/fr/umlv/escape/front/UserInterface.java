package fr.umlv.escape.front;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import fr.umlv.escape.game.Game;
import fr.umlv.escape.game.Player;
import fr.umlv.escape.ship.Ship;
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
		List<Weapon> weapons = Game.getTheGame().getPlayer1().getShip().getListWeapon().getWeapons();
		for(int i=0; i<weapons.size();i++){
			Weapon w = weapons.get(i);
			if(p.x >FrontApplication.WIDTH - 60 &&
					p.y > 60+i*75 &&
					p.x < FrontApplication.WIDTH - 10 &&
					p.y < 110+i*75 ) {
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
	
	public static void drawUIScoresAndLife(Canvas canvas){
		Player player = Game.getTheGame().getPlayer1();
		String life = String.valueOf(player.getLife());
		String health = String.valueOf(player.getShip().getHealth());
		String score = String.valueOf(player.getScore());
		
		Paint p = new Paint();
		FrontImages fi = FrontApplication.frontImage;
		p.setTextSize(25);
		p.setColor(Color.GREEN);
		
		Bitmap bmp = fi.getImage("hearth");
		canvas.drawBitmap(bmp, 10, 10, p);
		canvas.drawText(life, 60, 40, p);
		bmp = fi.getImage("health");
		canvas.drawBitmap(bmp, 90, 10, p);
		canvas.drawText(health, 130, 40, p);
		canvas.drawText(score, 200, 40, p);
	}
	
	public static void drawWeaponsIcons(Canvas canvas){
		Ship s = Game.getTheGame().getPlayer1().getShip();
		Weapon current_weapon = s.getCurrentWeapon();
		List<Weapon> weapons = s.getListWeapon().getWeapons();
		Paint p = new Paint();
		FrontImages fi = FrontApplication.frontImage;
		p.setStyle(Paint.Style.STROKE);
		p.setTextSize(20);
		Bitmap bmp;
		
		for(int i=0; i<weapons.size();i++){
			Weapon w = weapons.get(i);
			bmp = fi.getImage(w.getName());
			if(w == current_weapon){
				p.setColor(Color.BLUE);
			}
			canvas.drawRect(FrontApplication.WIDTH - 60, 60+i*75, FrontApplication.WIDTH - 10, 110+i*75, p);
			canvas.drawBitmap(bmp, FrontApplication.WIDTH - 55, 65+i*75, p);
			p.setColor(Color.GREEN);
			if(String.valueOf(w.getBulletQty()).length() == 1){
				canvas.drawText(" "+w.getBulletQty(), FrontApplication.WIDTH - 30, 105+i*75, p);
			} else {
				canvas.drawText(String.valueOf(w.getBulletQty()), FrontApplication.WIDTH - 30, 105+i*75, p);
			}
			
			p.setColor(Color.BLACK);
		}
	}
	
	/**
	 * Put in buffer the performance menu to print
	 * @param fps
	 */
	public static void drawPerformanceMenu(Canvas canvas, int fps, BattleField battleflied) {
		int height = FrontApplication.HEIGHT - 100;
		Paint p = new Paint();
		p.setStyle(Paint.Style.STROKE);
		p.setTextSize(20);
		
		canvas.drawText("Ship : "+battleflied.shipList.size(), 10, height - 10,p);
		canvas.drawText("Bullet : "+battleflied.bulletList.size(), 10, height - 20,p);
		canvas.drawText("Bonus : "+battleflied.bonusList.size(), 10, height - 30,p);
		canvas.drawText("Animation : "+battleflied.animationList.size(), 10, height - 40,p);
		canvas.drawText("fps:"+fps, 10, height -50,p);
		
		p.setColor(Color.BLACK);
	}
}
