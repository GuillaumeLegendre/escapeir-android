package fr.umlv.escape.front;

import java.awt.Image;

/**
 * Class that manage all sprites of {@link Player}
 */
public class PlayerSprites implements Sprite{
	private SpriteType sprite;
	private int currentImage;
	private long lastSet;
	private long lastImageChange;
	private int explosion;
	private final int speed;
	
	/**
	 * Constructors
	 */
	public PlayerSprites(){
		this.sprite=PlayerSprites.SpriteType.BASIC_IMAGE;
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
	 * Load all images that are needed to the sprites. This method has to be called before using this class.
	 */
	public static void loadImagesSprites(){
		Image image=ImagesFactory.getTheImagesFactory().createShipImage("DefaultShipPlayer");
		FrontImages.addImages("DefaultShipPlayer", image);

		for(int i=1;i<=13;++i){
			image=ImagesFactory.getTheImagesFactory().createShipImage("DefaultShipPlayerVL"+i);
			FrontImages.addImages("DefaultShipPlayerVL"+i, image);
		}
		for(int i=1;i<=13;++i){
			image=ImagesFactory.getTheImagesFactory().createShipImage("DefaultShipPlayerVR"+i);
			FrontImages.addImages("DefaultShipPlayerVR"+i, image);
		}
		for(int i=1;i<=1;++i){
			image=ImagesFactory.getTheImagesFactory().createShipImage("DefaultShipPlayerLM"+i);
			FrontImages.addImages("DefaultShipPlayerLM"+i, image);
		}
		for(int i=1;i<=1;++i){
			image=ImagesFactory.getTheImagesFactory().createShipImage("DefaultShipPlayerRM"+i);
			FrontImages.addImages("DefaultShipPlayerRM"+i, image);
		}
		
		for(int i=1;i<=10;++i){
			image=ImagesFactory.getTheImagesFactory().createShipImage("DefaultShipPlayerDead"+i);
			FrontImages.addImages("DefaultShipPlayerDead"+i, image);
		}
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
	public Image getNextImage() {
		return FrontImages.getImage(getNextImageName());
	}
	
}
