package fr.umlv.escape.front;

import org.jbox2d.dynamics.Body;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Class that manage all sprites of {@link Player}
 */
public class SpriteShip extends Sprite{
	private SpriteType sprite;
	private int currentImage;
	private long lastSet;
	private long lastImageChange;
	private int explosion;
	private final int speed;
	
	/**
	 * Constructors
	 */
	public SpriteShip(Body body, Bitmap image){
		super(body,image);
		this.sprite=SpriteShip.SpriteType.BASIC_IMAGE;
		this.currentImage=1;
		this.speed=75;
		this.explosion=1;
		this.lastSet=0;
	}
	
	/**Represent one of the sprite player
	 */
	public enum SpriteType{
		/**Represent the basic image without sprite
		 */
		BASIC_IMAGE,
		/**Represent the sprite of a left looping
		 */
		LEFT_VRILLE,
		/**Represent the sprite of a right looping
		 */
		RIGHT_VRILLE,
		/**Represent the sprite of a left move
		 */
		LEFT_MOVE,
		/**Represent the sprite of a right looping
		 */
		RIGHT_MOVE,
		/**Represent the sprite of the player death
		 */
		DEAD_SHIP
	}
	/**
	 * set the current sprite to process
	 * @param sprite
	 */
	public void setCurrentSprite(SpriteType sprite){
		this.sprite=sprite;
		this.currentImage=1;
		this.lastSet=System.currentTimeMillis();
	}

	private String getNextImageName() {
		String imageName;
		long currentTime=System.currentTimeMillis();
		
		switch(sprite){
		case BASIC_IMAGE: imageName = "DefaultShipPlayer";break;
		case LEFT_VRILLE: 
			imageName = "DefaultShipPlayerVL"+currentImage;
			if(currentTime-lastSet>speed){
				currentImage++;
				lastSet=currentTime;
			}
			if(currentImage==14){
				setCurrentSprite(SpriteType.BASIC_IMAGE);
			}
			break;
		case RIGHT_VRILLE:
			imageName = "DefaultShipPlayerVR"+currentImage;
			if(currentTime-lastSet>speed){
				currentImage++;
				lastSet=currentTime;
			}
			if(currentImage==14){
				setCurrentSprite(SpriteType.BASIC_IMAGE);
			}
			break;
		case LEFT_MOVE:
			imageName = "DefaultShipPlayerLM"+currentImage;
			if((currentTime-lastSet)>(speed*13)){
				currentImage++;
				lastSet=currentTime;
			}
			if(currentImage==2){
				setCurrentSprite(SpriteType.BASIC_IMAGE);
			}
			break;
		case RIGHT_MOVE:
			imageName = "DefaultShipPlayerRM"+currentImage;
			if((currentTime-lastSet)>(speed*13)){
				currentImage++;
				lastSet=currentTime;
			}
			if(currentImage==2){
				setCurrentSprite(SpriteType.BASIC_IMAGE);
			}
			break;
		case DEAD_SHIP:
			imageName = "DefaultShipPlayerDead"+explosion;
			if(currentTime-lastImageChange>speed){
				explosion++;
				lastImageChange=currentTime;
			}
			if(explosion==11){
				explosion=1;
			}
			break;
		default:
			throw new AssertionError();
		}
		return imageName;
	}
	
	@Override
	public void onDrawSprite(Canvas canvas) {
		super.onDrawSprite(canvas);
		this.setImage(FrontApplication.frontImage.getImage(getNextImageName()));
	}
}
