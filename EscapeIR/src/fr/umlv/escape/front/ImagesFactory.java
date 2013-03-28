package fr.umlv.escape.front;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import fr.umlv.escape.bonus.Bonus;
import fr.umlv.escape.bonus.BonusFactory;

/**This class supplies methods to create properly a {@link Image}.
 */
public class ImagesFactory {

	private static ImagesFactory TheImagesFactory;
	private final static String SHIP_PATH="./Images/Ships/";
	private final static String BACKGROUND_PATH="./Images/BackGrounds/";
	private final static String WEAPON_PATH="./Images/Weapons/";
	private final static String BULLET_PATH="./Images/Weapons/Bullets/";
	private final static String BONUS_PATH="./Images/Bonus/";
	private final static String UI_PATH="./Images/UI/";
	private final static String EXTENSION=".png";
	
	
	private ImagesFactory(){
	}
	
	/**
	 * Create an image that represent a ship
	 * @param imageName of the ship
	 * @return the image created
	 */
	public Image createShipImage(String imageName){
		try {
			return ImageIO.read(new File(SHIP_PATH+imageName+EXTENSION));
		} catch (IOException e) {
			throw new AssertionError(SHIP_PATH+imageName+EXTENSION+" not found");
		}
	}
	
	/**
	 * Create an image that represent a background
	 * @param imageName of the background
	 * @return the image created
	 */
	public Image createBackGroundImage(String imageName){
		try {
			return ImageIO.read(new File(BACKGROUND_PATH+imageName+EXTENSION));
		} catch (IOException e) {
			throw new AssertionError(BACKGROUND_PATH+imageName+EXTENSION+"image not found");
		}
	}
	
	/**
	 * Create an image that represent a bullet
	 * @param imageName of the bullet
	 * @return the image created
	 */
	public Image createBulletImage(String imageName){
		try {
			return ImageIO.read(new File(BULLET_PATH+imageName+EXTENSION));
		} catch (IOException e) {
			throw new AssertionError(BULLET_PATH+imageName+EXTENSION+"image not found");
		}
	}
	
	/**
	 * Create an image that represent a weapon
	 * @param imageName of the weapon
	 * @return the image created
	 */
	public Image createWeaponImage(String imageName){
		try {
			return ImageIO.read(new File(WEAPON_PATH+imageName+EXTENSION));
		} catch (IOException e) {
			throw new AssertionError(WEAPON_PATH+imageName+EXTENSION+"image not found");
		}
	}

	/**
	 * Create an image that represent a bonus
	 * @param bonusName of the bonus
	 * @return the image created
	 */
	public Image createBonusImage(String bonusName) {
		try {
			return ImageIO.read(new File(BONUS_PATH+bonusName+EXTENSION));
		} catch (IOException e) {
			throw new AssertionError(bonusName+" not found");
		}
	}
	
	/**
	 * Create an image that represent a part of the user interface
	 * @param bonusName of the the user interface
	 * @return the image created
	 */
	public Image createUIImage(String name) {
		try {
			return ImageIO.read(new File(UI_PATH+name+EXTENSION));
		} catch (IOException e) {
			throw new AssertionError(name+" not found");
		}
	}
	
	/** Get the unique instance of {@link ImagesFactory}.
	 * @return The unique instance of {@link ImagesFactory}
	 */
	public static ImagesFactory getTheImagesFactory(){
		if(ImagesFactory.TheImagesFactory==null){
			ImagesFactory.TheImagesFactory = new ImagesFactory();
		}
		return ImagesFactory.TheImagesFactory;
	}
}
