package fr.umlv.escape.front;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jbox2d.dynamics.Body;

import fr.umlv.escape.bonus.Bonus;
import fr.umlv.escape.game.Game;
import fr.umlv.escape.game.Player;
import fr.umlv.escape.ship.Ship;
import fr.umlv.escape.weapon.Bullet;
import fr.umlv.escape.weapon.ListWeapon;
import fr.umlv.escape.weapon.Weapon;
import fr.umlv.escape.world.EscapeWorld;

/** This class manage all elements that can be displayed in the {@link FrontApplication}
 * including drawing elements and launching sprite associated to them.
 * Elements can be add or get thread safely.
 */
public class DisplayableMonitor {
	
	private static BufferedImage screenBuffer = new BufferedImage(Game.getTheGame().getWidth(), Game.getTheGame().getHeight(), 2); 
	private static final Map<Body,Ship> shipMap=new Hashtable<>(50);
	private static final Map<Body,Bullet> bulletMap=new Hashtable<>(50);
	private static final Map<Body,Bonus> bonusMap=new Hashtable<>(10);
	private static final List<Ship> shipList=new ArrayList<>();
	private static final List<Bullet> bulletList=new ArrayList<>();
	private static final List<Bonus> bonusList=new ArrayList<>();
	private static final List<SpriteExplosion> spriteList=new ArrayList<>();
	private static final SpriteBullet spriteBullet=new SpriteBullet();	
	private static final PlayerSprites playerSprite=new PlayerSprites();
	private static final Ship playerShip=Game.getTheGame().getPlayer1().getShip();

	private static final Object bulletLock=new Object();
	private static final Object shipLock=new Object();
	private static final Object bonusLock=new Object();
	
	private static final Font FONT = new Font("arial", Font.BOLD, 20);
	private static final Font SMALL_FONT = new Font("arial", Font.BOLD, 10);
	private static final int SMALL_MARGIN = 10;

	private DisplayableMonitor() {
	}
	
	/**
	 * Return a ship associated to a body in O(1).
	 * @param body the body of the ship to get.
	 * @return The ship associated to the body in parameter.
	 */
	public static Ship getShip(Body body){
		return shipMap.get(body);
	}
	
	/**
	 * Return a bullet associated to a body in O(1).
	 * @param body the body of the bullet to get.
	 * @return The bullet associated to the body in parameter.
	 */
	public static Bullet getBullet(Body body){
		return bulletMap.get(body);
	}
	
	/**
	 * Return a bonus associated to a body in O(1).
	 * @param body the body of the bonus to get.
	 * @return The bonus associated to the body in parameter.
	 */
	public static Bonus getBonus(Body body){
		return bonusMap.get(body);
	}
	
	/**
	 * Add a bullet to the DisplayableMonitor. The drawing of the bullet added will
	 * be manage by the DisplayableMonitor. This method is ThreadSafe.
	 * @param bullet the bullet to add to the DisplayableMonitor.
	 */
	public static void addBullet(Bullet bullet){
		synchronized(bulletLock){
			bulletMap.put(bullet.getBody(), bullet);
			bulletList.add(bullet);
		}
	}
	
	/**
	 * Delete a bullet in the DisplayableMonitor. The drawing of the bullet will not
	 * be manage by the DisplayableMonitor anymore. This method is ThreadSafe.
	 * @param bullet the bullet to delete in the DisplayableMonitor.
	 */
	public static void deleteBullet(Bullet bullet){
		synchronized(bulletLock){
			if(bulletMap.containsValue(bullet)){
				EscapeWorld.getTheWorld().setActive(bullet.getBody(),false);
				bulletList.remove(bullet);
				shipMap.remove(bullet.getBody());
			}
		}
	}
	
	/**
	 * Add a ship to the DisplayableMonitor. The drawing of the ship added will
	 * be manage by the DisplayableMonitor. This method is ThreadSafe.
	 * @param ship the ship to add to the DisplayableMonitor.
	 */
	public static void addShip(Ship ship){
		synchronized(shipLock){
			shipMap.put(ship.getBody(), ship);
			shipList.add(ship);
		}
	}

	/**
	 * Delete a ship in the DisplayableMonitor. The drawing of the ship will not
	 * be manage by the DisplayableMonitor anymore. This method is ThreadSafe.
	 * @param ship the ship to delete in the DisplayableMonitor.
	 */
	public static void deleteShip(Ship ship){
		synchronized(shipLock){
			if(shipMap.containsValue(ship)){
				EscapeWorld.getTheWorld().setActive(ship.getBody(),false);
				shipList.remove(ship);
				shipMap.remove(ship.getBody());
			}
		}
	}
	
