package fr.umlv.escape.front;

import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

import fr.umlv.escape.weapon.Bullet;

/**
 * Class that manage all sprites of {@link Bullet}
 */
public class SpriteBullet implements Sprite {
	private String imageName;
	private int currentRotation;
	private long lastImageChange;
	private long currentTime;
	private int currentMissile;
	private int xRay;
	private final int speed;
	
	/**
	 * Constructor
	 */
	public SpriteBullet(){
		this.currentRotation=1;
		this.xRay=1;
		this.speed=100;
	}
	
	private static BufferedImage rotate(BufferedImage src,double angle) {
		AffineTransform at = AffineTransform.getRotateInstance(angle, src.getWidth()/2, src.getHeight()/2);
		RenderingHints hints = new RenderingHints(
				RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		BufferedImageOp op = new AffineTransformOp(at,hints);
		BufferedImage bi = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
		op.filter(src,bi);
		return bi;
	}
	
	/**
	 * Load all images that are needed to the sprites. This method has to be called before using this class.
	 */
	public static void loadImagesSprites(){
		Image image=ImagesFactory.getTheImagesFactory().createBulletImage("BasicMissile");
		FrontImages.addImages("BasicMissile", image);
		image=ImagesFactory.getTheImagesFactory().createBulletImage("BasicMissileOff");
		FrontImages.addImages("BasicMissileOff", image);
		FrontImages.addImages("BasicMissileP", image);
		image=ImagesFactory.getTheImagesFactory().createBulletImage("BasicMissileOff");
		FrontImages.addImages("BasicMissileOffP", image);

		image=ImagesFactory.getTheImagesFactory().createBulletImage("FireBall");
		FrontImages.addImages("FireBall", image);
		image=ImagesFactory.getTheImagesFactory().createBulletImage("FireBall_2");
		FrontImages.addImages("FireBall_2", image);
		image=ImagesFactory.getTheImagesFactory().createBulletImage("FireBall_3");
		FrontImages.addImages("FireBall_3", image);

		image=ImagesFactory.getTheImagesFactory().createBulletImage("Shiboleet");
		FrontImages.addImages("Shiboleet", image);
		image=ImagesFactory.getTheImagesFactory().createBulletImage("Shiboleet_2");
		FrontImages.addImages("Shiboleet_2", image);
		image=ImagesFactory.getTheImagesFactory().createBulletImage("Shiboleet_3");
		FrontImages.addImages("Shiboleet_3", image);	
		
		image=ImagesFactory.getTheImagesFactory().createBulletImage("XRay");
		FrontImages.addImages("XRay", image);
		image=ImagesFactory.getTheImagesFactory().createBulletImage("XRay2");
		FrontImages.addImages("XRay2", image);
	}

	/**
	 * Set the name of the image to animate
	 */
	public void setCurrentName(String name){
		this.imageName=name;
	}

	/**
	 * Set the current time that all image will use to animate.
	 */
	public void setCurrentTime(){
		currentTime=System.currentTimeMillis();
	}
	
	/**
	 * Set the last time a change have occurred. This is used for the switching sprite like basic missile
	 * which have only 2 images.
	 */
	public void setLastChange(){
		if((currentTime-lastImageChange)>speed){
			this.lastImageChange=currentTime;
			if(currentMissile==0){
				currentMissile=1;
			}
			else{
				currentMissile=0;
			}
		}
	}

	@Override
	public Image getNextImage() {
		currentRotation=((currentRotation+1)%18)+1;
		switch(imageName){
		case "BasicMissileP":
			if(currentMissile==1){
				return FrontImages.getImage("BasicMissileOffP");
			}
			return FrontImages.getImage("BasicMissileP");
		case "BasicMissile":
			if(currentMissile==1){
				return FrontImages.getImage("BasicMissileOff");
			}
			return FrontImages.getImage("BasicMissile");
		case "XRay":
			if(xRay==1){
				xRay=0;
				return FrontImages.getImage("XRay");
			}
			xRay=1;
			return FrontImages.getImage("XRay2");
		case "FireBall": 
		case "FireBall_2": 
		case "FireBall_3": 
		case "Shiboleet": 
		case "Shiboleet_2": 
		case "Shiboleet_3":
			double angle=currentRotation*20;
			if(angle==90){
				angle=100;
			}
			return rotate((BufferedImage)FrontImages.getImage(imageName),angle);	
		default:
			throw new AssertionError(imageName);
		}
	}
}

