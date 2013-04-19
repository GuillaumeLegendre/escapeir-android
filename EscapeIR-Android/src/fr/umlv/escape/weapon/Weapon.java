package fr.umlv.escape.weapon;

import java.awt.Image;
import java.awt.Point;

import fr.umlv.escape.front.FrontImages;
import fr.umlv.escape.front.ImagesFactory;
import fr.umlv.escape.game.Game;

/**  
 * This class represent the structure of a weapon.
 */
public abstract class Weapon {
	private int qty;
	private final int maxQty;
	private int ghostShoot;
	private final int frequency;
	private int lastShot;
	private final String missile;

	private final String name;
	private Point highLeft;
	private Point downRight;
	private Bullet loadingBullet;

	/**
	 * @return the bullet link to the {@link Ship}} 
	 */
	public Bullet getLoadingBullet() {
		return loadingBullet;
	}
	
	/**
	 * @return the string who represent the kind of missile.
	 */
	public String getMissile() {
		return missile;
	}
	
	/**
	 * Set the bullet who will be launch 
	 * @param bullet
	 */
	public void setLoadingBullet(Bullet bullet) {
		loadingBullet=bullet;
	}

	/**
	 * Constructor to create a weapon.
	 * @param qty
	 * @param maxQty
	 * @param frequency
	 * @param missile
	 * @param name
	 */
	public Weapon(int qty, int maxQty, int frequency, String missile, String name) {
		if(qty < 0 || qty > maxQty) throw new IllegalArgumentException("Quantity are less than 0 or much than max quantity");
		this.qty = qty;
		this.maxQty = maxQty;
		this.frequency = frequency;
		this.lastShot = (int) System.currentTimeMillis();
		this.missile = missile;
		this.name = name;
		this.ghostShoot=0;
	}
	
	/**
	 * Position of icon of {@link UserInterface}
	 * @param highLeft
	 * @param downRight
	 */
	public void setPos(Point highLeft, Point downRight){
		this.highLeft = highLeft;
		this.downRight = downRight;
	}
	
	/**
	 * @return get the higher {@link Point} of the icon UI.
	 */
	public Point getHighLeft() {
		return highLeft;
	}

	/**
	 * @return get the lowest {@link Point} of the icon UI.
	 */
	public Point getDownRight() {
		return downRight;
	}
	
	/**
	 * @return the quantity of {@link Bullet} in the weapon
	 */
	public int getBulletQty(){
		return qty;
	}
	
	/**
	 * @return the name of weapon
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * @return true if they are enough bullet or if the weapon as ammo unlimited else false.
	 */
	private boolean decQty(){
		if(qty==Integer.MIN_VALUE){
			return true;
		}
		if(qty > 0){
			qty--;
			return true;
		}
		return false;
	}
	
	/**
	 * Set the number of bullet to unlimited
	 */
	public void setInfinityQty(){
		this.qty=Integer.MIN_VALUE;
	}
	
	/**
	 * Add number of bullet at the weapon and if number max is reach or more the quantity is set to max.
	 * @param qty
	 */
	public void addQte(int qty){
		this.qty += qty;
		if( this.qty >= maxQty) this.qty = maxQty;
	}
	
	/**
	 * Get the number of ghost shoot of the weapon. Ghost shoot are used to (de)synchronize
	 * many ship shoot.
	 * @return The number of ghost shoot of the weapon.
	 */
	public int getGhostShoot() {
		return ghostShoot;
	}

	/**
	 * Set the number of ghost shoot of the weapon. Ghost shoot are used to (de)synchronize
	 * many ship shoot.
	 * @param ghostShoot The number of ghost shoot to set.
	 *
	 */
	public void setGhostShoot(int ghostShoot) {
		this.ghostShoot = ghostShoot;
	}
	
	/**
	 * @return true if the spend time since the last {@link #shoot()} is less than frequency
	 */
	public boolean canShoot(){
		int now = (int) System.currentTimeMillis();
		if(now - lastShot < frequency){
			return false;
		}
		boolean b = decQty();
		lastShot = now;
		return b;
	}
	
	/**
	 * Create a bullet who are link at this weapon.
	 * @param startPosition
	 * @return the bullet who are created
	 */
	public Bullet fire(Point startPosition){
		Image img=ImagesFactory.getTheImagesFactory().createBulletImage(missile);
		FrontImages.addImages(missile, img);
		
		BulletsFactory bf = BulletsFactory.getTheBulletsFactory();
		boolean playerBullet = false;
		if(Game.getTheGame().getPlayer1().getShip().getCurrentWeapon() == this){
			playerBullet = true;
			startPosition.y = startPosition.y - 40;
		} else {
			startPosition.y = startPosition.y + 40;
		}
			
		Bullet b = bf.createBullet(missile, startPosition, playerBullet);
		b.getBody().setActive(true);
		return b;
	}

}
