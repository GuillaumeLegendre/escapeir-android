package fr.umlv.escape.front;

import org.jbox2d.dynamics.Body;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

/**
 * Class that manage all sprites of {@link Bullet}
 */
public class SpriteBullet extends Sprite{
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
	public SpriteBullet(Body body, Bitmap image){
		super(body,image);
		this.currentRotation=1;
		this.xRay=1;
		this.speed=100;
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
	
	private Bitmap rotate(Bitmap source, float angle){
		Matrix matrix = new Matrix();
	    matrix.postRotate(angle);
	    return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
	}
	
	/**
	 * Set the last time a change have occurred. This is used for the switching sprite like basic missile
	 * which have only 2 images.
	 */
	public void setLastChange(){
		setCurrentTime();
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
	
	private enum ImageName{
		BASIC_MISSILE_PLAYER,
		BASIC_MISSILE,
		XRAY,
		FIREBALL,
		FIREBALL_2,
		FIREBALL_3,
		SHIBOLEET,
		SHIBOLEET_2,
		SHIBOLEET_3
	}

	public Bitmap getNextImage() {
		currentRotation=((currentRotation+1)%18)+1;
		ImageName enumImage = ImageName.valueOf(imageName.toUpperCase());
		switch(enumImage){
		case BASIC_MISSILE_PLAYER:
			setLastChange();
			if(currentMissile==1){
				return rotate(FrontApplication.frontImage.getImage("basic_missile_off"), 180);
			}
			return rotate(FrontApplication.frontImage.getImage("basic_missile"), 180);
		case BASIC_MISSILE:
			setLastChange();
			if(currentMissile==1){
				return FrontApplication.frontImage.getImage("basic_missile_off");
			}
			return FrontApplication.frontImage.getImage("basic_missile");
		case XRAY:
			if(xRay==1){
				xRay=0;
				return FrontApplication.frontImage.getImage("xray");
			}
			xRay=1;
			return FrontApplication.frontImage.getImage("xray2");
		case FIREBALL: 
		case FIREBALL_2: 
		case FIREBALL_3: 
		case SHIBOLEET: 
		case SHIBOLEET_2: 
		case SHIBOLEET_3:
			float angle=currentRotation*20;
			if(angle==90){
				angle=100;
			}
			return rotate(FrontApplication.frontImage.getImage(imageName),angle);	
		default:
			throw new AssertionError(imageName);
		}
	}
	
	@Override
	public void onDrawSprite(Canvas canvas) {
		super.onDrawSprite(canvas);
		this.setImage(getNextImage());
	}
}