	/**
	 * Add a bonus to the DisplayableMonitor. The drawing of the bonus added will
	 * be manage by the DisplayableMonitor. This method is ThreadSafe.
	 * @param bonus the bonus to add to the DisplayableMonitor.
	 */
	public static void addBonus(Bonus bonus){
		synchronized(bonusLock){
			bonusMap.put(bonus.getBody(), bonus);
			bonusList.add(bonus);
		}
	}
	
	/**
	 * Delete a bonus in the DisplayableMonitor. The drawing of the bonus will not
	 * be manage by the DisplayableMonitor anymore. This method is ThreadSafe.
	 * @param bonus the bonus to delete in the DisplayableMonitor.
	 */
	public static void deleteBonus(Bonus bonus){
		synchronized(bonusLock){
			if(bonusMap.containsValue(bonus)){
				EscapeWorld.getTheWorld().setActive(bonus.getBody(), false);
				bonusList.remove(bonus);
				bonusMap.remove(bonus.getBody());
			}
		}
	}
	
	/**
	 * Delete all bonus in DisplayableMonitor. Bonus's drawings will not
	 * be manage by the DisplayableMonitor anymore. This method is ThreadSafe.
	 */
	public static void deleteAllBonus(){
		synchronized(bonusLock){
			Iterator<Bonus> i = bonusList.iterator();
			while(i.hasNext()){
				Bonus bonus = i.next();
				EscapeWorld.getTheWorld().setActive(bonus.getBody(), false);
			}
			bonusList.clear();
			bonusMap.clear();
		}
	}
	
	/**
	 * Delete all ship enemy in DisplayableMonitor. Enemy's drawings will not
	 * be manage by the DisplayableMonitor anymore. This method is ThreadSafe.
	 */
	public static void deleteAllShipEnemy(){
		synchronized(shipLock){
			Iterator<Ship> i = shipList.iterator();
			while(i.hasNext()){
				Ship ship = i.next();
				EscapeWorld.getTheWorld().setActive(ship.getBody(), false);
			}
			shipMap.clear();
			shipList.clear();
		}
	}
	
	/**
	 * Delete all bullet in DisplayableMonitor. Bullet's drawings will not
	 * be manage by the DisplayableMonitor anymore. This method is ThreadSafe.
	 */
	public static void deleteAllBullet(){
		synchronized(bulletLock){
			Iterator<Bullet> i = bulletList.iterator();
			while(i.hasNext()){
				Bullet bullet = i.next();
				EscapeWorld.getTheWorld().setActive(bullet.getBody(), false);
			}
			bulletList.clear();
			bulletMap.clear();
		}
	}
	
	/**
	 * Put in Buffer all bullets. Thread-Safe
	 */
	public static void drawBullets(){
		synchronized(bulletLock){
			Graphics2D graphics = screenBuffer.createGraphics();
			Iterator<Bullet> iterBullet=bulletList.iterator();
			spriteBullet.setCurrentTime();
			while(iterBullet.hasNext()){
				Bullet bullet=iterBullet.next();
				if((bullet.getPosXCenter() > Game.getTheGame().getWidth()+FrontApplication.LIMITWORLD)	||
				   (bullet.getPosXCenter() < 0-FrontApplication.LIMITWORLD)								||
				   (bullet.getPosYCenter() > Game.getTheGame().getHeight()+FrontApplication.LIMITWORLD)	||
				   (bullet.getPosYCenter() < 0 - FrontApplication.LIMITWORLD)) {
					EscapeWorld.getTheWorld().setActive(bullet.getBody(), false);
					EscapeWorld.getTheWorld().destroyBody(bullet.getBody());
					bulletMap.remove(bullet);
					iterBullet.remove();
				} else if(bullet.getBody().isActive()){
					spriteBullet.setCurrentName(bullet.getNameLvl());
					Image img=spriteBullet.getNextImage();
					if(bullet.isPlayerBullet()){
						graphics.rotate(Math.toRadians(180),bullet.getPosXCenter(),bullet.getPosYCenter());
						graphics.drawImage(img,bullet.getPosXCenter()-(img.getWidth(null)/2),bullet.getPosYCenter()-(img.getHeight(null)/2),null);
						graphics.rotate(-Math.toRadians(180),bullet.getPosXCenter(),bullet.getPosYCenter());
					} else {
						graphics.drawImage(img,bullet.getPosXCenter()-(img.getWidth(null)/2),bullet.getPosYCenter()-(img.getHeight(null)/2),null);
					}
				} else {
					EscapeWorld.getTheWorld().destroyBody(bullet.getBody());
					SpriteExplosion sp=new SpriteExplosion(50,bullet.getPosXCenter(),bullet.getPosYCenter());
					spriteList.add(sp);
					bulletMap.remove(bullet);
					iterBullet.remove();
				}
			}
			spriteBullet.setLastChange();
		}
	}
	
