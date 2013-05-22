package fr.umlv.escape.front;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Class that an explosion sprite
 */
public class SpriteAnimation extends Sprite{
	private int currentImage;
	private long lastImageChange;
	private final int speed;
	private boolean isGrowing;
	private final int x;
	private final int y;
	
	/**
	 * constructor
	 * @param speed the speed of the sprite
	 * @param x the position x where the explosion is spawn
	 * @param y the position y where the explosion is spawn
	 */
	public SpriteAnimation(int speed, int x, int y, Bitmap image){
		super(null, image);
		if(speed<0){
			throw new IllegalArgumentException("speed cannot be negative");
		}
		
		this.currentImage=1;
		this.speed=speed;
		isGrowing=true;
		this.x=x;
		this.y=y;
	}

	public Bitmap getNextImage() {
		String imageName = "explosion"+currentImage;
		long currentTime=System.currentTimeMillis();
		
		if(isGrowing){
			if(currentTime-lastImageChange>speed){
				currentImage++;
				lastImageChange=currentTime;
			}
			if(currentImage==11){
				isGrowing=false;
			}
		}
		else{
			if(currentTime-lastImageChange>speed){
				currentImage--;
				lastImageChange=currentTime;
			}
			if(currentImage==0){
				return null;
			}
		}
		return FrontApplication.frontImage.getImage(imageName);
	}
	
	@Override
	public void onDrawSprite(Canvas canvas){
		if(image==null) return;
		
		canvas.drawBitmap(image, x - image.getWidth()/2 , y - image.getHeight()/2, new Paint());
		this.image = getNextImage();
	}

	@Override
	public boolean isStillDisplayable(){
		return (this.image != null);
	}
	
	/**
	 * Get the x position of the current explosion
	 * @return the x position of the current explosion
	 */
	@Override
	public int getPosXCenter() {
		return x;
	}

	/**
	 * Get the y position of the current explosion
	 * @return the y position of the current explosion
	 */
	@Override
	public int getPosYCenter() {
		return y;
	}
}
