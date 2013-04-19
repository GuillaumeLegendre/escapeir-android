package fr.umlv.escape.game;

import java.io.IOException;

import fr.umlv.escape.file.IllegalFormatContentFile;
import fr.umlv.escape.file.ParseFile;
import fr.umlv.escape.front.ImagesFactory;
import fr.umlv.escape.ship.WaveFactory;
import fr.umlv.escape.ship.Wave;

/**This class supplies methods to create properly a {@link Level}.
 */
public class LevelFactory {
	private static LevelFactory TheLevelFactory;
	private ParseFile parseLevel;

	private LevelFactory(){
	}
	
	/**
	 * Create a {@link Level}
	 * @param levelName The name of the level.
	 * @return The level created
	 * @throws IOException
	 * @throws IllegalFormatContentFile
	 */
	public Level createLevel(String levelName) throws IOException, IllegalFormatContentFile{
		Level newLevel = new Level(levelName);
		this.parseLevel=new ParseFile("Levels/"+levelName);
		
		//Get all the wave
		String waveName=this.parseLevel.getNextLine();
		long delay;
		if(waveName==null){
			throw new IllegalFormatContentFile("The file "+parseLevel.getFileName()+" is corrupted");
		}
		while(waveName!=null){
			if(!newLevel.addWaveList(WaveFactory.getTheWaveFactory().createWave(waveName))){
				throw new IllegalFormatContentFile("The file "+parseLevel.getFileName()+" is corrupted");
			}
			try{
				delay=Long.valueOf(parseLevel.getNextLine());
			}
			catch(NumberFormatException e){
				throw new IllegalFormatContentFile("The file "+parseLevel.getFileName()+" is corrupted");
			}
			if(!newLevel.addDelayList(delay)){
				throw new IllegalFormatContentFile("The file "+parseLevel.getFileName()+" is corrupted");
			}
			waveName=parseLevel.getNextLine();
		}
		FrontImages.addImages(levelName,ImagesFactory.getTheImagesFactory().createBackGroundImage(levelName));
		return newLevel;
	}
	
	/** Get the unique instance of {@link LevelFactory}.
	 * @return The unique instance of {@link LevelFactory}
	 */
	public static LevelFactory getTheLevelFactory(){
		if(LevelFactory.TheLevelFactory==null){
			LevelFactory.TheLevelFactory = new LevelFactory();
		}
		return LevelFactory.TheLevelFactory;
	}
}
