package fr.umlv.escape.front;

import fr.umlv.escape.Objects;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
  * Class that manage a scrolling background. The backGroundScroller can scroll an image in
  * the vertical or horizontal way.
  */
public class BackGroundScroller {
	Bitmap backgroundImage;
	Rect screenRect;
	Rect backgroundRect;
	private int backgroundHeight; // Optimization for not using getter
	private boolean isLooping;
	
	/**Constructor
	 * @param heightScreen The height of the screen.
	 * @param backGroundName The name of the background
	 */
	public BackGroundScroller(int widthScreen, int heightScreen, boolean isLooping){
		this(new Rect(0,0,widthScreen,heightScreen),isLooping);
		if(heightScreen<0){
			throw new IllegalArgumentException("height screen can't be negative");
		}
	}
	
	public BackGroundScroller(Rect screenRect, boolean isLooping){
		Objects.requireNonNull(screenRect);
		this.isLooping = isLooping;
		this.screenRect = screenRect;
	}

	/** Applies a vertical scroll step to the backGround.
	  */
	public void verticalScroll(int nbPixel){
		if(backgroundRect.top<=0 && nbPixel<0){
			System.out.println(1);
			if(isLooping){
				System.out.println(2);
				backgroundRect.top=backgroundHeight-screenRect.bottom;
				backgroundRect.bottom=backgroundHeight;
			} else {
				System.out.println(3);
				return;
			}
		}
		if(backgroundRect.bottom>=backgroundHeight && nbPixel>0){
			System.out.println(4);
			if(isLooping){
				System.out.println(5);
				backgroundRect.top=0;
				backgroundRect.bottom=backgroundHeight-screenRect.bottom;
			} else {
				System.out.println(6);
				return;
			}
		}
		backgroundRect.top+=nbPixel;
		backgroundRect.bottom+=nbPixel;
	}
	
	public void onDrawBackground(Canvas canvas){
		if(backgroundImage==null)return;
		canvas.drawBitmap(backgroundImage, backgroundRect, screenRect, null);
	}
	
	public void updateScreenSizes(int width, int height){
		updateScreenSizes(0, 0, width, height);
	}
	
	public void updateScreenSizes(int left, int top, int right, int bottom){
		System.out.println(left+" - "+top+" - "+right+" - "+bottom);
		this.screenRect = new Rect(left,top,right,bottom);
		this.backgroundRect = new Rect(0, backgroundHeight-bottom, this.backgroundImage.getWidth(), this.backgroundHeight);
	}

	
	public void changeBackground(Bitmap image) {
		this.backgroundImage=image;
		this.backgroundHeight = image.getHeight();
		this.backgroundRect = new Rect(0, backgroundHeight-screenRect.bottom, image.getWidth(), backgroundHeight);
		System.out.println("background: "+backgroundRect);
	}
}
