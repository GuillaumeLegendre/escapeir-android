package fr.umlv.escape.front;

import java.awt.Image;
import java.util.Objects;

/**
  * Class that manage a scrolling background. The backGroundScroller can scroll an image in
  * the vertical or horizontal way.
  */
public class BackGroundScroller {
	private int posX;
	private int posY;
	private final int heightScreen;
	private Image backgroundImage;
	
	/**Constructor
	 * @param heightScreen The height of the screen.
	 * @param backGroundName The name of the background
	 */
	public BackGroundScroller(int heightScreen,String backGroundName){
		if(heightScreen<0){
			throw new IllegalArgumentException("height screen can't be negative");
		}
		Objects.requireNonNull(backGroundName);
		
		this.heightScreen=heightScreen;
		this.backgroundImage=FrontImages.getImage(backGroundName);
		this.posX=0;
		this.posY=-(backgroundImage.getHeight(null)-heightScreen);
		
	}

	/** Applies a vertical scroll step to the backGround.
	  */
	public void verticalScroll(){
		posY+=1;
		if(posY>=0){
			posY=-(backgroundImage.getHeight(null)-heightScreen);
		}
	}
	
	/** Return the x position of the backGround.
	  * @return the x position of the backGround.
	  */
	public int getPosX(){
		return posX;
	}
	
	/** Return the y position of the backGround.
	  * @return the y position of the backGround.
	  */
	public int getPosY(){
		return posY;
	}
	
	/** Return the backGround image.
	  * @return the backGround image.
	  */
	public Image getBackgroundImage(){
		return backgroundImage;
	}
	
	/** Set the backGround image.
	  * @param backgroundImage Image of the new backGround.
	  */
	public void setBackgroundImage(Image backgroundImage){
		Objects.requireNonNull(backgroundImage);
		
		this.backgroundImage=backgroundImage;
	}
}
