package fr.umlv.escape.front;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

/**
 * This class contains all the images to draw
 */
public class FrontImages {
	private static final Map<String,Image> imagesMap = new HashMap<>();
	
	private FrontImages(){}
	
	/**
	 * Add an {@link Image} to the frontImage
	 * @param key the key to get the image
	 * @param value The image to add
	 * @return true if the image have been added else false
	 */
	public static boolean addImages(String key, Image value){
		if(key==null || value==null){
			throw new IllegalArgumentException();
		}
		if(!imagesMap.containsKey(key)){
			imagesMap.put(key, value);
			return true;
		}
		return false;
	}

	/**
	 * Get the {@link Image} associated to the key
	 * @param key the key of the image to get
	 * @return the image associated to the key given
	 */
	public static Image getImage(String key){
		return imagesMap.get(key);
	}
	
	/**
	 * Delete all images stored
	 */
	public static void clearMap(){
		imagesMap.clear();
	}
}