	/**
	 * Put in Buffer all Ships except player ship. Thread-Safe
	 */
	public static void drawShip(){
		synchronized(shipLock){
			Graphics2D graphics = screenBuffer.createGraphics();
			Iterator<Ship> iterShip=shipList.iterator();
			while(iterShip.hasNext()){
				Ship ship=iterShip.next();
				if((ship.getPosXCenter() > Game.getTheGame().getWidth()+FrontApplication.LIMITWORLD) ||
				   (ship.getPosXCenter() < 0-FrontApplication.LIMITWORLD)					 		 ||
				   (ship.getPosYCenter() > Game.getTheGame().getHeight()+FrontApplication.LIMITWORLD)||
				   (ship.getPosYCenter() < 0 - FrontApplication.LIMITWORLD)) {
					ship.setAlive(false);
					EscapeWorld.getTheWorld().setActive(ship.getBody(),false);
					EscapeWorld.getTheWorld().destroyBody(ship.getBody());
					shipMap.remove(ship);
					iterShip.remove();
				}
				else if(!ship.isAlive()){
					EscapeWorld.getTheWorld().setActive(ship.getBody(),false);
					SpriteExplosion sp=new SpriteExplosion(50,ship.getPosXCenter(),ship.getPosYCenter());
					spriteList.add(sp);
					EscapeWorld.getTheWorld().destroyBody(ship.getBody());
					shipMap.remove(ship);
					iterShip.remove();
				}
				else if(ship.getBody().isActive()){
					Image img=FrontImages.getImage(ship.getName());
					graphics.drawImage(img,ship.getPosXCenter()-(img.getWidth(null)/2),ship.getPosYCenter()-(img.getHeight(null)/2),null);
				}
			}
		}
	}
	
	/**
	 * Put in Buffer all Explosion.
	 */
	public static void drawSpriteExplosion(){
		Graphics2D graphics = screenBuffer.createGraphics();
		Iterator<SpriteExplosion> iterSprite=spriteList.iterator();
		while(iterSprite.hasNext()){
			SpriteExplosion sprite=iterSprite.next();
			Image img=sprite.getNextImage();
			if(img==null){
				iterSprite.remove();
			}
			else{
				graphics.drawImage(img,sprite.getX()-(img.getWidth(null)/2),sprite.getY()-(img.getHeight(null)/2),null);	
			}
		}
	}
	
	/**
	 * Put in Buffer all Bonus. Thread-Safe
	 */
	public static void drawBonus(){
		synchronized(bonusLock){
			Graphics2D graphics = screenBuffer.createGraphics();
			Iterator<Bonus> iterBonus=bonusList.iterator();
			while(iterBonus.hasNext()){
				Bonus bonus=iterBonus.next();
				if((bonus.getPosXCenter() > Game.getTheGame().getWidth()+FrontApplication.LIMITWORLD)	||
				   (bonus.getPosXCenter() < 0-FrontApplication.LIMITWORLD)								||
				   (bonus.getPosYCenter() > Game.getTheGame().getHeight()+FrontApplication.LIMITWORLD)	||
				   (bonus.getPosYCenter() < 0 - FrontApplication.LIMITWORLD)) {
					EscapeWorld.getTheWorld().setActive(bonus.getBody(),false);
				}	
				if(bonus.getBody().isActive()){
					Image img=FrontImages.getImage(bonus.getName()+bonus.getType());
					graphics.drawImage(img,bonus.getPosXCenter()-(img.getWidth(null)/2),bonus.getPosYCenter()-(img.getHeight(null)/2),null);
					
					graphics.setPaint(Color.BLUE);
					graphics.setFont(SMALL_FONT);
					graphics.drawString(String.valueOf(bonus.getQuantity()), bonus.getPosXCenter()+2, bonus.getPosYCenter()+13);
				}
				else{
					EscapeWorld.getTheWorld().destroyBody(bonus.getBody());
					shipMap.remove(bonus);
					iterBonus.remove();
				}
			}
		}
	}
	
	/**
	 * Put in Buffer icon weapons in User Interface.
	 */
	public static void drawUIWeapons() {
		Graphics2D graphics = screenBuffer.createGraphics();
		ListWeapon lw = Game.getTheGame().getPlayer1().getShip().getListWeapon();
		List<Weapon> weaponList = lw.getWeapons();
		Weapon currentWeapon = lw.getCurrentWeapon();
		
		int i = 1;
		for(Weapon w : weaponList){
			int qty = w.getBulletQty();
			if(w == currentWeapon){
				graphics.setPaint(Color.BLUE);
			}
			else if(qty <= 0){
				graphics.setPaint(Color.GRAY);
			}
			else{
				graphics.setPaint(Color.WHITE);
			}
			Image img=FrontImages.getImage(w.getName());
			int gameWidth = Game.getTheGame().getWidth();
			int posX = gameWidth - 50*i- SMALL_MARGIN*i;
			graphics.drawRect( posX, SMALL_MARGIN, 50, 50);
			graphics.drawImage(img, posX, SMALL_MARGIN,null);
			graphics.setFont(SMALL_FONT);
			graphics.drawString(String.valueOf(qty), posX + 30, 60);
			Point p = new Point(posX, SMALL_MARGIN);
			Point p2 = new Point(p);
			p2.x = p2.x + 50;
			p2.y = p2.y + 50;
			w.setPos(p, p2);
			i++;
		}
	}
	
	/**
	 * Put in Buffer Score and life (health and hearth) in UI
	 */
	public static void drawUIScoresAndLife(){
		Graphics2D graphics = screenBuffer.createGraphics();
		Player player = Game.getTheGame().getPlayer1();
		String life = String.valueOf(player.getLife());
		String health = String.valueOf(player.getShip().getHealth());
		String score = String.valueOf(player.getScore());
		
		graphics.setPaint(Color.RED);
		graphics.setFont(SMALL_FONT);
		
		Image img=FrontImages.getImage("Health");
		graphics.drawImage(img, SMALL_MARGIN, SMALL_MARGIN,null);
		graphics.drawString(health, 22, 31);
		
		img=FrontImages.getImage("Hearth");
		graphics.drawImage(img, SMALL_MARGIN + 50, SMALL_MARGIN,null);
		graphics.drawString(life, 75, 31);
		
		graphics.drawString(score, 120, 31);
	}
	
	/**
	 * Put in Buffer User's ship.
	 */
	public static void drawPlayer(){
		Graphics2D graphics = screenBuffer.createGraphics();
		if(!playerShip.isAlive()){
			spriteList.add(new SpriteExplosion(50,playerShip.getPosXCenter(),playerShip.getPosYCenter()));
			playerSprite.setCurrentSprite(PlayerSprites.SpriteType.DEAD_SHIP);
			playerShip.setAlive(true);
		}
		else{
			Image imagePlayer=playerSprite.getNextImage();
			graphics.drawImage(imagePlayer,playerShip.getPosXCenter()-(imagePlayer.getWidth(null)/2),playerShip.getPosYCenter()-(imagePlayer.getHeight(null)/2), null);
		}
	}
	
	/**
	 * Put in Buffer a background picture
	 * @param background {@link BackgroundScroller} to draw
	 */
	public static void drawBackground(BackGroundScroller background){
		Graphics2D graphics = screenBuffer.createGraphics();
		graphics.drawImage(background.getBackgroundImage(),background.getPosX(),background.getPosY(), null);
	}
	
	/**
	 * Draw the buffering {@link Graphics}
	 * @param graphics to draw
	 */
	public static void display(Graphics2D graphics){
		graphics.drawImage(screenBuffer,0,0,null);
	}

	
	/**
	 * Put in buffer a Full Text to Draw.
	 * @param s
	 */
	public static void drawFullText(String s) {
		Graphics2D graphics = screenBuffer.createGraphics();
		graphics.setPaint(Color.WHITE);
		graphics.setFont(FONT);
		graphics.drawString(s, 250, 250);
	}

	/**
	 *  Put in buffer the menu to draw
	 */
	public static void drawMenu(){
		Graphics2D graphics = screenBuffer.createGraphics();
		
		Image img=FrontImages.getImage("Jupiter");
		int height = (Game.getTheGame().getHeight() - img.getHeight(null))/2;

		graphics.drawImage(img, 100, height,null);
		img=FrontImages.getImage("Moon");
		graphics.drawImage(img, 260, height,null);
		img=FrontImages.getImage("Earth");
		graphics.drawImage(img, 420, height,null);
	}

	/**
	 * Put in buffer the performance menu to print
	 * @param fps
	 */
	public static void drawPerformanceMenu(int fps) {
		Graphics2D graphics = screenBuffer.createGraphics();
		graphics.setPaint(Color.WHITE);
		graphics.setFont(SMALL_FONT);
		int height = Game.getTheGame().getHeight() - 100;
		
		graphics.drawString("Ship : "+shipList.size(), 10, height - 10);
		graphics.drawString("Bullet : "+bulletList.size(), 10, height - 20);
		graphics.drawString("Bonus : "+bonusList.size(), 10, height - 30);
		graphics.drawString("Sprite : "+spriteList.size(), 10, height - 40);
		graphics.drawString("fps:"+fps, 10, height -50);
	}

	/**
	 * @return {@link PlayerSprites}
	 */
	public static PlayerSprites getPlayersprite() {
		return playerSprite;
	}
}
